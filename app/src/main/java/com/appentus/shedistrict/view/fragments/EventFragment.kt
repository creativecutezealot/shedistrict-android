package com.appentus.nutrition.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.TimePickerPopWin
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.MeetAdapter
import com.appentus.shedistrict.adapters.SpinnerCustomAdapter
import com.appentus.shedistrict.models.BaseApiResponse
import com.appentus.shedistrict.models.DateAndTimeBean
import com.appentus.shedistrict.models.FriendListBean
import com.appentus.shedistrict.models.MeetContentBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.category_list.*
import kotlinx.android.synthetic.main.dialog_shedule.*
import kotlinx.android.synthetic.main.fragment_report.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EventFragment : Fragment(), ResponseListener {

    lateinit var apiController: ApiController
    var result: List<MeetContentBean.ResultBean>? = null
    var value: MutableList<DateAndTimeBean>? = null
    var dateList: MutableList<DateAndTimeBean>? = null
    var month: MutableList<DateAndTimeBean>? = null
    var yearList: MutableList<DateAndTimeBean>? = null
    var friendList: MutableList<FriendListBean.ResultBean>? = null
    var friendId: String? = null
    var tvMeetValue: String? = null
    var tvSubjectValue: String? = null
    var tvReasonValue: String? = null
    var tvDayValue: String? = null
    var tvMonthValue: String? = null
    var tvYearValue: String? = null
    var tvTimeValue: String? = null
    var tvMedValue: String? = null
    var tvLocationValue: String? = null
    var date=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiController = ApiController(activity!!, this)

        result = ArrayList()
        value = ArrayList()
        dateList = ArrayList()
        month = ArrayList()
        yearList = ArrayList()
        friendList = ArrayList()

        apiController.makeCallGET(AppConstant.get_meet_content)
        getFriendList()

        ivAdd.setColorFilter(activity!!.resources.getColor(R.color.lightblue))

        if(arguments!=null) {
            if (arguments!!.getString("chatmeet").equals("chatmeet")) {
                showMeet()
            }
        }

        ivAdd.setOnClickListener {
            showMeet()
        }

        value?.add(DateAndTimeBean("AM"))
        value?.add(DateAndTimeBean("PM"))
        dateList?.add(0, DateAndTimeBean("Day"))
        for (i in 1..31) {
            dateList?.add(DateAndTimeBean(i.toString()))
        }

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


        yearList?.add(0, DateAndTimeBean("Year"))
        val cal = Calendar.getInstance()
        yearList?.add(DateAndTimeBean(cal.get(Calendar.YEAR).toString()))
        for (i in 1..20) {
            cal.add(Calendar.YEAR, +1)
            yearList?.add(DateAndTimeBean(cal.get(Calendar.YEAR).toString()))

        }


        tablayout.addTab(tablayout.newTab().setText("Pending"))
        tablayout.addTab(tablayout.newTab().setText("Attending"))

        tablayout.tabGravity = TabLayout.GRAVITY_FILL

        replaceFragment(PendingEventFragment(), "pending")
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (tablayout.selectedTabPosition == 0) {
                    ivActiveAnn.visibility = View.VISIBLE
                    ivActiveYouAnn.visibility = View.INVISIBLE
                    replaceFragment(PendingEventFragment(), "pending")
                } else {
                    ivActiveAnn.visibility = View.INVISIBLE
                    ivActiveYouAnn.visibility = View.VISIBLE
                    replaceFragment(AttendingEventFragment(), "attending")
                }
            }
        })


    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).activeTab(2)
    }


    fun getFriendList() {
        val map = HashMap<String, String>()
        map["user_lat"] = SheDistrict.getLocation().latitude.toString()
        map["user_lang"] = SheDistrict.getLocation().longitude.toString()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        map["keyword"] = ""
        apiController.makeCallSilent(AppConstant.get_friend_list, map)
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()/*
        fragmentTransaction.setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left)*/
        fragmentTransaction.replace(R.id.framchild, fragment, tag)
        fragmentTransaction.commit()
    }

    private fun showMeet() {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_shedule, activity!!)
        val ccDialog = dialog.ccDialog
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))

        val ccInvitation = dialog.ccInvitation
        val ccNever = dialog.ccNever
        val tvNever = dialog.tvNever
        val tvMsg = dialog.tvMsg
        val ivPlus = dialog.ivPlus
        val etMeet = dialog.etMeet
        val ccMeet = dialog.ccMeet
        val etType1 = dialog.etType1
        val etType2 = dialog.etType2
        val listAm = dialog.findViewById<Spinner>(R.id.listAm)
        val spinnerDate = dialog.spinnerDate
        val spinnerMonth = dialog.spinnerMonth
        val spinnerYear = dialog.spinnerYYYY
