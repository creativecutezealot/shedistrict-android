package com.appentus.shedistrict.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_sms_recive_verification.*
import kotlinx.android.synthetic.main.toolbar_logo.*
import java.util.concurrent.TimeUnit


class SmsReciveVerification : AppCompatActivity() {
    private var mVerificationId: String? = null
     var code=""
    public var editText_otp: EditText? = null
    public  var editText_otp2:EditText? = null
    public  var editText_otp3:EditText? = null
    public  var editText_otp4:EditText? = null
    public  var editText_otp5:EditText? = null
    public  var editText_otp6:EditText? = null
    var countrycode: String? = null
    private val editTextCode: EditText? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_recive_verification)
        ivBack.setOnClickListener {
            onBackPressed()
        }

         val number =intent.getStringExtra("number")
         val last= number .substring(number.length- 4)


        tvMsg.text= "Enter the verification code we've\n send to the phone number ending in" +"\n"+"******"+last


        countrycode= GetCountryZipCode()
        Log.e("countrycode",countrycode)
        if(!countrycode.isNullOrEmpty()) {
            sendVerificationCode(intent.getStringExtra("number"))
        }

        innitView()
        mAuth = FirebaseAuth.getInstance()

        tvSubmit.setOnClickListener {
            code=(etFirstText.text.toString() +
                    etSecondText.text.toString()
                    + etThirdText.text.toString()
                    + etFourthText.text.toString()
                    + etFifthText.text.toString()
                    + etSixthText.text.toString())
            Log.e("code",code)

            verifyVerificationCode(code,mVerificationId)
        }

        tvChooseDiffent.setOnClickListener {

            dialogChooseDiffrent()
        }

        tvTryagain.setOnClickListener {
            if(!countrycode.isNullOrEmpty()) {
                sendVerificationCode(intent.getStringExtra("number"))
            }

        }


    }
    private fun dialogChooseDiffrent() {
        val dialog = Utility().fullScreenDialog(R.layout.choose_different_type, SheDistrict.activity!!)
        val rldialog = dialog.findViewById<RelativeLayout>(R.id.rldialog)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvVerifyPhoto = dialog.findViewById<TextView>(R.id.tvVerifyPhoto)
        val tvSms = dialog.findViewById<TextView>(R.id.tvSms)

        tvSms.text = "Email"

        rldialog.background = RoundView(SheDistrict.activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))
        ivClose.setOnClickListener() {
            dialog.dismiss()


        }
    }

    private fun innitView() {
        editText_otp = findViewById(R.id.etFirstText)
        editText_otp2 = findViewById(R.id.etSecondText)
        editText_otp3 = findViewById(R.id.etThirdText)
        editText_otp4 = findViewById(R.id.etFourthText)
        editText_otp5 = findViewById(R.id.etFifthText)
        editText_otp6 = findViewById(R.id.etSixthText)

        etFirstText.setText(etFirstText.getText().toString() +
                etSecondText.getText().toString()
                + etThirdText.getText().toString()
                + etFourthText.getText().toString()
                + etFifthText.getText().toString()
                + etSixthText.getText().toString())

        etFirstText.addTextChangedListener(GenericTextWatcher(etFirstText))
        etSecondText.addTextChangedListener(GenericTextWatcher(etSecondText))
        etThirdText.addTextChangedListener(GenericTextWatcher(etThirdText))
        etFourthText.addTextChangedListener(GenericTextWatcher(etFourthText))
        etFifthText.addTextChangedListener(GenericTextWatcher(etFifthText))
        etSixthText.addTextChangedListener(GenericTextWatcher(etSixthText))

    }



    private fun sendVerificationCode(mobile: String) {
        Log.e("sdfds",countrycode)
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "$+$countrycode$mobile",
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks)
    }


    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
       override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) { //Getting the code sent by SMS
            val code: String? = phoneAuthCredential.getSmsCode()
            //sometime the code is not detected automatically
//in this case the code will be null
//so user has to manually enter the code
            if (code != null) {
                editTextCode?.setText(code)
                //verifying the code
              //  verifyVerificationCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
           // Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(s, forceResendingToken)
            mVerificationId = s

          //  mResendToken = forceResendingToken
        }
    }

    private fun verifyVerificationCode(code: String, mVerificationId: String?) { //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
        //signing the user
        signInWithPhoneAuthCredential(credential)
        // Save verification ID and resending token so we can use them later

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult?> {
                   override fun onComplete(@NonNull task: Task<AuthResult?>) {
                        if (task.isSuccessful()) { //verification successful we will start the profile activity
                            val intent = Intent(this@SmsReciveVerification, ResetPassword::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("type",intent.getStringExtra("type"))
                            intent.putExtra("userid",intent.getStringExtra("userid"))
                            startActivity(intent)
                        } else { //verification unsuccessful.. display an error message
                            var message = "Somthing is wrong, we will fix it soon..."
                            if (task.getException() is FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered..."
                            }

                        }
                    }
                })
    }

    fun GetCountryZipCode(): String? {
        var CountryID = ""
        var CountryZipCode = ""
        val manager = this.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        //getNetworkCountryIso
        CountryID = manager.simCountryIso.toUpperCase()
        val rl = this.resources.getStringArray(R.array.CountryCodes)
        for (i in rl.indices) {
            val g = rl[i].split(",").toTypedArray()
            if (g[1].trim { it <= ' ' } == CountryID.trim { it <= ' ' }) {
                CountryZipCode = g[0]
                break
            }
        }
        return CountryZipCode
    }



    inner class  GenericTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
          val  text = editable.toString()
            Log.e("otp_text", text)
            when (view.id) {
                R.id.etFirstText-> if (text.length == 1) editText_otp2!!.requestFocus()
                R.id.etSecondText -> if (text.length == 1) editText_otp3!!.requestFocus() else if (text.length == 0) editText_otp!!.requestFocus()
                R.id.etThirdText -> if (text.length == 1) editText_otp4!!.requestFocus() else if (text.length == 0) editText_otp2!!.requestFocus()
                R.id.etFourthText -> if (text.length == 1) editText_otp5!!.requestFocus() else if (text.length == 0) editText_otp3!!.requestFocus()
                R.id.etFifthText -> if (text.length == 1) editText_otp6!!.requestFocus() else if (text.length == 0) editText_otp4!!.requestFocus()
                R.id.etSixthText -> if (text.length == 0) editText_otp5!!.requestFocus()
            }
        }

    }

}

