package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.asksira.bsimagepicker.BSImagePicker
import com.asksira.bsimagepicker.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_create_profile.*
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class CreateProfileActivity : BaseActivity(), BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.ImageLoaderDelegate, ResponseListener {
    val activity: Activity = this
    lateinit var picker: BSImagePicker
    var createProfileFile: File? = null
    var bio: String? = null
    var cameraFile: File? = null
    var videoFile: File? = null

    lateinit var apiController: ApiController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        apiController = ApiController(this, this)

        getPicker()

        ivBack.setOnClickListener {
            onBackPressed()
        }

        ivAvtar.setColorFilter(resources.getColor(R.color.yellow))
        ivSubmit.setColorFilter(resources.getColor(R.color.yellow))

        ccBio.setOnClickListener {
            if (createProfileFile == null) {
                /*
                Utility().showSnackBar(this,"Please choose image ")*/

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccImage)
            }else{
                startActivityForResult(Intent(activity, WriteBioActivity::class.java), 2)
            }
        }
        ccIntro.setOnClickListener {
            startActivityForResult(Intent(activity, RecordIntroActivity::class.java), 3)
        }
        ccBoth.setOnClickListener {
            startActivityForResult(Intent(activity, BothWriteRecordActivity::class.java), 4)
        }


        ivSubmit.setOnClickListener {
            if(intent.hasExtra("profile" )){
                if (createProfileFile == null) {
                    /*
                    Utility().showSnackBar(this,"Please choose image ")*/
                    YoYo.with(Techniques.Shake)
                            .duration(50)
                            .repeat(5)
                            .playOn(ccImage)


                }else{
                    apiController.loader.show()
                    requestCreateProfile()
                }

            }else if(isValidation()) {
                apiController.loader.show()
                requestCreateProfile()

            }
        }

        ccIvImage.setOnClickListener {

            picker.show(supportFragmentManager, "picker")
        }

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
        createProfileFile = File(uri?.path!!)
        createProfileFile = Compressor(this).compressToFile(createProfileFile)
        Glide.with(this).load(uri)
                .placeholder(R.drawable.ic_logo).into(ccIvImage)

    }


    override fun loadImage(imageFile: File?, ivImage: ImageView?) {
        Glide.with(this).load(imageFile)
                .placeholder(R.drawable.ic_logo).into(ivImage!!)

        ////Default: show. Set this if you don't want user to take photo.
        //               .hideGalleryTile()
    }


    fun requestCreateProfile() {

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        builder.addFormDataPart("user_id",intent.getStringExtra("detail_user_id"))
        if (bio.isNullOrEmpty()) {
            builder.addFormDataPart("user_bio","")
        } else {
            builder.addFormDataPart("user_bio", bio!!)
        }
        if (cameraFile == null) {
            builder.addFormDataPart("user_bio_image", "")
        } else {
            builder.addFormDataPart("user_bio_image", cameraFile?.name, RequestBody.create(MediaType.parse("multipart/form-data"), cameraFile!!))
        }
        if (videoFile == null) {
            builder.addFormDataPart("user_bio_video", "")
            builder.addFormDataPart("user_intro", "")
        } else {
            Log.e("length",videoFile!!.length().toString())
            builder.addFormDataPart("user_bio_video", videoFile?.name, RequestBody.create(MediaType.parse("multipart/form-data"), videoFile!!))
            builder.addFormDataPart("user_intro", videoFile?.name, RequestBody.create(MediaType.parse("multipart/form-data"), videoFile!!))
        }
        if(createProfileFile==null){
            builder.addFormDataPart("profile"," ")
        }else{
            builder.addFormDataPart("profile", createProfileFile?.name, RequestBody.create(MediaType.parse("multipart/form-data"), createProfileFile!!))
            apiController.makeCall(AppConstant.create_profile, builder.build())
        }



    }

    fun isValidation(): Boolean {

        if (createProfileFile == null) {
            /*
            Utility().showSnackBar(this,"Please choose image ")*/

            YoYo.with(Techniques.Shake)
                    .duration(50)
                    .repeat(5)
                    .playOn(ccImage)


            return false
        } else if (bio.isNullOrEmpty() && cameraFile == null && videoFile == null) {

          //  Utility().showSnackBar(this,"hoose write bio or choose do both.. ")
            YoYo.with(Techniques.Shake)
                    .duration(50)
                    .repeat(5)
                    .playOn(ccBio)


            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            2 -> when (resultCode) {
                Activity.RESULT_OK -> {
                    bio = data!!.getStringExtra("bio")

                    if(!data.getStringExtra("fileCamera").isNullOrEmpty()) {
                        cameraFile = File(data.getStringExtra("fileCamera")!!)
                    }
                    if(!data.getStringExtra("fileVideo").isNullOrEmpty()) {
                        videoFile = File(data.getStringExtra("fileVideo")!!)
                    }

                }

            }
            3 -> when (resultCode) {
                Activity.RESULT_OK -> {
                     videoFile = File(data?.getStringExtra("fileVideo")!!)


                }

            }

            4 -> when (resultCode) {
                Activity.RESULT_OK -> {
                    bio = data?.getStringExtra("bio")
                    videoFile = File(data?.getStringExtra("fileVideo")!!)
                }
            }
        }


    }

    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if (tag == AppConstant.create_profile) {
            if(intent.hasExtra("Updateprofile")){
                startActivity(Intent(activity, EditProfileActivity::class.java))

            }
            else{
                PrefManager.putString(AppConstant.userInfo, response)
                startActivity(Intent(activity, UploadSomePicActivity::class.java))
            }

        }
    }

    override fun onFailure(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onError(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }
}