if(arguments!= null) {
    if (arguments!!.getString("chatmeet").equals("chatmeet")) {
        etMeet.text = arguments!!.getString("friendname")
        friendId = arguments!!.getString("friendId")
    }
}

        val ccTime = dialog.ccTime
        val tvTime = dialog.tvHour
        val etWLocation = dialog.etWLocation

        val text = "<font color=#A9A9A9>Read</font> <font color=#6071ed>Rules & Tips </font> <font color=#A9A9A9>before sending invitation</font> "
        tvMsg.text = (Html.fromHtml(text))


        listAm.adapter = SpinnerCustomAdapter(activity!!, value,false)
        listAm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                tvMedValue = value?.get(position)?.text
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinnerDate.adapter = SpinnerCustomAdapter(activity!!,month ,true)
        spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                tvDayValue = month?.get(position)?.text
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinnerMonth.adapter = SpinnerCustomAdapter(activity!!,dateList  ,true)
        spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                tvMonthValue = dateList?.get(position)?.text
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinnerYear.adapter = SpinnerCustomAdapter(activity!!, yearList,true)
        spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                tvYearValue = yearList?.get(position)?.text
            }
        }

        ccTime.setOnClickListener {
            val pickerPopWin = TimePickerPopWin.Builder(activity!!, TimePickerPopWin.OnTimePickedListener { hour, min, sec, meridium, timeDesc ->
                tvTime.text = timeDesc
                tvTime.setTextColor(Color.parseColor("#000000"))
            })
                    .textConfirm("Done") //text of confirm button
                    .textCancel("CANCEL") //text of cancel button
                    .btnTextSize(16)
                    .viewTextSize(25)
                    .colorCancel(Color.parseColor("#000000")) //color of cancel button
                    .colorConfirm(Color.parseColor("#000000"))//color of confirm button
                    .build()
            pickerPopWin.showPopWin(activity!!, ccDialog)

        }

        ivPlus.setColorFilter(resources.getColor(R.color.lightblue))
        ccNever.setOnClickListener {
            dialog.dismiss()
        }

        tvMsg.setOnClickListener {
            showRule()
        }

        ccMeet.setOnClickListener {
            showCategoryDialog(etMeet)

        }
        ccInvitation.setOnClickListener {
            tvMeetValue = etMeet.text.toString()
            tvSubjectValue = etType1.text.toString()
            tvReasonValue = etType2.text.toString()
            tvTimeValue = tvTime.text.toString()
            tvLocationValue = etWLocation.text.toString()
            if (validation(dialog)) {
                getShedule()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun showRule() {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_rule, activity!!)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))
        val tvRules = dialog.findViewById<TextView>(R.id.tvRules)
        Utility().setHtmlText(tvRules, result?.get(0)!!.rule_tips)

        val tvGot = dialog.findViewById<TextView>(R.id.tvGot)

        tvGot.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun showCategoryDialog(etMeet: TextView) {
        val view = layoutInflater.inflate(R.layout.category_list, null)
        val dialog = BottomSheetDialog(activity!!)
        dialog.setContentView(view)

        val categoryAdapter = MeetAdapter(activity!!, friendList!!, object :
                MeetAdapter.GetClick {
            override fun dataClick(position: Int) {
                dialog.dismiss()
                friendId = friendList?.get(position)?.user_id
                etMeet.text = friendList?.get(position)?.user_name
                etMeet.setTextColor(Color.parseColor("#A9A9A9"))
            }
        })

        val manager: RecyclerView.LayoutManager =
                LinearLayoutManager(activity!!)
        dialog.rvList.layoutManager = manager
        dialog.rvList.adapter = categoryAdapter
        dialog.show()
    }


    fun validation(ccDialog: Dialog): Boolean {

        val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val  currentdate = Utility().getDateFormated("dd/MM/yyyy", "yyyy/MM/dd", currentDate)

        Log.e("dkjadhbj",currentDate)
        Log.e("dkjadsfshbj",currentdate)
        val value = "$tvDayValue/$tvMonthValue/$tvYearValue"
        date = Utility().getDateFormated("MMM/dd/yyyy", "yyyy/MM/dd", value)
        Log.e("date",date)

        when {
            tvMeetValue.isNullOrEmpty() || tvMeetValue == "Meet with:"-> {
                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccMeet)
                return false
            }
            tvSubjectValue.isNullOrEmpty() -> {

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccType1)

                return false
            }
            tvReasonValue.isNullOrEmpty() -> {

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccType2)

                return false
            }
            tvDayValue.isNullOrEmpty() || tvDayValue == "Day" -> {

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccDate)

                return false
            }
            tvMonthValue.isNullOrEmpty() || tvMonthValue == "Month" -> {

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccMonth)
                return false
            }
            tvYearValue.isNullOrEmpty() || tvYearValue == "Year" -> {

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccYYYY)

                return false
            }
            tvTimeValue.isNullOrEmpty()  || tvTimeValue == "Time"-> {

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccTime)
                return false
            }
            tvMedValue.isNullOrEmpty() -> {

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccDes)
                return false
            }
            tvLocationValue.isNullOrEmpty() -> {

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccDialog.ccWriteLocation)
                return false
            }
            date.compareTo(currentdate) < 0-> {
                Toast.makeText(activity,"Please Select Correct Date", Toast.LENGTH_LONG).show()
                Log.e("vkjdfnk",date.compareTo(currentdate).toString())
                return false
            }
            else -> return true
        }

    }


    fun getShedule() {
        val value = "$tvDayValue/$tvMonthValue/$tvYearValue/"
        date = Utility().getDateFormated("MMM/dd/yyyy", "yyyy/MM/dd", value)
        Log.e("date",date)

        Log.e("value", "$tvTimeValue $tvMedValue")
        val map = HashMap<String, String>()
        map["meeting_user_id"] = Utility().getUser()?.user_id.toString()
        map["meeting_friend_id"] = friendId.toString()
        map["meeting_subject"] = tvSubjectValue.toString()
        map["meeting_reason"] = tvReasonValue.toString()
        map["meeting_date"] = date
        map["meeting_time"] = "$tvTimeValue $tvMedValue"
        map["meeting_location"] = tvLocationValue.toString()
        apiController.makeCall(AppConstant.schedule_meeting, map)
    }



    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if (tag == AppConstant.get_meet_content) {
            val model = apiController.parseJson(response, MeetContentBean::class.java)
            result = model.result

        } else if (tag == AppConstant.get_friend_list) {

            val model = apiController.parseJson(response, FriendListBean::class.java)
            friendList = model.result
        }
        else if(tag == AppConstant.schedule_meeting){
            val model = apiController.parseJson(response,BaseApiResponse::class.java)
            Utility().showSnackBar(activity!!,model.message)
          //  fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
            replaceFragment(PendingEventFragment(), "pending")
        }
    }

    override fun onFailure(tag: String, msg: String) {
       // Utility().showSnackBar(activity!!, msg)
    }

    override fun onError(tag: String, msg: String) {
       // Utility().showSnackBar(activity!!, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!, msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }


}