package com.appentus.shedistrict.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.cbHidePassword
import kotlinx.android.synthetic.main.activity_login.etpswd
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.toolbar_title.*

class ResetPassword : AppCompatActivity() , ResponseListener {
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        apiController = ApiController(this,this)

        ivBack.setOnClickListener {
            onBackPressed()
        }

        cbHidePassword.setOnClickListener(View.OnClickListener {
            if (cbHidePassword.isChecked) {
                etConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        })

        cbHideNewPassword.setOnClickListener(View.OnClickListener {
            if (cbHideNewPassword.isChecked) {
                etNewPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                etNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        })
        infopassword.setOnClickListener(){

            cvinfo.visibility=View.VISIBLE
            ccNewPassword.visibility=View.GONE

        }
        ccDeny.setOnClickListener(){
            cvinfo.visibility=View.GONE
            ccNewPassword.visibility=View.VISIBLE
        }
        ccReset.setOnClickListener(){
            if(isValidation()) {
                resetPassword()
            }


        }


    }

    private fun isValidation(): Boolean {
        when {
            etNewPassword.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please Enter your new password")
                return false
            }

            etNewPassword.text.length < 6 -> {
                cvinfo.visibility=View.VISIBLE
                ccNewPassword.visibility=View.GONE
                return false
            }

            (! etNewPassword.text.toString().equals(etConfirmPassword.text.toString()))-> {
                Utility().showSnackBar(this, "Password not matching")
                return false
            }


            else -> return true
        }

    }


    private fun resetPassword() {
        val map = HashMap<String,String>()
        map["user_id"] = intent.getStringExtra("userid")
        map["type"] = intent.getStringExtra("type")
        map["password"] = etNewPassword.text.toString()
        Log.e("jbjjb",etNewPassword.text.toString())
        Log.e("jbjdadaasdasjb", intent.getStringExtra("type"))
        Log.e("jbjjbds",intent.getStringExtra("userid"))
        apiController.makeCall(AppConstant.forget_password,map)
    }

    override fun onSuccess(tag: String, response: String) {
          val dialog = Utility().fullScreenDialog(R.layout.info_password, this!!)
            val rvdialog = dialog.findViewById<LinearLayout>(R.id.rvdialog)
            val ccDeny = dialog.findViewById<ImageView>(R.id.ccDeny)

            rvdialog.background = RoundView(this!!.resources.getColor(R.color.white), Utility().getRadius(40f))

            ccDeny.setOnClickListener(){
                startActivity(Intent(this,LoginActivity::class.java))
                dialog.dismiss()
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
}
