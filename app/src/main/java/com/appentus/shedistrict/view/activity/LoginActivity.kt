package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar_title.*


class LoginActivity : BaseActivity(),ResponseListener{
    val activity: Activity =this
    lateinit var apiController:ApiController
    var status=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        apiController = ApiController(this,this)

        val pref = getSharedPreferences("rememberme", Context.MODE_PRIVATE)

       if(pref!=null) {
           if(pref.getString("status", "").equals("1")){

               etName.setText(pref.getString("username", ""))
               etpswd.setText(pref.getString("userpass", ""))
               tvRemember.isChecked=true

           }
       }


        ivSubmit.setColorFilter(resources.getColor(R.color.txtPurpal))
        ivSubmit.setOnClickListener {
            if (isValidation()) {

                userLogin()
            }
        }

        tvRemember.setOnClickListener { view ->
            if (view is CheckBox) {
                if (view.isChecked)
                    status="1"

                else
                    status="2"
            }
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }

        cbHidePassword.setOnClickListener(View.OnClickListener {
            if (cbHidePassword.isChecked) {
                etpswd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {

                etpswd.transformationMethod = PasswordTransformationMethod.getInstance()

            }
        })

        tvForgot.setOnClickListener {
            if (isValidationForgetpassword())
            {
                val map = HashMap<String,String>()
                map["email"] = etName.text.toString()
                Log.e("vjdsvj",etName.text.toString())
                apiController.makeCall(AppConstant.get_user_by_email,map)

            }
        }

    }


    fun userLogin(){


        val pref=  getSharedPreferences("rememberme", Context.MODE_PRIVATE)
                .edit()
                .putString("username",etName.text.toString())
                .putString("userpass",etpswd.text.toString())
                .putString("status",status)
                .commit()


        val map = HashMap<String,String>()
        map["user_email"] = etName.text.toString()
        map["user_password"] = etpswd.text.toString()
        map["user_lat"] = SheDistrict.getLocation().latitude.toString()
        map["user_lang"] = SheDistrict.getLocation().longitude.toString()
        map[AppConstant.device_type] = AppConstant.deviceTypeValue.toString()
        map[AppConstant.device_token] =  PrefManager.getString(AppConstant.deviceTokenValue)

        Log.e("fdf",AppConstant.deviceTypeValue.toString())
        Log.e("frgrtry",PrefManager.getString(AppConstant.deviceTokenValue))
        apiController.makeCall(AppConstant.user_login,map)
    }

    private fun isValidation(): Boolean {
        when {
            etName.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please enter user name or emailid")
                return false
            }
            etpswd.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please Enter password.")
                return false
            }
            else -> return true
        }
    }

    private fun isValidationForgetpassword(): Boolean {
        when {
            etName.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please fill in your email address")
                return false
            }
            !Utility().isValidEmail(etName.text.toString()) -> {
                Utility().showSnackBar(this, "Please fill in your email address")
                return false
            }

            else -> return true
            }

           }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.user_login){

            Log.e("dfdfddfgf",response)
            val model = apiController.parseJson(response, UserProfileBean::class.java)
            if(model.result.user_status.equals("0")||model.result.user_status.equals("2")){
                if(model.result.user_details[0].user_intro_video.isNullOrEmpty()){
                    PrefManager.putString("UserId", model.result.user_id)
                    PrefManager.putString(AppConstant.userInfo, response)
                    startActivity(Intent(activity, WriteBioCameraActivity::class.java).putExtra("status", model.result.user_status).putExtra("imageurl", model.result.user_details[0].user_bio_image).putExtra("userstatus", model.result.user_status))
                    finishAffinity()
                }else {
                    PrefManager.putString("UserId", model.result.user_id)
                    PrefManager.putString(AppConstant.userInfo, response)
                    startActivity(Intent(activity, WriteBioVideoActivity::class.java).putExtra("status", model.result.user_status).putExtra("vediourl", model.result.user_details[0].user_intro_video).putExtra("userstatus", model.result.user_status))
                    finishAffinity()
                }
            }else
            {
                PrefManager.putString(AppConstant.userInfo, response)
                PrefManager.putBoolean(AppConstant.userSession, true)
                PrefManager.putString("UserId", model.result.user_id)
                startActivity(Intent(activity, HomeActivity::class.java))
                finishAffinity()
            }

        }else   if(tag == AppConstant.get_user_by_email){
          //  val model = apiController.parseJson(response, UserInfoByEmailBean::class.java)
            Log.e("ddhh","gdfdfh")
            startActivity(Intent(this,ForgetPassword::class.java).putExtra("emailid",etName.text.toString()))
            finishAffinity()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }

    override fun onStart() {
        super.onStart()

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
                this,
                OnSuccessListener<InstanceIdResult> { instanceIdResult ->
                    Log.e("onSuccess: ", instanceIdResult.token)

                    Log.e("dfgffgghfghfgg",instanceIdResult.token)
                    PrefManager.putString(AppConstant.deviceTokenValue, instanceIdResult.token)

                })

    }

}
