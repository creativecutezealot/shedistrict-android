package com.appentus.shedistrict.view.fragments

import UnsplashBean
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.SheDistrict.activity
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.CategoryAdapter
import com.appentus.shedistrict.adapters.UnSplashSearchAdapter
import com.appentus.shedistrict.adapters.unSplashAdapter
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.models.ChooseCategoryBean
import com.appentus.shedistrict.models.UnsplaceSearchBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ProgressView
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.activity.PreviewActivity
import com.asksira.bsimagepicker.BSImagePicker
import com.asksira.bsimagepicker.Utils
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_new_post.ccCategory
import kotlinx.android.synthetic.main.activity_new_post.ccPreview
import kotlinx.android.synthetic.main.activity_new_post.ccSubmit
import kotlinx.android.synthetic.main.activity_new_post.ccTitle
import kotlinx.android.synthetic.main.activity_new_post.ccTitle1
import kotlinx.android.synthetic.main.activity_new_post.ccUploadImage
import kotlinx.android.synthetic.main.activity_new_post.ccchooseImage
import kotlinx.android.synthetic.main.activity_new_post.etCategory
import kotlinx.android.synthetic.main.activity_new_post.etTitle
import kotlinx.android.synthetic.main.activity_new_post.etTitle1
import kotlinx.android.synthetic.main.activity_new_post.ivImage1
import kotlinx.android.synthetic.main.activity_new_post.spinnerCategory
import kotlinx.android.synthetic.main.category_list.*
import kotlinx.android.synthetic.main.fragment_repostfragment.*
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URI
import java.net.URL

/**
 * A simple [Fragment] subclass.
 */
class Repostfragment : Fragment(), ResponseListener, BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.ImageLoaderDelegate {


    lateinit var apiController: ApiController
    var result : AnnouncmentBean.ResultBean ? = null
    var categoryId: String? = null
  //  val loader: Dialog = ProgressView.getLoader(activity!!)
    var categoryValue: String? = null
    var list: MutableList<String> = ArrayList()
    var listid: MutableList<String> = ArrayList()
    var unsplasedImagelist: MutableList<UnsplashBean> = ArrayList()
    var unsplasedImageSearchlist: MutableList<UnsplaceSearchBean.ResultsBean> = ArrayList()
    var file: File? = null
    var uri: Uri? = null
    var uri1: String? = null
    var uri2: String? = null
    var page: Int = 1
    var unsplashimageurl: String? = null
    var image_type= ""
    lateinit var rvUnspalshImage: RecyclerView
    lateinit var picker: BSImagePicker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repostfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiController = ApiController(activity!!, this)

        result = getArguments()?.getSerializable("list") as AnnouncmentBean.ResultBean

        dataset()

        requestPostCategory()
        getPicker()
        ivBack.setColorFilter(this.resources.getColor(R.color.yellow))

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        ccUploadImage.setOnClickListener {
            image_type="2"
            apiController.unSplashApi(AppConstant.unplaceImage, page.toString())


        }

        etTitle1.isVerticalScrollBarEnabled = true
        etTitle1.movementMethod = ScrollingMovementMethod()

        spinnerCategory!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("value", "Spinner selected : ${parent.getItemAtPosition(position)}")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        titleTop.text = "New Post"

        ccchooseImage.setOnClickListener {

           // llIchoosemage.visibility = View.GONE
            image_type="1"
            picker.show(childFragmentManager, "picker")



        }
        ccCategory.setOnClickListener {
            /* spinnerCategory.visibility = View.VISIBLE
             etCategory.visibility = View.GONE
             val aa = ArrayAdapter(activity!!, R.layout.spinner_item, list)
             aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
             spinnerCategory.adapter = aa
             spinnerCategory.performClick()*/

            showCategoryDialog(list,listid)
        }

