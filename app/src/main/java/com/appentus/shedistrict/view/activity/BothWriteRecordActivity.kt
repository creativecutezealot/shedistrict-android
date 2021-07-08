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
import androidx.fragment.app.Fragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.view.fragments.SurfaceVideoFragment
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_both_write_record.*
import kotlinx.android.synthetic.main.toolbar_title.*

class BothWriteRecordActivity : BaseActivity() {


    private lateinit var waitTimer: CountDownTimer
    lateinit var slideUp: Animation
    private var filePath: String? = null
    private var pStatus: Int = 0
    private var buttonCode = 0
    val activity: Activity = this
    var fragment: SurfaceVideoFragment? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_both_write_record)



        ivBack.setOnClickListener {
            onBackPressed()
        }

        ivSubmit.setColorFilter(this.resources.getColor(R.color.circleImage))

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


        replaceFragment(SurfaceVideoFragment(), "camera")
        ivCam.setColorFilter(this.resources.getColor(R.color.skyblue))
        ivClose.setColorFilter(this.resources.getColor(R.color.skyblue))


        slideUp = AnimationUtils.loadAnimation(this, R.anim.down_to_up)


        ccOpenCam.setOnClickListener {

            ccVideo.visibility = View.VISIBLE
            when (buttonCode) {
                0 -> {
                    ccVideoView.visibility = View.GONE
                    buttonCode = 1
                    ivImage.visibility = View.VISIBLE
                    ivVideo.visibility = View.GONE
                    playButton.visibility = View.GONE
                    ccClose.visibility = View.GONE
                    tvOpen.visibility = View.GONE
                    tvStart.visibility = View.VISIBLE
                    stopVideo.visibility = View.GONE
                }
                1 -> {
                    pStatus = 0
                    buttonCode = 2
                    fragment!!.startRecordingVideo()
                    tvTime.visibility = View.VISIBLE
                    waitTimer.start()
                    tvStop.visibility = View.VISIBLE
                    tvStart.visibility = View.GONE
                }
                2 -> {

                    if(pStatus > 5) {
                        resetRecorderTime()
                    }
                }
            }
        }



        ccClose.setOnClickListener {
            ccVideo.visibility = View.GONE
            ccClose.visibility = View.GONE
            ccPlay.visibility = View.GONE
            ccStop.visibility = View.GONE
            ccSave.visibility = View.GONE
            ccVideoView.visibility = View.GONE
            ivSubmit.setColorFilter(this.resources.getColor(R.color.circleImage))
            filePath = null
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
            Log.e("dfhksdf",ivVideo.duration.toString())

        }

        stopVideo.setOnClickListener {

            ivVideo.pause()
            playButton.visibility = View.VISIBLE
            stopVideo.visibility = View.GONE
        }



        ivSubmit.setOnClickListener {
            when {
                etAbout.text.toString().isNullOrEmpty() -> {
                    /*
                    Utility().showSnackBar(this, "Please write bio something")*/
                    YoYo.with(Techniques.Shake)
                            .duration(50)
                            .repeat(5)
                            .playOn(ccInput)

                }
                filePath.isNullOrEmpty() -> {
                    /*
                    Utility().showSnackBar(this, "Please record video...")*/
                    YoYo.with(Techniques.Shake)
                            .duration(50)
                            .repeat(5)
                            .playOn(ccOpenCam)
                }
                else -> {
                    val resultIntent = Intent()
                    resultIntent.putExtra("bio", etAbout.text.toString())
                    resultIntent.putExtra("fileVideo", filePath)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }


    private fun resetRecorderTime() {

        waitTimer.cancel()
        pStatus = 0
        tvTime.text = "00:$pStatus"
        buttonCode = 0
        tvOpen.visibility = View.VISIBLE
        tvStop.visibility = View.GONE

        fragment!!.stopRecordingVideo()
        filePath = fragment!!.mNextVideoAbsolutePath
        ivVideo.setVideoURI(Uri.parse(fragment!!.mNextVideoAbsolutePath))
        ivVideo.seekTo(1)
        fragment!!.mNextVideoAbsolutePath = null

        ccClose.visibility = View.VISIBLE
        ivVideo.visibility = View.VISIBLE
        playButton.visibility = View.VISIBLE
        ivImage.visibility = View.GONE
        tvTime.visibility = View.GONE
        ccVideoView.visibility = View.VISIBLE

        ivSubmit.setColorFilter(this.resources.getColor(R.color.skyblue))
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
