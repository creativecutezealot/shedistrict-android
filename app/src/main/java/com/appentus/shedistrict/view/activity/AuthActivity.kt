package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.appentus.shedistrict.R
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : BaseActivity() {

    val activity: Activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btLogin.setOnClickListener { }
        btSignup.setOnClickListener {
            startActivity(Intent(activity, SignupAuthActivity::class.java))
        }
        tvJoinus.setOnClickListener {
            startActivity(Intent(activity, JoinUsActivity::class.java))

        }
        btLogin.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))

        }
        val view: View? = this.getCurrentFocus()
        if (view != null) {
            val inputManager = this
                    .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }

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

   /* override fun onRestart() {
        super.onRestart()
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    override fun onPause() {
        super.onPause()
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}*/
