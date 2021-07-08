package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_upload_some_pic.*
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class UploadSomePicActivity : AppCompatActivity(), BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.ImageLoaderDelegate, ResponseListener {

    val activity: Activity = this
    lateinit var picker: BSImagePicker
    lateinit var imageV: ImageView
    var file: MutableList<File> = ArrayList()
    var createProfileFile: File? = null
    var createProfileFilec: File? = null
    lateinit var apiController: ApiController
    var clicked = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_some_pic)
        apiController = ApiController(this, this)
        ivBack.setOnClickListener {
            onBackPressed()
        }
        getPicker()


        if (intent.hasExtra("update")) {
            if(Utility().getUser()?.user_photos?.size!! >0)
            Glide.with(this).load(AppConstant.ImageUrl + (Utility().getUser()?.user_photos?.get(0)?.user_photos)).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(iv1)
           if(Utility().getUser()?.user_photos?.size!! >1){
               Glide.with(this).load(AppConstant.ImageUrl + (Utility().getUser()?.user_photos?.get(1)?.user_photos)).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(iv2)
           }
           if(Utility().getUser()?.user_photos?.size!!>2) {
               Glide.with(this).load(AppConstant.ImageUrl + (Utility().getUser()?.user_photos?.get(2)?.user_photos)).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(iv3)
           }
            if(Utility().getUser()?.user_photos?.size!!>3) {
                Glide.with(this).load(AppConstant.ImageUrl + (Utility().getUser()?.user_photos?.get(3)?.user_photos)).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(iv4)

            }
        }


        ivSubmit.setColorFilter(this.resources.getColor(R.color.txtDarkpink))

        ivSubmit.setOnClickListener {
            if (intent.hasExtra("update")) {
                startActivity(Intent(activity, EditProfileActivity::class.java))
                finish()
            } else {
                Log.e("dgcngc",file.toString())
                if(file.toString().isNullOrEmpty())
                {
                    Utility().showSnackBar(this,"Please select Image")

                } else {

                    requestUploadPhoto()

                } }
        }


        iv1.setOnClickListener {
            if (intent.hasExtra("update")) {

                clicked = 0;


            } else {

            }
            setImage(iv1)

        }

        iv2.setOnClickListener {
            if (intent.hasExtra("update")) {

                clicked = 1;
            } else {

            }
            setImage(iv2)


        }

        iv3.setOnClickListener {
            if (intent.hasExtra("update")) {

                clicked = 2;


            } else {

            }
            setImage(iv3)
        }

        iv4.setOnClickListener {
            if (intent.hasExtra("update")) {

                clicked = 3;

            } else {

            }
            setImage(iv4)
        }

    }

    private fun updateUploadPhoto(userPhotosid: String?) {
        apiController.loader.show()
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("user_id", Utility().getUser()!!.user_id)

        builder.addFormDataPart("photo_id", userPhotosid.toString())
        if (createProfileFilec == null) {
            builder.addFormDataPart("photos", "")
        } else {
            builder.addFormDataPart("photos",createProfileFilec?.name, RequestBody.create(MediaType.parse("multipart/form-data"), createProfileFile!!))
        }

        Log.e("iod", Utility().getUser()!!.user_id)
        Log.e("userPhotosid", userPhotosid.toString())
        apiController.makeCall(AppConstant.update_photos, builder.build())

    }


    fun setImage(imageView: ImageView) {
        imageV = imageView
        picker.show(supportFragmentManager, "picker")
    }

    private fun getPicker() {
        picker = BSImagePicker.Builder("com.appentus.shedistrict.fileprovider")
                .setSpanCount(3)
                .hideGalleryTile()
                .setGridSpacing(Utils.dp2px(2))
                .setPeekHeight(Utils.dp2px(360))
                .hideCameraTile()
                .build()
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        if (intent.hasExtra("update")) {
            createProfileFile = File(uri?.path!!)
            createProfileFilec = Compressor(activity).compressToFile(createProfileFile)
            Glide.with(this).load(uri).placeholder(R.drawable.ic_logo).into(imageV)
            if(clicked==0){
                if(Utility().getUser()?.user_photos?.size!! >0){

                    updateUploadPhoto(Utility().getUser()?.user_photos?.get(0)?.photo_id)

                }else{
                    file.add(Compressor(this).compressToFile(createProfileFile))
                    requestUploadPhoto()

                }


            }
            if(clicked==1){
                if(Utility().getUser()?.user_photos?.size!! >1){
                    updateUploadPhoto(Utility().getUser()?.user_photos?.get(1)?.photo_id)

                }else{
                    file.add(Compressor(this).compressToFile(createProfileFile))
                    requestUploadPhoto()

                }


            }
            if(clicked==2){
                if(Utility().getUser()?.user_photos?.size!! >2){
                    updateUploadPhoto(Utility().getUser()?.user_photos?.get(2)?.photo_id)

                }else{
                    file.add(Compressor(this).compressToFile(createProfileFile))
                    requestUploadPhoto()

                }

            }
            if(clicked==3){
                if(Utility().getUser()?.user_photos?.size!! >3){
                    updateUploadPhoto(Utility().getUser()?.user_photos?.get(3)?.photo_id)

                }else{
                    file.add(Compressor(this).compressToFile(createProfileFile))
                    requestUploadPhoto()

                }
            }


        } else {

            val fileData = File(uri?.path!!)

            file.add(Compressor(this).compressToFile(fileData))

            Glide.with(this).load(uri)
                    .placeholder(R.drawable.ic_logo).into(imageV)
        }
    }


    override fun loadImage(imageFile: File?, ivImage: ImageView?) {
        Glide.with(this).load(imageFile)
                .placeholder(R.drawable.ic_logo).into(ivImage!!)

    }

    fun requestUploadPhoto() {
        apiController.loader.show()
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        apiController.makeCall(AppConstant.add_photos, map,file)

    }

    override fun onSuccess(tag: String, response: String) {
        if (tag == AppConstant.add_photos) {
            PrefManager.putString(AppConstant.userInfo, response)
            PrefManager.putBoolean(AppConstant.userSession, true)
            if(intent.hasExtra("update")){

            }else{
                startActivity(Intent(activity, TandCActivity::class.java))
                finishAffinity()
            }

        } else if (tag == AppConstant.update_photos) {
            PrefManager.putString(AppConstant.userInfo, response)
            PrefManager.putBoolean(AppConstant.userSession, true)
            apiController.loader.dismiss()
        }
    }

    override fun onError(tag: String, msg: String) {

        Utility().showSnackBar(this, msg)
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(this, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {

        Utility().showSnackBar(this, msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }
}
