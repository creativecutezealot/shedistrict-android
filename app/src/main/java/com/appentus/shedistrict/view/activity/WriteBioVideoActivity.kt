package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.CreateProfileBean
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.fragments.SurfaceVideoFragment
import com.asksira.bsimagepicker.BSImagePicker
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_record_intro.*
import kotlinx.android.synthetic.main.activity_write_bio_video.*
import kotlinx.android.synthetic.main.activity_write_bio_video.ccClose
import kotlinx.android.synthetic.main.activity_write_bio_video.ccPlay
import kotlinx.android.synthetic.main.activity_write_bio_video.ccSave
import kotlinx.android.synthetic.main.activity_write_bio_video.ccStop
import kotlinx.android.synthetic.main.activity_write_bio_video.ccVideoView
import kotlinx.android.synthetic.main.activity_write_bio_video.ivClose
import kotlinx.android.synthetic.main.activity_write_bio_video.ivImage
import kotlinx.android.synthetic.main.activity_write_bio_video.ivVideo
import kotlinx.android.synthetic.main.activity_write_bio_video.stopVideo
import kotlinx.android.synthetic.main.activity_write_bio_video.tvTime
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class WriteBioVideoActivity : BaseActivity(), ResponseListener {

    val activity: Activity = this
    lateinit var apiController: ApiController
    private lateinit var fragment: SurfaceVideoFragment
    private lateinit var waitTimer: CountDownTimer
    lateinit var slideUp: Animation
    private var filePath: String? = null
    private var videofilePath: File? = null
    private var pStatus: Int = 0
    private var buttonCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_bio_video)
        ivBack.setOnClickListener {
            onBackPressed()
        }

        apiController = ApiController(this, this)
        replaceFragment(SurfaceVideoFragment(), "camera")

        if(intent!=null) {
            if (intent.getStringExtra("type") == AppConstant.NotificationEventFilter.verify) {
                dialogApproved()
                ccVideoView.visibility=View.VISIBLE
                ccClose.visibility=View.VISIBLE
                ivClose.setColorFilter(this.resources.getColor(R.color.skyblue))
                ivVideo.setVideoPath(AppConstant.ImageUrl+intent.getStringExtra("vediourl"))

            }else if(intent.getStringExtra("type") == AppConstant.NotificationEventFilter.unverify) {

                dialogRejected()
                ccVideoView.visibility=View.VISIBLE
                ivVideo.setVideoPath(AppConstant.ImageUrl+intent.getStringExtra("vediourl"))

            }
        }

        if(intent!=null&& intent.hasExtra("status")){

            if(intent.getStringExtra("status").equals("0")){
                ccVideoView.visibility=View.VISIBLE
                ivVideo.setVideoPath(AppConstant.ImageUrl+intent.getStringExtra("vediourl"))
                dialogRequestSend()

            }else if(intent.getStringExtra("status").equals("2")){

                ccVideoView.visibility=View.VISIBLE
                ivVideo.setVideoPath(AppConstant.ImageUrl+intent.getStringExtra("vediourl"))
                  dialogRejected()

            }

        }

        if(!intent.getStringExtra("vediourl").isNullOrEmpty()){

            ccVideoView.visibility=View.VISIBLE
            ivVideo.setVideoPath(AppConstant.ImageUrl+intent.getStringExtra("vediourl"))

        }


        waitTimer= object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                pStatus+=1
                if (pStatus<=59) {
                    tvTime.text = "00:$pStatus"
                }else{
                    pStatus=0
                    waitTimer.cancel()
                }

                val mi = millisUntilFinished / 1000
                if (mi == 0L) {
                    resetRecorderTime()
                }

            }

            override fun onFinish() {
            }
        }

        ivClose.setColorFilter(this.resources.getColor(R.color.skyblue))
        slideUp = AnimationUtils.loadAnimation(this, R.anim.down_to_up)

        tvCamera.setOnClickListener {
            tvCamera.isEnabled = false
            tvStart.isEnabled = true
            ccVideoView.visibility = View.GONE
            tvCamera.visibility = View.GONE
            tvStart.visibility = View.VISIBLE
            ivImage.visibility = View.VISIBLE
            ivVideo.visibility = View.GONE
            ccClose.visibility = View.GONE
            stopVideo.visibility = View.GONE
        }
        tvStart.setOnClickListener {

            tvStart.isEnabled = false
            tvStop.isEnabled = true

            pStatus = 0
            buttonCode = 2
            tvCamera.text = "Stop Recording"
            fragment.startRecordingVideo()
            tvTime.visibility = View.VISIBLE
            waitTimer.start()
            tvStart.visibility = View.GONE
            tvStop.visibility = View.VISIBLE

        }


        tvStop.setOnClickListener {

            if(pStatus > 5) {
                resetRecorderTime()
            }
        }

        ccClose.setOnClickListener {
            ccClose.visibility = View.GONE
            ivVideo.visibility = View.GONE
            ccPlay.visibility = View.GONE
            ccStop.visibility = View.GONE
            ccSave.visibility = View.GONE
            ccVideoView.visibility = View.GONE
            filePath = null
            ccButton.setCardBackgroundColor(resources.getColor(R.color.circleImage))
        }


        playButton.setOnClickListener {
            if (ivVideo.isPlaying) {
                ivVideo.resume()
            } else {
                ivVideo.start()
            }
            ivVideo.requestFocus()
            playButton.visibility = View.GONE
            stopVideo.visibility = View.VISIBLE

        }

        stopVideo.setOnClickListener {

            ivVideo.pause()
            playButton.visibility = View.VISIBLE
            stopVideo.visibility = View.GONE
        }


        btSubmit.setOnClickListener {



            if(intent.hasExtra("userstatus")) {
                if (filePath.isNullOrEmpty() && intent.getStringExtra("userstatus").equals("2")) {
                    YoYo.with(Techniques.Shake)
                            .duration(50)
                            .repeat(5)
                            .playOn(ccCard)
                } else if (filePath.isNullOrEmpty() && intent.getStringExtra("userstatus").equals("1")) {

                    startActivity(Intent(this, CreateProfileActivity::class.java).putExtra("profile", "profile").putExtra("detail_user_id", intent.getStringExtra("detail_user_id")))

                } else if (filePath.isNullOrEmpty() && intent.getStringExtra("userstatus").equals("0")) {

                }
            }
            else {
                videofilePath = File(filePath!!)
                apiController.loader.show()
                requestCreateProfile()
            }
        }
    }

    private fun dialogRejected() {
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_rejected,this)
        val llDialog = dialog.findViewById<LinearLayout>(R.id.llDialog)
        val tvGotit = dialog.findViewById<LinearLayout>(R.id.llDialog)
        val tvRecived = dialog.findViewById<LinearLayout>(R.id.tvRecived)

        tvGotit.setOnClickListener {

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
            builder.addFormDataPart("user_bio_image", "")

        if (filePath == null) {
            builder.addFormDataPart("user_bio_video", "")
            builder.addFormDataPart("user_intro", "")
        } else {
            Log.e("length",videofilePath!!.length().toString())
            builder.addFormDataPart("user_bio_video", videofilePath?.name, RequestBody.create(MediaType.parse("multipart/form-data"), videofilePath!!))
            builder.addFormDataPart("user_intro", videofilePath?.name, RequestBody.create(MediaType.parse("multipart/form-data"), videofilePath!!))
        }

        builder.addFormDataPart("profile","")
        apiController.makeCall(AppConstant.create_profile, builder.build())

    }

    private fun dialogRequestSend() {
        var reason:String
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_requestsend,this)
        val llDialog = dialog.findViewById<LinearLayout>(R.id.llDialog)
        val tvGotit = dialog.findViewById<TextView>(R.id.tvGotit)
        val tvRecived = dialog.findViewById<TextView>(R.id.tvRecived)

       // llDialog.background = RoundView(this.resources.getColor(R.color.dialogbackround), Utility().getRadius(40f))

        tvGotit.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()

        }

        tvRecived.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finishAffinity()
        }

        dialog.show()

    }


    private fun resetRecorderTime() {
        if (waitTimer!=null){
            waitTimer.cancel()
        }
        pStatus = 0
        tvTime.text = "00:$pStatus"

        tvStop.isEnabled = false
        tvCamera.isEnabled = true
        buttonCode = 0
        tvCamera.text = "Record"

        tvStop.visibility = View.GONE
        tvCamera.visibility = View.VISIBLE
        fragment.stopRecordingVideo()
        filePath = fragment.mNextVideoAbsolutePath
        ivVideo.setVideoURI(Uri.parse(fragment.mNextVideoAbsolutePath))
        ivVideo.seekTo(1)
        fragment.mNextVideoAbsolutePath = null
        ccClose.visibility = View.VISIBLE
        ivVideo.visibility = View.VISIBLE
        playButton.visibility = View.VISIBLE
        ccVideoView.visibility = View.VISIBLE
        ivImage.visibility = View.GONE
        tvTime.visibility = View.GONE
        tvCamera.isEnabled = true
        ccButton.setCardBackgroundColor(resources.getColor(R.color.skyblue))
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
        val model = apiController.parseJson(response, CreateProfileBean::class.java)
        PrefManager.putString("UserId", model.result.user_id)
        PrefManager.putString(AppConstant.userInfo, response)
        dialogRequestSend()
      //
    }

    override fun onFailure(tag: String, msg: String) {

    }

    override fun onError(tag: String, msg: String) {

    }

    override fun onNoConnection(tag: String, msg: String) {

    }

}
