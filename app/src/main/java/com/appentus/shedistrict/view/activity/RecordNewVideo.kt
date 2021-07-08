package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.fragments.SurfaceVideoFragment
import com.asksira.bsimagepicker.BSImagePicker
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_create_profile.*
import kotlinx.android.synthetic.main.activity_record_intro.*
import kotlinx.android.synthetic.main.activity_record_intro.ivMore
import kotlinx.android.synthetic.main.activity_record_intro.ivSubmit
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RecordNewVideo : AppCompatActivity(),ResponseListener {

    private var pStatus: Int = 0
    private lateinit var waitTimer: CountDownTimer
    var buttonCode = 0
    private var filePath: String? = null
    var slideUp: Animation? = null
    private var timerprefix: String = "00:"
    private lateinit var fragment: SurfaceVideoFragment
    val activity: Activity = this
    lateinit var picker: BSImagePicker
    var createProfileFile: File? = null
    var bio: String? = null
    var cameraFile: File? = null
    var videoFile: File? = null
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_new_video)

        apiController = ApiController(this, this)

        ivBack.setOnClickListener {
            onBackPressed()
        }

        ivSubmit.setColorFilter(Color.parseColor("#CACACA"))
        ivBack.setColorFilter(Color.parseColor("#78E1AC"))
        ivClose.setColorFilter(this.resources.getColor(R.color.skyblue))
        replaceFragment(SurfaceVideoFragment(), "camera")


      //  ivVideo.setVideoPath(+Utility().getUser()?.user_details?.get(0)?.user_intro_video);
        ivVideo.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");

        ivSubmit.setOnClickListener {

            if(!filePath.isNullOrEmpty()) {
                apiController.loader.show()
                requestCreateProfile()

                videoFile = File(filePath)
            }


        }

        if(!Utility().getUser()!!.user_details[0].user_intro_video.isNullOrEmpty()){
            ccVideoView.visibility=View.VISIBLE
            ivVideo.setVideoPath(AppConstant.ImageUrl+Utility().getUser()!!.user_details[0].user_intro_video)

        }


        waitTimer = object : CountDownTimer(300000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pStatus += 1
                if (pStatus < 60) {
                    tvTime.text = "$timerprefix$pStatus"
                } else if(timerprefix.startsWith("00:")){
                    pStatus=0
                    timerprefix="01:"
//                    waitTimer.cancel()
                }
                else if(timerprefix.startsWith("01:"))
                {
                    pStatus=0
                    timerprefix="02:"
                }
                else if(timerprefix.startsWith("02:"))
                {
                    pStatus=0
                    timerprefix="03:"
                }
                else if(timerprefix.startsWith("03:"))
                {
                    pStatus=0
                    timerprefix="04:"
                }
                else if(timerprefix.startsWith("04:"))
                {
                    pStatus=0
                    timerprefix="05:"
                }
                val mi = millisUntilFinished / 1000
                if (mi == 0L) {
                    resetRecorderTime()
                }
            }

            override fun onFinish() {
            }
        }

        if (PrefManager.getBoolean("record") == true) {
            filePath = PrefManager.getString("recordIntro")
            ivVideo.setVideoURI(Uri.parse(filePath))
            ivVideo.seekTo(1)
            ivSubmit.visibility = View.VISIBLE
            ivVideo.animation = slideUp
            ccPlay.animation = slideUp
            ccStop.animation = slideUp
            ccSave.animation = slideUp
            ccClose.visibility = View.VISIBLE
            ccVideoView.visibility = View.VISIBLE
            ccPlay.visibility = View.VISIBLE
            ccStop.visibility = View.VISIBLE
            ccSave.visibility = View.VISIBLE
            ivImage.visibility = View.GONE
            tvTime.visibility = View.GONE
            ccPrefect.visibility = View.VISIBLE
            ivMore.visibility = View.VISIBLE
            ivSubmit.setColorFilter(this.resources.getColor(R.color.circleImage))
        }

        slideUp = AnimationUtils.loadAnimation(this, R.anim.down_to_up)

        ivCam.setColorFilter(this.resources.getColor(R.color.skyblue))

        ccOpenCam.setOnClickListener {
            if (buttonCode == 0) {
                openCamera.text = "Start Recording"
                buttonCode = 1
                ivImage.visibility = View.VISIBLE
                ccVideoView.visibility = View.GONE
                ccPrefect.visibility = View.GONE
                ivMore.visibility = View.GONE
                ccClose.visibility = View.GONE
                ccPlay.visibility = View.GONE
                ccStop.visibility = View.GONE
                ccSave.visibility = View.GONE
            } else if (buttonCode == 1) {
                filePath = null
                buttonCode = 2
                openCamera.text = "Stop Recording"
                ivImage.visibility = View.VISIBLE
                fragment.startRecordingVideo()
                tvTime.visibility = View.VISIBLE
                waitTimer.start()
            } else if (buttonCode == 2) {

                if(pStatus > 5) {
                    resetRecorderTime()
                }

            }
        }


        ccClose.setOnClickListener {
            closeButton()
        }

        ccPlay.setOnClickListener {
            if (ivVideo.isPlaying) {
            } else {
                ivVideo.start()
                ivPlayButton.visibility = View.GONE
                stopVideo.visibility = View.VISIBLE
                ccPlay.isEnabled = true
            }
            ivVideo.requestFocus()

        }


        ivPlayButton.setOnClickListener {
            if (ivVideo.isPlaying) {
                ivVideo.resume()
            } else {
                ivVideo.start()
            }
            ivVideo.requestFocus()
            ivPlayButton.visibility = View.GONE
            stopVideo.visibility = View.VISIBLE



        }



        stopVideo.setOnClickListener {

            ivVideo.pause()
            ivPlayButton.visibility = View.VISIBLE
            stopVideo.visibility = View.GONE

        }

        ccStop.setOnClickListener {

            ivVideo.pause()
            ivPlayButton.visibility = View.VISIBLE
        }

        ccSave.setOnClickListener {

            if (!filePath.isNullOrEmpty()) {

                setResult(Activity.RESULT_OK, intent.putExtra("fileVideo", filePath.toString()))
                Log.e("file", filePath.toString())
                finish()

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

    fun requestCreateProfile() {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)

        builder.addFormDataPart("user_id", Utility().getUser()!!.user_id)
            builder.addFormDataPart("user_bio", "")
            builder.addFormDataPart("user_bio_image", "")
        if (videoFile == null) {
            builder.addFormDataPart("user_bio_video", "")
            builder.addFormDataPart("user_intro","")

        }else{
            builder.addFormDataPart("user_intro", videoFile?.name, RequestBody.create(MediaType.parse("multipart/form-data"), videoFile!!))
        }

        builder.addFormDataPart("profile","")
        apiController.makeCall(AppConstant.create_profile, builder.build())

    }

    private fun resetRecorderTime() {


        if (waitTimer != null) {
            waitTimer.cancel()
        }
        pStatus = 0
        tvTime.text = "00:$pStatus"
        buttonCode = 0
        openCamera.text = "Open Camera"
        fragment.stopRecordingVideo()
        filePath = fragment.mNextVideoAbsolutePath
        ivVideo.setVideoURI(Uri.parse(fragment.mNextVideoAbsolutePath))
        ivVideo.seekTo(1)
        fragment.mNextVideoAbsolutePath = null
        ivSubmit.visibility = View.VISIBLE
        ivVideo.animation = slideUp
        ccPlay.animation = slideUp
        ccStop.animation = slideUp
        ccSave.animation = slideUp
        ivPlayButton.visibility = View.VISIBLE
        stopVideo.visibility = View.GONE
        ccClose.visibility = View.VISIBLE
        ccVideoView.visibility = View.VISIBLE
        ccPlay.visibility = View.VISIBLE
        ivImage.visibility = View.GONE
        tvTime.visibility = View.GONE
        ccPrefect.visibility = View.VISIBLE
        ivMore.visibility = View.VISIBLE
        ivSubmit.setColorFilter(this.resources.getColor(R.color.skyblue))

    }


    fun closeButton(){
        if (waitTimer != null) {
            waitTimer.cancel()
        }
        pStatus = 0
        tvTime.visibility = View.GONE
        buttonCode = 0
        openCamera.text = "Open Camera"
        filePath = null
        ivSubmit.visibility = View.VISIBLE
        ivVideo.animation = slideUp
        ccPlay.animation = slideUp
        ccStop.animation = slideUp
        ccSave.animation = slideUp
        ccClose.visibility = View.GONE

        ccPlay.visibility = View.GONE
        ccPrefect.visibility = View.GONE
        ivMore.visibility = View.GONE
        ccStop.visibility = View.GONE
        ccSave.visibility = View.GONE
        ivImage.visibility = View.GONE
        PrefManager.putBoolean("record", false)
        ivSubmit.setColorFilter(Color.parseColor("#CACACA"))
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.ivImage, fragment, tag)
        fragmentTransaction.commit()

        Handler().postDelayed({
            this.fragment = supportFragmentManager.findFragmentById(R.id.ivImage) as SurfaceVideoFragment
        },1500)

    }
    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if (tag == AppConstant.create_profile) {
                startActivity(Intent(activity, EditProfileActivity::class.java))
            finish()

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

}
