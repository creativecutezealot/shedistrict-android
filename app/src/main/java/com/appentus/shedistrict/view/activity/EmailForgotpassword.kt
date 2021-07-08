package com.appentus.shedistrict.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.activity_email_forgotpassword.*
import kotlinx.android.synthetic.main.activity_email_forgotpassword.view.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.toolbar_logo.*

class EmailForgotpassword : AppCompatActivity(), ResponseListener {
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_forgotpassword)
        apiController = ApiController(this,this)
        ivBack.setOnClickListener {
            onBackPressed()
        }

        ccSubmit.setOnClickListener {
            if(isValidation()) {
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
        val map = HashMap<String,String>()
        map["email"] = etEmailId.text.toString()
        map["type"] = intent.getStringExtra("type")
        apiController.makeCall(AppConstant.forget_password,map)
    }

    private fun isValidation(): Boolean {
        when {
            etEmailId.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please Enter your email")
                return false
            }

            !Utility().isValidEmail(etEmailId.text.toString()) -> {
                Utility().showSnackBar(this, "Please Enter Valid email")
                return false
            }



            else -> return true
        }

    }

    override fun onSuccess(tag: String, response: String) {
        startActivity(Intent(SheDistrict.activity,EmailRecivelink::class.java).putExtra("userid",intent.getStringExtra("userid")).putExtra("emailid",intent.getStringExtra("emailid")).putExtra("type",intent.getStringExtra("type")))
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
