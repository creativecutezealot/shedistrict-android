package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.*
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.otaliastudios.cameraview.CameraListener
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_photo_verification.*
import kotlinx.android.synthetic.main.activity_photo_verification.tvChooseAnother
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_writebio_camera.*
import kotlinx.android.synthetic.main.activity_writebio_camera.btSubmit
import kotlinx.android.synthetic.main.activity_writebio_camera.camera
import kotlinx.android.synthetic.main.activity_writebio_camera.ccButton
import kotlinx.android.synthetic.main.activity_writebio_camera.ccClose
import kotlinx.android.synthetic.main.activity_writebio_camera.ccImageCard
import kotlinx.android.synthetic.main.activity_writebio_camera.ivClose
import kotlinx.android.synthetic.main.activity_writebio_camera.ivImage
import kotlinx.android.synthetic.main.activity_writebio_camera.tCamera
import kotlinx.android.synthetic.main.activity_writebio_camera.tvCamera
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PhotoVerification : AppCompatActivity() ,ResponseListener {
    private var file: File? = null
    val activity: Activity = this
    var buttonCode = 0
    var cameraCode = 0
    var type=""
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_verification)

        apiController = ApiController(this,this)
        ccClose.visibility = View.GONE

        ivImage.visibility = View.GONE

        tvChooseAnother.setOnClickListener {
            dialogChooseDiffrent()
        }


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
            if (file != null) {
                Log.e("file", file?.toString()!!)
                setResult(Activity.RESULT_OK, intent.putExtra("fileCamera", file?.toString()))
                finish()
            } else {
                /*  Utility().showSnackBar(this, "Please choose image ")*/


                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(tvCamera)

            }
        }
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
        ccButton.setCardBackgroundColor(resources.getColor(R.color.skyblue))

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        buttonCode = 0
        //tCamera.text = "Retake"
        resetPassword()
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

    private fun dialogChooseDiffrent() {
        val dialog = Utility().fullScreenDialog(R.layout.choose_different_type, SheDistrict.activity!!)
        val rldialog = dialog.findViewById<RelativeLayout>(R.id.rldialog)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvSms = dialog.findViewById<TextView>(R.id.tvSms)
        val tvEmail= dialog.findViewById<TextView>(R.id.tvSecurity)
        val tvSecurity = dialog.findViewById<TextView>(R.id.tvVerifyPhoto)

        tvEmail.text="Email"
        tvSecurity.text="Security question"

        tvEmail.setOnClickListener {
            type="1"
            startActivity(Intent(SheDistrict.activity,EmailForgotpassword::class.java).putExtra("type",type))

        }


        tvSms.setOnClickListener {
            type="2"
            startActivity(Intent(SheDistrict.activity,SmsForgotpassword::class.java).putExtra("type",type).putExtra("userid", intent.getStringExtra("userid")))

        }
        tvSecurity.setOnClickListener {
            type="3"
            startActivity(Intent(SheDistrict.activity,ForgetPassword::class.java).putExtra("type",type).putExtra("emailid",intent.getStringExtra("emailid")))

        }


        rldialog.background = RoundView(SheDistrict.activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))
        ivClose.setOnClickListener() {
            dialog.dismiss()

        }
    }

    private fun resetPassword() {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("user_id", intent.getStringExtra("userid"))
        builder.addFormDataPart("type",intent.getStringExtra("type"))
        if (file == null) {
            builder.addFormDataPart("file", "")
        } else {
            builder.addFormDataPart("file", file?.name, RequestBody.create(MediaType.parse("multipart/form-data"), file!!))
        }
        apiController.makeCall(AppConstant.forget_password, builder.build())

    }

    override fun onSuccess(tag: String, response: String) {
        Toast.makeText(this,"Admin will verify your photo",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,LoginActivity::class.java))
        finishAffinity()
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

}

