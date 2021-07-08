package com.appentus.shedistrict.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentActivity
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.UserInfoByEmailBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.etName
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar_logo.*


class ForgetPassword : AppCompatActivity(),ResponseListener {
    lateinit var apiController: ApiController
    var answer=""
    var type=""
    var userid=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        ivBack.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        apiController = ApiController(this,this)

        Userdetails()

        etAnswer.setOnEditorActionListener { v, actionId, event ->
            type="3"
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.e("fssfds",etAnswer.text.toString())
                if(etAnswer.text.toString().equals(answer)){

                    imRightWrong.setImageResource(R.drawable.correctans)
                    startActivity(Intent(this,ResetPassword::class.java).putExtra("type",type).putExtra("userid",userid))
                }
                else{
                    imRightWrong.setImageResource(R.drawable.cancel)

                    val unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.wrong_background)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                 //   DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#"+result[position].color))

                    llAnswerType.background= wrappedDrawable
                //    llAnswerType.background=R.drawable.wrong_background

                    tvChooseAnother.visibility= View.VISIBLE
                    llEmailSms.visibility= View.VISIBLE
                    ccVerifyPhoto.visibility= View.VISIBLE
                }

                true
            } else {
                false
            }
        }




        etAnswer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {



            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        tvEmail.setOnClickListener {
            type="1"
            startActivity(Intent(SheDistrict.activity,EmailForgotpassword::class.java).putExtra("type",type).putExtra("userid",userid).putExtra("emailid",intent.getStringExtra("emailid")))

        }


        tvSms.setOnClickListener {
            type="2"
            startActivity(Intent(SheDistrict.activity,SmsForgotpassword::class.java).putExtra("type",type).putExtra("userid",userid))

        }
        tvVerifyPhoto.setOnClickListener {
            type="4"
            startActivity(Intent(SheDistrict.activity,PhotoVerification::class.java).putExtra("type",type).putExtra("userid",userid).putExtra("emailid",intent.getStringExtra("emailid")))

        }

    }

    private fun Userdetails() {
        val map = HashMap<String,String>()
        map["email"] = intent.getStringExtra("emailid")
        apiController.makeCall(AppConstant.get_user_by_email,map)
    }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.get_user_by_email){
            val model = apiController.parseJson(response, UserInfoByEmailBean::class.java)
            answer=model.result.answer
            userid=model.result.user_id
            Log.e("fds",answer)
            etName.text= model.result.question

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
