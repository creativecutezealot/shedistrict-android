package com.appentus.shedistrict.view.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.OnPermissionChange
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.network.AppConstant
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.kotlinpermissions.KotlinPermissions
import io.embrace.android.embracesdk.Embrace
import io.embrace.android.embracesdk.annotation.StartupActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.item_home.*

@StartupActivity
class SplashActivity : AppCompatActivity(),OnPermissionChange{

    val activity: Activity=this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

         //FirebaseCrashlytics.getInstance().log("crash")

        motionLayout.transitionToStart()
        motionLayout.transitionToEnd()

        accessPermissions(this)


        Embrace.getInstance().logError("There was an error!")
        Embrace.getInstance().endAppStartup()

    }

    fun accessPermissions(permissionChange: OnPermissionChange) {
        KotlinPermissions.with(this)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

                .onAccepted {

                    permissionChange.havePermission(true)

                }
                .onDenied {
                    Toast.makeText(this, "Please accept all require permissions", Toast.LENGTH_SHORT)
                            .show()
                }
                .onForeverDenied {
                    Toast.makeText(
                            this,
                            "Please accept all require permissions from setting",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                .ask()
    }


    private fun goNext() {

        Handler().postDelayed(Runnable {

            if (pbProgress.progress<100){

                if(pbProgress.progress>=45){
                    tvText.text = "Do you have any feedback, questions, or concerns? Please reach out to us to help make us better!"
                }
                pbProgress.progress=pbProgress.progress+1
                goNext()
            }
            else {

                if (PrefManager.getBoolean(AppConstant.userSession)) {
                    startActivity(Intent(activity, HomeActivity::class.java))
                    finishAffinity()
                } else {
                    startActivity(Intent(activity, AuthActivity::class.java))
                    finishAffinity()
                }
            }

        },120)
    }

    override fun havePermission(isGranted: Boolean) {
        if(isGranted){
            if (isFirstTime()) {
                goNext()
            }
            else if (PrefManager.getBoolean(AppConstant.userSession)) {
                goNextwithspped()
            } else {
                goNext()
            }
         }else{
            finish()
        }
    }

    private fun goNextwithspped() {
        Handler().postDelayed(Runnable {
            tvText.visibility=View.GONE
            if (pbProgress.progress<100){

                if(pbProgress.progress>=45){
                    tvText.visibility=View.GONE
                    //tvText.text = "Do you have any feedback, questions, or concerns? Please reach out to us to help make us better!"
                }
                pbProgress.progress=pbProgress.progress+1
                goNextwithspped()
            }
            else {

                if (PrefManager.getBoolean(AppConstant.userSession)) {
                    startActivity(Intent(activity, HomeActivity::class.java))
                    finishAffinity()
                } else {
                    startActivity(Intent(activity, AuthActivity::class.java))
                    finishAffinity()
                }
            }

        },10)
    }

    override fun onStart() {
        super.onStart()
/*
        FirebaseApp.initializeApp(this)*/
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
                this,
                OnSuccessListener<InstanceIdResult> { instanceIdResult ->
                    Log.e("onSuccess: ", instanceIdResult.token)
                    Log.e("dfgffgghfghfgg",instanceIdResult.token)
                    PrefManager.putString(AppConstant.deviceTokenValue, instanceIdResult.token)

                })

        }


    private fun isFirstTime(): Boolean {
        val preferences: SharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
        val ranBefore = preferences.getBoolean("RanBefore", false)
        if (!ranBefore) { // first time
            val editor = preferences.edit()
            editor.putBoolean("RanBefore", true)
            editor.commit()
        }
        return !ranBefore
    }



}
