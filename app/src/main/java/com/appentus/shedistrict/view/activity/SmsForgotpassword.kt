package com.appentus.shedistrict.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.SheDistrict
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_create_profile.*
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_sms_forgotpassword.*
import kotlinx.android.synthetic.main.toolbar_logo.*

class SmsForgotpassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_forgotpassword)
        ivBack.setOnClickListener {
            onBackPressed()
        }

        ccSubmit.setOnClickListener {
            if(etNumber.text.isNullOrEmpty()){

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(etNumber)

            }else {
                startActivity(Intent(SheDistrict.activity, SmsReciveVerification::class.java).putExtra("number", etNumber.text.toString()).putExtra("type", intent.getStringExtra("type")).putExtra("userid", intent.getStringExtra("userid")))

            }
        }

    }
}
