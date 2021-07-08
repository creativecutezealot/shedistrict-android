package com.appentus.shedistrict.view.activity


import android.app.Activity
import android.graphics.Color
import android.net.Uri
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
import com.appentus.shedistrict.view.fragments.SurfaceVideoFragment
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_record_intro.*
import kotlinx.android.synthetic.main.toolbar_title.*


class RecordIntroActivity : BaseActivity() {

    private var pStatus: Int = 0
    private var timerprefix: String = "00:"

    private lateinit var waitTimer: CountDownTimer
    var buttonCode = 0
    private var filePath: String? = null
    var slideUp: Animation? = null
   // private lateinit var fragment: SurfaceVideoFragment
    var fragment: SurfaceVideoFragment? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_intro)
        ivBack.setOnClickListener {
            onBackPressed()
        }

       // this.fragment = supportFragmentManager.findFragmentById(R.id.ivImage) as SurfaceVideoFragment

        ivSubmit.setColorFilter(Color.parseColor("#CACACA"))
        ivClose.setColorFilter(this.resources.getColor(R.color.skyblue))
        replaceFragment(SurfaceVideoFragment(), "camera")
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
                fragment!!.startRecordingVideo()
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

                PrefManager.putString("recordIntro", filePath)
                PrefManager.putBoolean("record", true)
                setResult(Activity.RESULT_OK, intent.putExtra("fileVideo", filePath.toString()))
                Log.e("file", filePath.toString())
                finish()

            }
        }

        ivSubmit.setOnClickListener {
            if (filePath.isNullOrEmpty()) {
                /*
                Utility().showSnackBar(this, "Please record video...")*/

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccOpenCam)
            } else {
                PrefManager.putBoolean("record", false)
                setResult(Activity.RESULT_OK, intent.putExtra("fileVideo", filePath.toString()))
                Log.e("file", filePath.toString())
                finish()

            }
        }


    }

    private fun resetRecorderTime() {
        if (waitTimer != null) {
            waitTimer.cancel()
        }
        pStatus = 0
        tvTime.text = "00:$pStatus"
        buttonCode = 0
        openCamera.text = "Open Camera"
        fragment!!.stopRecordingVideo()
        filePath = fragment!!.mNextVideoAbsolutePath
        ivVideo.setVideoURI(Uri.parse(fragment!!.mNextVideoAbsolutePath))
        ivVideo.seekTo(1)
        fragment!!.mNextVideoAbsolutePath = null
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
        ccVideoView.visibility = View.GONE
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



}
