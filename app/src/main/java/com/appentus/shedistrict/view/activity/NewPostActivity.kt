package com.appentus.shedistrict.view.activity

import UnsplashBean
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
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
import com.appentus.nutrition.ui.fragments.AnnouncementFragment
import com.appentus.nutrition.ui.fragments.YourAnnouncementFragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.CategoryAdapter
import com.appentus.shedistrict.adapters.UnSplashSearchAdapter
import com.appentus.shedistrict.adapters.unSplashAdapter
import com.appentus.shedistrict.models.ChooseCategoryBean
import com.appentus.shedistrict.models.UnsplaceSearchBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.fragments.AllPicPrefrence
import com.asksira.bsimagepicker.BSImagePicker
import com.asksira.bsimagepicker.Utils
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.category_list.*
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI
import java.util.HashMap


class NewPostActivity : Fragment(), ResponseListener, BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.ImageLoaderDelegate {

    lateinit var apiController: ApiController
    var categoryId: String? = null
    var categoryValue: String? = null
    var list: MutableList<String> = ArrayList()
    var listid: MutableList<String> = ArrayList()
    var unsplasedImagelist: MutableList<UnsplashBean> = ArrayList()
    var unsplasedImageSearchlist: MutableList<UnsplaceSearchBean.ResultsBean> = ArrayList()
    var file: File? = null
    var uri:Uri? = null
    var uri1: String? = null
    var uri2: String? = null
    var unsplashimageurl: String? = null
    var image_type= ""
    var page: Int = 1
    lateinit var rvUnspalshImage:RecyclerView
    lateinit var picker: BSImagePicker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_new_post, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiController = ApiController(activity!!, this)
        requestPostCategory()
        getPicker()
        ivBack.setColorFilter(this.resources.getColor(R.color.yellow))

        if(arguments?.getString("edit").equals("edit")){
            // file = File(arguments?.getString("value"))
            if( arguments?.getString("image_type").equals("1"))
            {
                file = File(arguments?.getString("image"))
                uri1=arguments?.getString("image")
                Glide.with(this).load(file)
                        .into(ivChooseImage1)

            }
            else{

                Glide.with(this).load(arguments?.getString("image"))
                        .into(ivImage1)
                unsplashimageurl=arguments?.getString("image")
            }
            etCategory.text  = arguments?.getString("category")
            etTitle.setText(arguments?.getString("title"))
            etTitle1.setText(arguments?.getString("desc"))
            categoryId = arguments?.getString("categoryId")
            image_type = arguments?.getString("image_type")!!

        }


        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        ccUploadImage.setOnClickListener {
            image_type="2"
            unsplasedImageSearchlist.clear()
            unsplasedImagelist.clear()
            dialogUnsplas()
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
            image_type="1"
            picker.show(childFragmentManager, "picker")

        }

        ccCategory.setOnClickListener {
            showCategoryDialog(list,listid)
        }

        ccPreview.setOnClickListener {
            if (isValidation()) {
                 if(image_type.equals("2")){
                     val fragment = PreviewActivity()
                     val bundle = Bundle()
                     bundle.putString("value",unsplashimageurl)
                     bundle.putString("unsplase","unsplase")
                     bundle.putString("desc", etTitle1.text.toString().trim())
                     bundle.putString("title", etTitle.text.toString())
                     bundle.putString("category", etCategory.text.toString())
                     bundle.putString("categoryId", categoryId)
                     fragment.arguments = bundle
                     (activity as HomeActivity).replaceFragment(fragment, "postdetail")
                 }
                else {

                     val fragment = PreviewActivity()
                     val bundle = Bundle()
                     bundle.putString("value",uri1)
                     bundle.putString("desc", etTitle1.text.toString().trim())
                     bundle.putString("title", etTitle.text.toString())
                     bundle.putString("category", etCategory.text.toString())
                     bundle.putString("categoryId", categoryId)
                     fragment.arguments = bundle
                     (activity as HomeActivity).replaceFragment(fragment, "postdetail")
                 }

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

        setstatus()
    }

    private fun setstatus() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["status"] = "1"
        apiController.makeCallSilent(AppConstant.update_online_offline, map)

    }