        ccPreview.setOnClickListener {
            if (isValidation()) {
                if(!unsplashimageurl.isNullOrEmpty()){
                    val fragment = PreviewActivity()
                    val bundle = Bundle()
                    bundle.putString("value",unsplashimageurl)
                    bundle.putString("unsplase","unsplase")
                    bundle.putString("desc", etTitle1.text.toString())
                    bundle.putString("title", etTitle.text.toString())
                    bundle.putString("category", etCategory.text.toString())
                    bundle.putString("categoryId", categoryId)
                    fragment.arguments = bundle
                    (activity as HomeActivity).replaceFragment(fragment, "postdetail")
                }
                else {

                    val fragment = PreviewActivity()
                    val bundle = Bundle()
                    bundle.putString("value", uri1)
                    bundle.putString("desc", etTitle1.text.toString())
                    bundle.putString("title", etTitle.text.toString())
                    bundle.putString("category", etCategory.text.toString())
                    bundle.putString("categoryId", categoryId)
                    fragment.arguments = bundle
                    (activity as HomeActivity).replaceFragment(fragment, "postdetail")
                }


                /* val nextIntent = Intent(activity!!, PreviewActivity::class.java)
                 nextIntent.putExtra("value", uri1)
                 nextIntent.putExtra("desc",etTitle1.text.toString())
                 nextIntent.putExtra("title",etTitle.text.toString())
                 nextIntent.putExtra("category",etCategory.text.toString())
                 nextIntent.putExtra("categoryId",categoryId)
                 startActivity(nextIntent)*/
            } else {
                val toast = Toast.makeText(activity!!, "Please fill the all field ", Toast.LENGTH_LONG)
                toast.show()
            }

        }
        ccSubmit.setOnClickListener {
            if (isValidation()) {
                apiController.loader.show()
                requestAddAnnouncment()
            } else {
                val toast = Toast.makeText(activity!!, "Please fill the all field ", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    private fun dataset() {
        if(!result?.announcement_image.isNullOrEmpty()){
            if(result?.image_type.equals("1")) {

                image_type="1"
                ivChooseImagee.visibility=View.VISIBLE
                Glide.with(this).load(AppConstant.ImageUrl + result?.announcement_image)
                        .into(ivChooseImagee)
                DownloadFileFromURL().execute(AppConstant.ImageUrl+result?.announcement_image)

              //  file= File(URI(AppConstant.ImageUrl+result?.announcement_image).path)
            }
            else{
                ivImage1.visibility=View.VISIBLE
                Glide.with(this).load(result?.announcement_image)
                        .into(ivImage1)
                image_type="2"
                unsplashimageurl = result?.announcement_image
            }
        }
        etTitle1.setText(result?.announcement_desc)
        etCategory.text = result?.category?.get(0)?.category_name
        categoryId= result?.category?.get(0)?.category_id
        etTitle.setText(result?.announcement_title.toString())

        image_type= result?.image_type.toString()


         }

    private fun isValidation(): Boolean {
        when {
            file == null && unsplashimageurl==null -> {
                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccchooseImage)

                return false
            }
            etCategory.text.toString().isNullOrEmpty() -> {
                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccCategory)

                return false
            }
            etTitle.text.toString().isNullOrEmpty() -> {
                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccTitle)
                return false
            }
            etTitle1.text.isNullOrEmpty() -> {
                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccTitle1)
                return false
            }
            else -> return true
        }
    }


    private fun showDialogConfirm() {

        val dialog = Utility().fullScreenDialog(R.layout.warringdialog, activity!!)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvWarring = dialog.findViewById<TextView>(R.id.tvWarring)
        val tvNo = dialog.findViewById<TextView>(R.id.tvNo)
        val tvYes = dialog.findViewById<TextView>(R.id.tvYes)
        tvNo.visibility = View.GONE
        tvMsg.text = "Your announcement"
        val text = "<font color=#919191>has been</font> <font color=#000000><b>posted!</b></font>"
        tvWarring.text = (Html.fromHtml(text))
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))
        tvYes.text = "Ok"
        tvYes.setTextColor(resources.getColor(R.color.yellow))
        tvYes.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent( activity!!, HomeActivity::class.java))
            activity!!.finishAffinity()
        }
        dialog.show()
    }


    private fun requestPostCategory() {
        apiController.makeCallGET(AppConstant.post_category)
    }

    private fun requestAddAnnouncment() {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("user_id", Utility().getUser()!!.user_id)
        builder.addFormDataPart("category_id", categoryId)
        builder.addFormDataPart("announcement_title", etTitle.text.toString())
        builder.addFormDataPart("announcement_desc", etTitle1.text.toString())
        builder.addFormDataPart("image_type",image_type)
        if(image_type.equals("1")) {
            Log.e("sddewfe",file.toString())
            Log.e("sfsf","jjgjgj")
            builder.addFormDataPart("image", file?.name, RequestBody.create(MediaType.parse("multipart/form-data"), file!!))
        }else{
            Log.e("fsfsf","assasds")
            Log.e("sddewffbgffe",file.toString())
            builder.addFormDataPart("image", unsplashimageurl)
        }
        apiController.makeCall(AppConstant.add_announcement, builder.build())
    }

    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if (tag == AppConstant.post_category) {
            val model = apiController.parseJson(response, ChooseCategoryBean::class.java)
            for (i in 0 until model.result.size) {

                if (model.result[i].is_show == "0") {
                    list.addAll(listOf(model.result[i].category_name))
                    listid.addAll(listOf(model.result[i].category_id))
                    Log.e("knk", list.size.toString())
                    Log.e("jbsjd", model.result[i].is_show)
                }
            }
        } else if (tag == AppConstant.add_announcement) {
            showDialogConfirm()
        } else if (tag == AppConstant.unplaceImage) {
            var listType = object : TypeToken<List<UnsplashBean>>() {}.type
            var projects = Gson().fromJson(response, listType) as List<UnsplashBean>
            unsplasedImagelist.addAll(projects)
            dialogUnsplas()
        }else if (tag == AppConstant.unplaceImageSearch) {
            val model = apiController.parseJson(response, UnsplaceSearchBean::class.java)
            unsplasedImageSearchlist.addAll(model.results)
            rvUnspalshImage.layoutManager = GridLayoutManager(activity!!, 4, GridLayoutManager.VERTICAL, false)
            rvUnspalshImage.adapter = UnSplashSearchAdapter(activity!!, unsplasedImageSearchlist)
            UnSplashSearchAdapter(activity!!, unsplasedImageSearchlist).notifyDataSetChanged()

            /* var listType = object : TypeToken<List<UnsplaceSearchBean.ResultsBean>>() {}.type
             var projects = Gson().fromJson(response, listType) as List<UnsplaceSearchBean.ResultsBean>
             unsplasedImageSearchlist.addAll(projects)
             rvUnspalshImage.layoutManager = GridLayoutManager(activity!!, 4, GridLayoutManager.VERTICAL, false)
             rvUnspalshImage.adapter = UnSplashSearchAdapter(activity!!, unsplasedImageSearchlist)
             UnSplashSearchAdapter(activity!!, unsplasedImageSearchlist).notifyDataSetChanged()*/
            Log.e("nssnsd","sdnskd")
        }
    }

    override fun onFailure(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!, msg)
        Log.e("jjm", "knkn")
    }

    override fun onError(tag: String, msg: String) {

        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!, msg)
        Log.e("ijooj", "knkn")
    }

    override fun onNoConnection(tag: String, msg: String) {

        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!, msg)
        Log.e("bjbcxv", "knkn")
    }


    private fun showCategoryDialog(categoryList: MutableList<String>, listid: MutableList<String>) {
        val view = layoutInflater.inflate(R.layout.category_list, null)
        val dialog = BottomSheetDialog(activity!!)
        dialog.setContentView(view)

        val categoryAdapter = CategoryAdapter(activity!!, categoryList, object :
                CategoryAdapter.GetClick {
            override fun dataClick(position: Int) {
                dialog.dismiss()
                categoryId = listid[position]
                etCategory.text = categoryList[position]
                etCategory.setTextColor(Color.parseColor("#000000"))
                Log.e("jbscsacssjd", categoryId)

            }
        })
        val manager: RecyclerView.LayoutManager =
                LinearLayoutManager(activity!!)
        dialog.rvList.layoutManager = manager
        dialog.rvList.adapter = categoryAdapter
        dialog.show()
    }


    private fun getPicker() {
        picker = BSImagePicker.Builder("com.appentus.shedistrict.fileprovider")
                .setSpanCount(3)
                .hideGalleryTile()
                .setGridSpacing(Utils.dp2px(2))
                .setPeekHeight(Utils.dp2px(360))
                .build()
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        ivChooseImagee.visibility = View.VISIBLE
        val circularProgressDrawable = CircularProgressDrawable(activity!!)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        uri1 = uri?.path!!
        Log.e("kkkbkb",uri1)
        file = File(uri.path!!)
        Glide.with(this).load(uri).into(ivChooseImagee)

    }

     override fun loadImage(imageFile: File?, ivChooseImage: ImageView?) {
        Glide.with(this).load(imageFile)
                .into(ivChooseImage!!)

     }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }


    private fun dialogUnsplas() {

        val dialog = Utility().fullScreenDialog(R.layout.unaplace_image_dialog, activity!!)
        val imCancel = dialog.findViewById<ImageView>(R.id.ivClose)
        val rldialog = dialog.findViewById<RelativeLayout>(R.id.rldialog)
        val ccSearchview = dialog.findViewById<CardView>(R.id.ccSearchview)
        val etSearch = dialog.findViewById<EditText>(R.id.etSearch)
        rvUnspalshImage = dialog.findViewById<RecyclerView>(R.id.rvUnspalshImage)

        rldialog.background = RoundView(activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))

        rvUnspalshImage.layoutManager = GridLayoutManager(activity!!, 4, GridLayoutManager.VERTICAL, false)
        rvUnspalshImage.adapter = unSplashAdapter(activity!!, unsplasedImagelist)

        rvUnspalshImage.addOnItemTouchListener(RecyclerTouchListener(activity, rvUnspalshImage, RecyclerTouchListener.ClickListener { view, position ->

            ivImage1.visibility=View.VISIBLE
            Glide.with(this).load(unsplasedImagelist[position].urls.small).into(ivImage1)
            Log.e("kjbjbj",unsplasedImagelist[position].urls.small)
            dialog.dismiss()

            // Downloadimage(position,holder);


            unsplashimageurl=unsplasedImagelist[position].urls.small



        }))
        Log.e("kjjdbdj", unsplasedImagelist.toString())


        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.e("fssfds",etSearch.text.toString())
                apiController.unSplashSerchApi(AppConstant.unplaceImageSearch, page.toString(),etSearch.text.toString())
                true
            } else {
                false
            }
        }
        imCancel.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(tag)
