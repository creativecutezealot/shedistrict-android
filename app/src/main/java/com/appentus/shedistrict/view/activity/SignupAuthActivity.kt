package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.facebook.*
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_signup_auth.*


class SignupAuthActivity : BaseActivity(),ResponseListener{

    val activity: Activity = this
    lateinit var callbackManager: CallbackManager
    var accessToken : AccessToken ? = null
    lateinit var apiController: ApiController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_auth)
        apiController = ApiController(this,this)

        callbackManager = CallbackManager.Factory.create()
        val view: View? = this.getCurrentFocus()
        if (view != null) {
            val inputManager = this
                    .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }

        btFacebook.background = RoundView(this.resources.getColor(R.color.fbColor), Utility().getRadius(80f))
        /*
        val msg = "" +   getString(R.string.already_have_an_account_log_in) + " <font color='#EF3674'> Log In</font>"*/
       /* Utility().setHtmlText(tvLogin, msg)*/


        btEmail.setOnClickListener {
            startActivity(Intent(activity, SignupActivity::class.java))

        }
        btFacebook.setOnClickListener {
            val manager= LoginManager.getInstance()
            manager.loginBehavior = LoginBehavior.DIALOG_ONLY
            manager.logInWithReadPermissions(this, listOf("email", "public_profile"))
            manager.registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            // App code
                            accessToken = loginResult.accessToken
                            if (accessToken != null) {
                                useLoginInformation(accessToken!!)
                            }
                            Log.d("FBLOGIN", loginResult.accessToken.token.toString())
                            Log.d("FBLOGIN", loginResult.recentlyDeniedPermissions.toString())
                            Log.d("FBLOGIN", loginResult.recentlyGrantedPermissions.toString())
                        }

                        override fun onCancel() {
                            // App code
                        }

                        override fun onError(exception: FacebookException) {
                            // App code
                        }
                    })
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }


    private fun useLoginInformation(accessToken: AccessToken) {

        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
            try {
                //here is the data that you want
                Log.d("FBLOGIN_JSON_RES", `object`.toString())

                if (`object`.has("id")) {
                    Log.e("name", `object`.getString("name"))
                    val email : String = if(`object`.getString("email").isNullOrEmpty()){
                        ""
                    }else{
                        `object`.getString("email")
                    }
                    val map = HashMap<String, String>()
                    map["name"] = `object`.getString("name")
                    map["email"] = email
                    map[AppConstant.social] = `object`.getString("id")
                    map["user_profile"] = `object`.getJSONObject("picture").getJSONObject("data").getString("url").toString()
                    map["user_lat"] = SheDistrict.getLocation().latitude.toString()
                    map["user_lang"] = SheDistrict.getLocation().longitude.toString()
                    map[AppConstant.device_type] = AppConstant.deviceTypeValue.toString()
                    map[AppConstant.device_token] = PrefManager.getString(AppConstant.deviceTokenValue)
                    apiController.makeCall(AppConstant.social_login,map)
                } else {
                    Log.e("FBLOGIN_FAILD", `object`.toString())
                }

            } catch (e: Exception) {
                e.printStackTrace()

            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "name,email,id,picture.width(200)")
        request.parameters = parameters
        request.executeAsync()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.social_login) {
            val model = apiController.parseJson(response, UserProfileBean::class.java)
            PrefManager.putString("userInfo",response)
            PrefManager.putString("social","1")
            Utility().showSnackBar(this, model.message)
            startActivity(Intent(this, CreateProfileActivity::class.java))
            finish()
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


    override fun onResume() {
        super.onResume()
        val view: View? = this.getCurrentFocus()
        if (view != null) {
            val inputManager = this
                    .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }



}