    private fun isValidation(): Boolean {
        when {
            file == null && image_type == null -> {
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
            startActivity(Intent( activity!!,HomeActivity::class.java))
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
        builder.addFormDataPart("announcement_desc", etTitle1.text.toString().trim())
        builder.addFormDataPart("image_type",image_type)
        if(image_type.equals("1")) {
            builder.addFormDataPart("image", file?.name, RequestBody.create(MediaType.parse("multipart/form-data"), file!!))
        }else{
           Log.e("sfsfdsd",unsplashimageurl.toString())
Log.e("sdadafsfdsd",image_type.toString())
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

            rvUnspalshImage.layoutManager = GridLayoutManager(activity!!, 4, GridLayoutManager.VERTICAL, false)
            rvUnspalshImage.adapter = unSplashAdapter(activity!!, unsplasedImagelist)

        }else if (tag == AppConstant.unplaceImageSearch) {
            val model = apiController.parseJson(response, UnsplaceSearchBean::class.java)
            unsplasedImageSearchlist=model.results
            Log.e("jbsjasa",unsplasedImageSearchlist.toString())

            rvUnspalshImage.layoutManager = GridLayoutManager(activity!!, 4, GridLayoutManager.VERTICAL, false)
            val adapter = UnSplashSearchAdapter(activity!!, unsplasedImageSearchlist)
            rvUnspalshImage.adapter = adapter
            //adapter.notifyDataSetChanged()
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
        ivChooseImage1.visibility = View.VISIBLE
        val circularProgressDrawable = CircularProgressDrawable(activity!!)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        uri1 = uri?.path!!
        Log.e("kkkbkb",uri?.path!!)
        Log.e("jgjgj", File(uri.path!!).toString())
        file = File(uri.path!!)
        Glide.with(this).load(uri)
                .placeholder(circularProgressDrawable).into(ivChooseImage1)

        if(!unsplashimageurl.toString().isNullOrEmpty()){

            ivImage1.visibility=View.GONE
        }

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
        apiController.unSplashApi(AppConstant.unplaceImage, page.toString())

        rldialog.background = RoundView(activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))


        rvUnspalshImage.addOnItemTouchListener(RecyclerTouchListener(activity, rvUnspalshImage, RecyclerTouchListener.ClickListener { view, position ->
            ivImage1.visibility=View.VISIBLE

            if(unsplasedImageSearchlist.isNullOrEmpty()){

                Glide.with(this).load(unsplasedImagelist[position].urls.small).placeholder(R.drawable.imageplaceholder).into(ivImage1)
                unsplashimageurl = unsplasedImagelist[position].urls.small

            }else{

                Glide.with(this).load(unsplasedImageSearchlist[position].urls.small).placeholder(R.drawable.imageplaceholder).into(ivImage1)
                unsplashimageurl = unsplasedImageSearchlist[position].urls.small

            }



            dialog.dismiss()




            if(!uri1.toString().isNullOrEmpty()){
                ivChooseImage1.visibility=View.GONE
            }



        }))

        rvUnspalshImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val a = isLastItemDisplaying(rvUnspalshImage)
                if (a) {
                    if(etSearch.text.toString().isNullOrEmpty()){
                        super.onScrolled(recyclerView, dx, dy)
                        page=page+1
                        apiController.unSplashApi(AppConstant.unplaceImage, page.toString())
                    }else{

                        super.onScrolled(recyclerView, dx, dy)
                        page=page+1
                        apiController.unSplashApi(AppConstant.unplaceImageSearch, page.toString())
                    }

                }


            }
        })
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

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
              if(s.isNullOrEmpty()){

                  apiController.unSplashApi(AppConstant.unplaceImage, page.toString())
                  unsplasedImageSearchlist.clear()
              }

            }
        })



        imCancel.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        if (recyclerView.adapter!!.itemCount != 0) {
            val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!.itemCount-1)
                return true
        }
        return false
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(tag)
//            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
        fragmentTransaction.replace(R.id.framchild, fragment, tag)
        fragmentTransaction.commit()
    }


}
