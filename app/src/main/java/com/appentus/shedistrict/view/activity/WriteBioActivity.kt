package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.AppConstant
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_create_profile.*
import kotlinx.android.synthetic.main.activity_write_bio.*
import kotlinx.android.synthetic.main.activity_write_bio.ivSubmit
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class WriteBioActivity : BaseActivity() {

    val activity: Activity = this
    var fileCamera: String? = null
    var fileVideo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_bio)
        llCamera.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(20f))
        llVideo.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(20f))

        ivSubmit.setColorFilter(resources.getColor(R.color.circleImage))

        ivBack.setOnClickListener {
            onBackPressed()
        }

        llCamera.setOnClickListener {
            startActivityForResult(Intent(activity, WriteBioCameraActivity::class.java), 2)
        }

        llVideo.setOnClickListener {
            startActivityForResult(Intent(activity, WriteBioVideoActivity::class.java), 3)
        }


        ivSubmit.setOnClickListener {
            if (isValidation()) {
                val resultIntent = Intent()
                resultIntent.putExtra("bio", etAbout.text.toString())
                resultIntent.putExtra("fileCamera", fileCamera)
                resultIntent.putExtra("fileVideo", fileVideo)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        tvWhyim.setOnClickListener {
            val dialo = Utility().fullScreenDialog(R.layout.dialog_why_im_seeing, activity)
            val ivClose = dialo.findViewById<ImageView>(R.id.ivClose)
            ivClose.setColorFilter(this.resources.getColor(R.color.skyblue))
            ivClose.setOnClickListener { dialo.dismiss() }
            dialo.show()
        }
    }




    private fun isValidation(): Boolean {
        return when {
            etAbout.text.toString().isEmpty() -> {
               /* Utility().showSnackBar(this, "Please write something...")*/

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(etAbout)
                false
            }
            fileCamera.isNullOrEmpty()&& fileVideo.isNullOrEmpty() -> {
//                Utility().showSnackBar(this, "Please take a picture...")

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(clVerify)

                false
            }

            else -> true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            2 -> when (resultCode) {
                Activity.RESULT_OK -> {
                    fileCamera = data?.getStringExtra("fileCamera")

                    if (!fileCamera.isNullOrEmpty() && !fileVideo.isNullOrEmpty()) {
                        llCamera.visibility = View.GONE
                        llVerify.visibility = View.VISIBLE
                        ivSubmit.setColorFilter(this.resources.getColor(R.color.skyblue))
                    }


                    val dialo = Utility().fullScreenDialog(R.layout.dialog_verification_complete, activity)
                    dialo.setCancelable(true);
                    dialo.setCanceledOnTouchOutside(false);
                    val ivClose = dialo.findViewById<ImageView>(R.id.ivClose)
                    ivClose.setColorFilter(this.resources.getColor(R.color.sblue))
                    ivClose.setOnClickListener {
                        dialo.dismiss()
                    }
                    dialo.show()
                }

            }

            3 -> when (resultCode) {
                Activity.RESULT_OK -> {
                    fileVideo = data?.getStringExtra("fileVideo")

                    if (!fileCamera.isNullOrEmpty() && !fileVideo.isNullOrEmpty()) {
                        llCamera.visibility = View.GONE
                        /*llVideo.visibility = View.GONE*/
                        llVerify.visibility = View.VISIBLE
                        ivSubmit.setColorFilter(this.resources.getColor(R.color.skyblue))

                    }

                    val dialo = Utility().fullScreenDialog(R.layout.dialog_verification_complete, activity)
                    dialo.setCancelable(true);
                    dialo.setCanceledOnTouchOutside(false);
                    val ivClose = dialo.findViewById<ImageView>(R.id.ivClose)
                    ivClose.setColorFilter(this.resources.getColor(R.color.sblue))
                    ivClose.setOnClickListener {
                        dialo.dismiss()
                    }

                    dialo.show()
                }


            }
        }


    }
}