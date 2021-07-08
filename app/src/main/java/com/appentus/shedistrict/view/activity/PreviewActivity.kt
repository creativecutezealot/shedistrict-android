package com.appentus.shedistrict.view.activity


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.activity_preview.ivImages
import kotlinx.android.synthetic.main.activity_preview.ivProfile
import kotlinx.android.synthetic.main.activity_preview.root
import kotlinx.android.synthetic.main.activity_preview.tvDis
import kotlinx.android.synthetic.main.activity_preview.tvName
import kotlinx.android.synthetic.main.activity_preview.tvTime
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class PreviewActivity :Fragment(),ResponseListener{

    lateinit var apiController: ApiController
    var file : File?= null
    var categoryId:String? = null
    var unsplashimageurl: String? = null
    var image_type= ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_preview, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiController = ApiController( activity!!,this)

        val circularProgressDrawable = CircularProgressDrawable( activity!!)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        getIntentData()
        getData()
    }

    fun getIntentData(){
        if(arguments?.getString("unsplase").equals("unsplase")){
           // file = File(arguments?.getString("value"))
            Glide.with(this).load(arguments?.getString("value"))
                    .into(ivImages)
            unsplashimageurl=arguments?.getString("value")
            image_type="2"
            tvCategory.text = arguments?.getString("category")
            tvDis.text = arguments?.getString("desc")
            titleTop.text =arguments?.getString("title")
            categoryId = arguments?.getString("categoryId")

        }
        else{

            file = File(arguments?.getString("value"))
            Glide.with(this).load(file)
                    .into(ivImages)
            image_type="1"
            tvCategory.text = arguments?.getString("category")
            tvDis.text = arguments?.getString("desc")
            titleTop.text =arguments?.getString("title")
            categoryId = arguments?.getString("categoryId")

        }
        setstatus()
    }

    private fun setstatus() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["status"] = "1"
        apiController.makeCallSilent(AppConstant.update_online_offline, map)

    }

    fun getData(){
        ivBack.visibility = View.GONE


        tvEdit.setOnClickListener {
           // activity!!.onBackPressed()
           // activity!!.finish()
            val fragment = NewPostActivity()
            val bundle = Bundle()
            bundle.putString("edit","edit")
            bundle.putString("image",arguments?.getString("value"))
            bundle.putString("image_type",image_type)
            bundle.putString("category",arguments?.getString("category"))
            bundle.putString("categoryId",categoryId)
            bundle.putString("title",arguments?.getString("title"))
            bundle.putString("desc", arguments?.getString("desc"))
            fragment.arguments = bundle
            (activity as HomeActivity).replaceFragment(fragment, "postdetail")
        }
        tvPreview.setOnClickListener {
            apiController.loader.show()
            requestAddAnnouncment()
        }


        tvName.text = Utility().getUser()?.user_name


        if(Utility().getUser()?.user_social.isNullOrEmpty()){
            if(!Utility().getUser()?.user_profile.isNullOrEmpty()){
                Glide.with(this).load(AppConstant.ImageUrl + Utility().getUser()?.user_profile)
                        .into(ivProfile)
            }
        }else{
            if(!Utility().getUser()?.user_profile.isNullOrEmpty()){
                Glide.with(this).load(Utility().getUser()?.user_profile)
                        .into(ivProfile)

            }
        }


        val currentTime: Date = Calendar.getInstance().time

        when {
            Utility().getDateAgo("EEE MMM dd HH:mm:ss z yyyy", currentTime.toString()) == 0 -> {
                tvTime.text = "Today " + Utility().getDateFormated("EEE MMM dd HH:mm:ss z yyyy",getString(R.string.HHMMA),currentTime.toString())
            }
            Utility().getDateAgo("EEE MMM dd HH:mm:ss z yyyy", currentTime.toString()) == 1 -> {
                tvTime.text = Utility().getDateFormated("EEE MMM dd HH:mm:ss z yyyy",getString(R.string.HHMMA),currentTime.toString())
            }
            Utility().getDateAgo("EEE MMM dd HH:mm:ss z yyyy", currentTime.toString()) == 3 -> {
                tvTime.text =Utility().getDateFormated("EEE MMM dd HH:mm:ss z yyyy",getString(R.string.HHMMA),currentTime.toString())

            }

        }
    }

    private fun showDialogConfirm() {
        root.setAlpha(0.10f);
        val dialog = Utility().fullScreenDialog(R.layout.warringdialog,  activity!!)
        val ccDialog=dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg=dialog.findViewById<TextView>(R.id.tvMsg)
        val tvWarring=dialog.findViewById<TextView>(R.id.tvWarring)
        val tvNo=dialog.findViewById<TextView>(R.id.tvNo)
        val tvYes=dialog.findViewById<TextView>(R.id.tvYes)
        tvNo.visibility= View.GONE
        tvMsg.text="Your announcement has been"
        tvWarring.text="posted!"
        ccDialog.background= RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))
        tvYes.text = "Ok"
        tvYes.setTextColor(resources.getColor(R.color.yellow))
        tvYes.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent( activity!!,HomeActivity::class.java))
            activity!!.finishAffinity()
        }
        dialog.show()
    }


    private fun requestAddAnnouncment(){
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("user_id",Utility().getUser()!!.user_id)
        builder.addFormDataPart("category_id", categoryId)
        builder.addFormDataPart("announcement_title",titleTop.text.toString())
        builder.addFormDataPart("announcement_desc",tvDis.text.toString())
        builder.addFormDataPart("image_type",image_type)
        if(image_type.equals("1")) {
            builder.addFormDataPart("image", file?.name, RequestBody.create(MediaType.parse("multipart/form-data"), file!!))
        }else{

            builder.addFormDataPart("image", unsplashimageurl)
        }
        apiController.makeCall(AppConstant.add_announcement,builder.build())
    }


    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if(tag == AppConstant.add_announcement){
            showDialogConfirm()
        }
    }

    override fun onFailure(tag: String, msg: String) {

        apiController.loader.dismiss()
        Utility().showSnackBar( activity!!,msg)
    }

    override fun onError(tag: String, msg: String) {

        apiController.loader.dismiss()
        Utility().showSnackBar( activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {

        apiController.loader.dismiss()
        Utility().showSnackBar( activity!!,msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }


}
