package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.CreateProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.otaliastudios.cameraview.CameraListener
import kotlinx.android.synthetic.main.activity_write_bio_video.*
import kotlinx.android.synthetic.main.activity_writebio_camera.*
import kotlinx.android.synthetic.main.activity_writebio_camera.btSubmit
import kotlinx.android.synthetic.main.activity_writebio_camera.ccButton
import kotlinx.android.synthetic.main.activity_writebio_camera.ccClose
import kotlinx.android.synthetic.main.activity_writebio_camera.ivClose
import kotlinx.android.synthetic.main.activity_writebio_camera.ivImage
import kotlinx.android.synthetic.main.activity_writebio_camera.tvCamera
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class WriteBioCameraActivity : BaseActivity(),ResponseListener {

    private var file: File? = null
    val activity: Activity = this
    var buttonCode = 0
    var cameraCode = 0
    var cameraFile: File? = null
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writebio_camera)
        apiController = ApiController(this, this)

        if (intent != null) {
            if (intent.getStringExtra("type") == AppConstant.NotificationEventFilter.verify) {
                dialogApproved()

                ccImageCard.visibility = View.VISIBLE
                ivImage.visibility = View.VISIBLE
                ccClose.visibility=View.VISIBLE
                ivClose.setColorFilter(this.resources.getColor(R.color.skyblue))
                // camera.visibility=View.GONE

                ccButton.setCardBackgroundColor(resources.getColor(R.color.skyblue))
                Glide.with(this).load(AppConstant.ImageUrl + intent.getStringExtra("imageurl"))
                        .into(ivImage)


            } else if (intent.getStringExtra("type") == AppConstant.NotificationEventFilter.unverify) {
                dialogRejected()
                ccImageCard.visibility = View.VISIBLE
                // camera.visibility=View.GONE
                ivImage.visibility = View.VISIBLE
                ccButton.setCardBackgroundColor(resources.getColor(R.color.skyblue))

                Glide.with(this).load(AppConstant.ImageUrl + intent.getStringExtra("imageurl"))
                        .into(ivImage)

            }
        }

        if(intent!=null&& intent.hasExtra("status")){
            if(intent.getStringExtra("status").equals("0")){

                ccImageCard.visibility = View.VISIBLE
                ivImage.visibility = View.VISIBLE
                // camera.visibility=View.GONE

                ccButton.setCardBackgroundColor(resources.getColor(R.color.skyblue))
                Glide.with(this).load(AppConstant.ImageUrl + intent.getStringExtra("imageurl"))
                        .into(ivImage)
                dialogRequestSend()

            }else if(intent.getStringExtra("status").equals("2")){

                dialogRejected()
                ccImageCard.visibility = View.VISIBLE
                // camera.visibility=View.GONE
                ivImage.visibility = View.VISIBLE
                ccButton.setCardBackgroundColor(resources.getColor(R.color.skyblue))

                Glide.with(this).load(AppConstant.ImageUrl + intent.getStringExtra("imageurl"))
                        .into(ivImage)

            }

        }

        //ccClose.visibility = View.GONE

      //  ivImage.visibility = View.GONE

        ivBack.setOnClickListener {
            onBackPressed()
        }

        tvCamera.setOnClickListener {
            ccImageCard.visibility = View.VISIBLE
            if (buttonCode == 0) {
                ivImage.visibility = View.GONE
                camera.visibility = View.VISIBLE
                tCamera.text = "Click photo"

                buttonCode = 1
                camera.addCameraListener(object : CameraListener() {
                    override fun onPictureTaken(jpeg: ByteArray) {
                        super.onPictureTaken(jpeg)
                        onPicture(jpeg)
                    }
                })
            } else {
                camera.capturePicture()
            }
        }
        ivClose.setOnClickListener {

            buttonCode = 0
            tCamera.text = "Camera"
            ccImageCard.visibility = View.INVISIBLE
            tvCamera.visibility = View.VISIBLE
            ccClose.visibility = View.GONE
            ivImage.visibility = View.GONE
            ccButton.setCardBackgroundColor(resources.getColor(R.color.circleImage))

        }

        btSubmit.setOnClickListener {
           if(file!=null) {

               apiController.loader.show()
               requestCreateProfile()
               // Log.e("file", file?.toString()!!)

               //  setResult(Activity.RESULT_OK, intent.putExtra("fileCamera", file?.toString()))
               // finish()
           }else if (intent != null && intent.hasExtra("userstatus")) {

               if (file == null && intent.getStringExtra("userstatus").equals("2")) {
                    YoYo.with(Techniques.Shake)
                            .duration(50)
                            .repeat(5)
                            .playOn(tvCamera)

                } else if (file == null && intent.getStringExtra("userstatus").equals("1")) {

                    startActivity(Intent(this, CreateProfileActivity::class.java).putExtra("profile","profile").putExtra("detail_user_id",intent.getStringExtra("detail_user_id")))

                     }
                 } else if (file == null) {

                     YoYo.with(Techniques.Shake)
                       .duration(50)
                       .repeat(5)
                       .playOn(tvCamera)

           }

        }
    }

    private fun dialogRejected() {
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_rejected,this)
        val llDialog = dialog.findViewById<LinearLayout>(R.id.llDialog)
        val tvGotit = dialog.findViewById<LinearLayout>(R.id.llDialog)
        val tvRecived = dialog.findViewById<LinearLayout>(R.id.tvRecived)

        tvGotit.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()
        }

        dialog.show()
    }

    private fun dialogApproved() {
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_approved,this)
        val llDialog = dialog.findViewById<LinearLayout>(R.id.llDialog)
        val tvGotit = dialog.findViewById<LinearLayout>(R.id.llDialog)
        val tvRecived = dialog.findViewById<TextView>(R.id.tvRecived)

        // llDialog.background = RoundView(this.resources.getColor(R.color.dialogbackround), Utility().getRadius(40f))
        tvGotit.setOnClickListener {
            dialog.dismiss()

        }

        dialog.show()
    }


    fun requestCreateProfile() {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        builder.addFormDataPart("user_id",Utility().getUser()!!.user_id)

        builder.addFormDataPart("user_bio", "")

        if (file == null) {
            builder.addFormDataPart("user_bio_image", "")
        } else {
            builder.addFormDataPart("user_bio_image", file?.name, RequestBody.create(MediaType.parse("multipart/form-data"), file!!))
        }

            builder.addFormDataPart("user_bio_video", "")
            builder.addFormDataPart("user_intro", "")

             builder.addFormDataPart("profile","")
            apiController.makeCall(AppConstant.create_profile, builder.build())

    }

    private fun onPicture(jpeg: ByteArray) {
        val photo = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis().toString() + ".jpg")
        if (photo.exists()) {
            photo.delete()
        }
        try {
            val fos = FileOutputStream(photo.path)
            fos.write(jpeg)
            fos.close()
        } catch (e: IOException) {
            Log.e("PictureDemo", "Exception in photoCallback", e)
        }

        file = File(photo.path)
        camera.visibility = View.GONE
        ccClose.visibility = View.VISIBLE
        ivImage.visibility = View.VISIBLE
        ivClose.setColorFilter(this.resources.getColor(R.color.skyblue))
        ccButton.setCardBackgroundColor(resources.getColor(R.color.skyblue))

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        buttonCode = 0
        tCamera.text = "Retake"
        Glide.with(this).load(photo)
                .placeholder(circularProgressDrawable).into(ivImage)
    }

    override fun onResume() {
        super.onResume()
        camera.start()
    }

    override fun onPause() {
        super.onPause()
        camera.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        camera.destroy()
    }

    private fun dialogRequestSend() {
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_requestsend,this)
        val llDialog = dialog.findViewById<LinearLayout>(R.id.llDialog)
        val tvGotit = dialog.findViewById<TextView>(R.id.tvGotit)
        val tvRecived = dialog.findViewById<TextView>(R.id.tvRecived)

        // llDialog.background = RoundView(this.resources.getColor(R.color.dialogbackround), Utility().getRadius(40f))

        tvGotit.setOnClickListener {
            dialog.dismiss()

            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()

        }

        tvRecived.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()
        }

        dialog.show()

    }


    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        val model = apiController.parseJson(response, CreateProfileBean::class.java)
        PrefManager.putString("UserId", model.result.user_id)
        PrefManager.putString(AppConstant.userInfo, response)
        dialogRequestSend()
    }

    override fun onFailure(tag: String, msg: String) {

    }

    override fun onError(tag: String, msg: String) {

    }

    override fun onNoConnection(tag: String, msg: String) {

    }


}
