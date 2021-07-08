package com.appentus.shedistrict.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.*
import com.appentus.shedistrict.adapters.AddsubPersonaltAdapter
import com.appentus.shedistrict.adapters.SequrityQuestionAdapter
import com.appentus.shedistrict.adapters.SpinnerCustomAdapter
import com.appentus.shedistrict.models.DateAndTimeBean
import com.appentus.shedistrict.models.SecurityQuestionBean
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.checkbox.*
import kotlinx.android.synthetic.main.checkbox.view.*
import kotlinx.android.synthetic.main.toolbar_logo.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SignupActivity : BaseActivity(), ResponseListener {

    lateinit var apiController: ApiController
    var dateList: MutableList<DateAndTimeBean>? = null
    var month: MutableList<DateAndTimeBean>? = null
    var yearList: MutableList<DateAndTimeBean>? = null
    var tvDayValue: String? = null
    var tvMonthValue: String? = null
    var tvYearValue: String? = null
    var date=""
    var value=""
    var question_id=""
    var answer="coco"
    var flag:Boolean=true
    var list:MutableList<SecurityQuestionBean.ResultBean>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        dateList = ArrayList()
        month = ArrayList()
        yearList = ArrayList()
        list = ArrayList()
        apiController = ApiController(this, this)
        etEmail.addTextChangedListener(GenricTextWatcher("email", etEmail,check1))
        etUserName.addTextChangedListener(GenricTextWatcher("name", etUserName,check2))
        etpswd.addTextChangedListener(GenricTextWatcher("psw", etpswd,check3))
        etMobile.addTextChangedListener(GenricTextWatcher("mobile", etMobile,check4))

        apiController.makeCallGET(AppConstant.get_security_question)

        ccSelectSecurityque.setOnClickListener {
            if(flag){
                rvSecurityQuestion.visibility=View.VISIBLE
                ivUpDown.setImageResource(R.drawable.up)
                flag=false
            }
            else{
                rvSecurityQuestion.visibility=View.GONE
                ivUpDown.setImageResource(R.drawable.down)
                flag=true
            }

        }
        ivSubmit.setColorFilter(resources.getColor(R.color.txtPurpal))
        ivBack.setOnClickListener {
            onBackPressed()
        }
        ivSubmit.setOnClickListener {
            if (isValidation()) {
            requestForSignUp()
        }
        }

        cbShowPassword.setOnClickListener(View.OnClickListener {
            if (cbShowPassword.isChecked) {
                etpswd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                etpswd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        })

        month?.add(DateAndTimeBean("Month"))
        month?.add(DateAndTimeBean("Jan"))
        month?.add(DateAndTimeBean("Feb"))
        month?.add(DateAndTimeBean("Mar"))
        month?.add(DateAndTimeBean("Apr"))
        month?.add(DateAndTimeBean("May"))
        month?.add(DateAndTimeBean("Jun"))
        month?.add(DateAndTimeBean("Jul"))
        month?.add(DateAndTimeBean("Aug"))
        month?.add(DateAndTimeBean("Sep"))
        month?.add(DateAndTimeBean("Oct"))
        month?.add(DateAndTimeBean("Nov"))
        month?.add(DateAndTimeBean("Dec"))

        spinnerMonth.adapter = SpinnerCustomAdapter(this, month,true)
        spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                tvMonthValue = month?.get(position)?.text
                Log.e("jjbjs",tvMonthValue)


                if(! tvMonthValue.equals("Jan")||tvMonthValue.equals("Mar")||tvMonthValue.equals("May")||tvMonthValue.equals("Jul")||tvMonthValue.equals("Aug")||tvMonthValue.equals("Oct")|| tvMonthValue.equals("Dec")|| tvMonthValue.equals("Feb")){
                    (dateList as ArrayList<DateAndTimeBean>).clear()
                    dateList?.add(0, DateAndTimeBean("Date"))
                    for (i in 1..30) {

                        dateList?.add(DateAndTimeBean(i.toString()))
                    }
                    spinnerDate.adapter = SpinnerCustomAdapter(this@SignupActivity, dateList,true)
                    }

                if(tvMonthValue.equals("Jan")||tvMonthValue.equals("Mar")||tvMonthValue.equals("May")||tvMonthValue.equals("Jul")||tvMonthValue.equals("Aug")||tvMonthValue.equals("Oct")|| tvMonthValue.equals("Dec")) {
                    (dateList as ArrayList<DateAndTimeBean>).clear()
                    dateList?.add(0, DateAndTimeBean("Date"))
                    for (i in 1..31) {

                        dateList?.add(DateAndTimeBean(i.toString()))

                    }
                    spinnerDate.adapter = SpinnerCustomAdapter(this@SignupActivity, dateList,true)
                }else
                {
                    if( month?.get(position)?.text.equals("Feb")){
                        (dateList as ArrayList<DateAndTimeBean>).clear()
                        dateList?.add(0, DateAndTimeBean("Date"))
                        for (i in 1..29) {
                            dateList?.add(DateAndTimeBean(i.toString()))

                        }
                        spinnerDate.adapter = SpinnerCustomAdapter(this@SignupActivity, dateList,true)
                    }

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                tvDayValue = dateList?.get(position)?.text
            }
        }

        yearList?.add(0, DateAndTimeBean("Year"))
        val cal = Calendar.getInstance()
       // yearList?.add(DateAndTimeBean((cal.get(Calendar.YEAR).toString())-17))
        for (i in 17..90) {
            //cal.add(Calendar.YEAR, +1)
            yearList?.add(DateAndTimeBean((cal.get(Calendar.YEAR)-i).toString()))
        }

        spinnerYYYY.adapter = SpinnerCustomAdapter(this, yearList,true)
        spinnerYYYY.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                tvYearValue = yearList?.get(position)?.text
            }
        }
    }

    private fun requestForSignUp() {


        value = "$tvMonthValue/$tvDayValue/$tvYearValue"
        date = Utility().getDateFormated("MMM/dd/yyyy", "MM/dd/yyyy", value)
        Log.e("date",date)
        val map = HashMap<String, String>()
        map["email"] = etEmail.text.toString()
        map["name"] = etUserName.text.toString()
        map["password"] = etpswd.text.toString()
        map["mobile"] = etMobile.text.toString()
        map["user_lat"] = SheDistrict.getLocation().latitude.toString()
        map["user_lang"] = SheDistrict.getLocation().longitude.toString()
        map["dob"] = date
        map["question"] = question_id
        map["answer"] = etSecurityAnswer.text.toString()
        map[AppConstant.device_type] = AppConstant.deviceTypeValue.toString()
        map[AppConstant.device_token] = PrefManager.getString(AppConstant.deviceTokenValue)
        apiController.makeCall(AppConstant.user_signup, map)

    }

    private fun isValidation(): Boolean {

        when {

            etEmail.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please Enter Email.")
                return false
            }

            etUserName.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please Enter Name.")
                return false
            }


            etpswd.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please Enter Password.")
                return false
            }

            !Utility().isValidEmail(etEmail.text.toString()) -> {
                Utility().showSnackBar(this, "Please Enter Valid Email.")
                return false
            }
            !Utility().passValidator(etpswd.text.toString()) -> {
                Utility().showSnackBar(this, "Please Enter Minimum 6 digits in password.")
                return false
            }

            tvMonthValue.equals("Month")-> {
                Utility().showSnackBar(this, "please select date of birth")
                return false
            }
            tvDayValue.equals("Date")-> {
            Utility().showSnackBar(this, "please select date of birth")
            return false
        }
            tvYearValue.equals("Year")-> {
                Utility().showSnackBar(this, "please select date of birth")
                return false
            }
            question_id.isEmpty() -> {
                Utility().showSnackBar(this, "Please Select a security question")
                return false
            }
            etSecurityAnswer.text.toString().isEmpty() -> {
                Utility().showSnackBar(this, "Please type the answer")
                return false
            }
            else -> return true
        }

    }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.user_signup) {
            val model = apiController.parseJson(response, UserProfileBean::class.java)
            PrefManager.putString("userInfo",response)
            Utility().showSnackBar(this, model.message)
            startActivity(Intent(this, CreateProfileActivity::class.java))
        }else if(tag==AppConstant.get_security_question){
            val model = apiController.parseJson(response, SecurityQuestionBean::class.java)

            rvSecurityQuestion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
            val adapter =  SequrityQuestionAdapter(this, model.result )
            rvSecurityQuestion.adapter =adapter
            rvSecurityQuestion.isNestedScrollingEnabled = false
            rvSecurityQuestion.addOnItemTouchListener(RecyclerTouchListener(this, rvSecurityQuestion, RecyclerTouchListener.ClickListener { _, pos ->

            for (i in 0 until  model.result.size) {
                if (i == pos) {
                    if ( model.result[i].isSelect()) {
                        model.result[i].isSelect= false
                    } else {

                        model.result[i].isSelect= true
                        question_id=model.result[i].question
                        Log.e("cskkd",question_id)
                    }

                } else {
                    model.result[i].isSelect = false
                }
            }
                adapter.notifyDataSetChanged()

            /*  val json = JSONArray()
             result[position].values.forEach {
                 if (it.isSelect){
                     json.put(it.value_name)
                 }
             }
            */
        }))





        }
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(this, msg)

    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(this, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(this, msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }

  /*  override fun onStart() {
        super.onStart()

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
                this,
                OnSuccessListener<InstanceIdResult> { instanceIdResult ->
                    Log.e("onSuccess: ", instanceIdResult.token)

                    Log.e("dfgffgghfghfgg",instanceIdResult.token)
                    PrefManager.putString(AppConstant.deviceTokenValue, instanceIdResult.token)
                })

    }*/
}
