package com.appentus.shedistrict.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.SheDistrict.activity
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.activity_email_forgotpassword.*
import kotlinx.android.synthetic.main.activity_email_recivelink.*
import kotlinx.android.synthetic.main.activity_email_recivelink.tvChooseDiffent
import kotlinx.android.synthetic.main.activity_email_recivelink.tvTryagain
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.etName
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sms_recive_verification.*
import kotlinx.android.synthetic.main.toolbar_logo.*

class EmailRecivelink : AppCompatActivity(),ResponseListener {
    var type=""
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_recivelink)
        apiController = ApiController(this,this)
        ivBack.setOnClickListener {
            onBackPressed()
        }

        tvEmailid.text=intent.getStringExtra("emailid")

        tvTryagain.setOnClickListener {
            resetPassword()

        }

        tvChooseDiffent.setOnClickListener {

            dialogChooseDiffrent()
        }

    }

    private fun resetPassword() {
        val map = HashMap<String,String>()
        map["email"] = intent.getStringExtra("emailid")
        map["type"] = intent.getStringExtra("type")
        apiController.makeCall(AppConstant.forget_password,map)
    }

    private fun dialogChooseDiffrent() {
        val dialog = Utility().fullScreenDialog(R.layout.choose_different_type, activity!!)
        val rldialog = dialog.findViewById<RelativeLayout>(R.id.rldialog)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvSecurity = dialog.findViewById<TextView>(R.id.tvSecurity)
        val tvSms = dialog.findViewById<TextView>(R.id.tvSms)
        val tvVerifyPhoto = dialog.findViewById<TextView>(R.id.tvVerifyPhoto)

        rldialog.background = RoundView(activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))

        tvSecurity.setOnClickListener {
            type="3"
            startActivity(Intent(activity,ForgetPassword::class.java).putExtra("emailid",intent.getStringExtra("email")))

        }


        tvSms.setOnClickListener {
            type="2"
            startActivity(Intent(activity,SmsForgotpassword::class.java).putExtra("userid",intent.getStringExtra("userid")).putExtra("type",type))

        }
        tvVerifyPhoto.setOnClickListener {
            type="4"
            startActivity(Intent(activity,PhotoVerification::class.java).putExtra("userid",intent.getStringExtra("userid")).putExtra("emailid",intent.getStringExtra("emailid")).putExtra("type",type))

        }

        ivClose.setOnClickListener() {
            dialog.dismiss()

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
