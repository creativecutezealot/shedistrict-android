package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.appentus.shedistrict.R
import kotlinx.android.synthetic.main.activity_join_us.*

class JoinUsActivity : BaseActivity() {

    val activity:Activity=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_us)

        tvContent.movementMethod=LinkMovementMethod.getInstance()

        btJoinUs.setOnClickListener {

            startActivity(Intent(activity, SignupAuthActivity::class.java))
        }

    }
}