//            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
        fragmentTransaction.replace(R.id.framchild, fragment, tag)
        fragmentTransaction.commit()
    }
   inner class DownloadFileFromURL : AsyncTask<String?, String?, String>() {
       // var pd: ProgressDialog? = null
        var pathFolder = ""
        var pathFile = ""
        override fun onPreExecute() {
            super.onPreExecute()
            apiController.loader.show()
        }

        public override fun doInBackground(vararg f_url: String?): String? {
            var count: Int
            try {
                pathFolder = Environment.getExternalStorageDirectory().toString() + "/DownloadTestFolder"
                pathFile = pathFolder + "/" + System.currentTimeMillis() + ".jpg"
                val futureStudioIconFile = File(pathFolder)
                uri1=pathFile
                file = File(pathFile)
                Log.e("kdbfjff",pathFile)
                if (!futureStudioIconFile.exists()) {
                    futureStudioIconFile.mkdirs()
                }
                val url = URL(f_url[0])
                val connection = url.openConnection()
                connection.connect()
                // this will be useful so that you can show a tipical 0-100%
// progress bar
                val lengthOfFile = connection.contentLength
                // download the file
                val input: InputStream = BufferedInputStream(url.openStream())
                val output = FileOutputStream(pathFile)
                val data = ByteArray(1024) //anybody know what 1024 means ?
                var total: Long = 0
                while (input.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    // publishing the progress....
// After this onProgressUpdate will be called
                    publishProgress("" + (total * 100 / lengthOfFile).toInt())
                    // writing data to file
                    output.write(data, 0, count)
                }
                // flushing output
                output.flush()
                // closing streams
                output.close()
                input.close()
             /*   Log.e("dgdh",pathFile)
                Log.e("dsfsgdh",file.toString())
             file = File(pathFile)*/
            } catch (e: Exception) {
                Log.e("Error: ", e.message)
            }
            return pathFile
        }


        override fun onPostExecute(file_url: String) {
            if ( apiController.loader != null) {
                apiController.loader.dismiss()
            }
           /* if (pd != null) {
                pd!!.dismiss()
            }
            val builder = VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val i = Intent(Intent.ACTION_VIEW)
            i.setDataAndType(Uri.fromFile(File(file_url)), "application/vnd.android.package-archive")
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Toast.makeText(activity, "Successfully download", Toast.LENGTH_SHORT).show()
            activity.getApplicationContext().startActivity(i)*/
        }


    }

}
