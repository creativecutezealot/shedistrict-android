package com.appentus.nutrition.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.AttendingEventAdapter
import com.appentus.shedistrict.models.DaysBaseModel
import com.appentus.shedistrict.models.EventBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_your_announcement.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AttendingEventFragment : Fragment(),ResponseListener{

    lateinit var apiController: ApiController
    var result = mutableListOf<EventBean.AttendingBean>()
    lateinit var dayWiseResult : MutableList<DaysBaseModel>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_your_announcement, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!,this)
        dayWiseResult=ArrayList()

        getData()

    }

    fun getData(){

        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_events, map)

       }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.get_events){
            val model = apiController.parseJson(response,EventBean::class.java)
            if(!model.attending.isNullOrEmpty()){
                result = model.attending

                filterData(result!!)

            }

        }
    }


    private fun filterData(result: MutableList<EventBean.AttendingBean>) {

        for (attendingBean in result) {
            when {
                Utility().getDateAgo("yyyy/MM/dd", attendingBean.meeting_date) == 0 -> {

                    if (checkCurrentData("Today")==-1){

                        var  list=ArrayList<EventBean.AttendingBean>()

                        list.add(attendingBean)
                        dayWiseResult.add(DaysBaseModel("Today",list))
                    }else{
                        dayWiseResult[checkCurrentData("Today")].listResult.add(attendingBean)
                    }

                }
                Utility().getDateAgo("yyyy/MM/dd", attendingBean.meeting_date) == 1 -> {


                    if (checkCurrentData("Yesterday")==-1){

                        var  list=ArrayList<EventBean.AttendingBean>();

                        list.add(attendingBean)
                        dayWiseResult.add(DaysBaseModel("Yesterday",list))
                    }else{
                        dayWiseResult[checkCurrentData("Yesterday")].listResult.add(attendingBean)
                    }
                }
                Utility().getDateAgo("yyyy/MM/dd", attendingBean.meeting_date) == 3 -> {
                    if (checkCurrentData("Upcoming")==-1){

                        var  list=ArrayList<EventBean.AttendingBean>();

                        list.add(attendingBean)
                        dayWiseResult.add(DaysBaseModel("Upcoming",list))
                    }else{
                        dayWiseResult[checkCurrentData("Upcoming")].listResult.add(attendingBean)
                    }
                }
            }
        }

        rvItem.layoutManager=LinearLayoutManager(activity)
        rvItem.adapter=AttendingEventAdapter(activity!!,dayWiseResult)
    }


    private fun checkCurrentData(title: String):Int {
        for (i in 0 until dayWiseResult.size){
            if (dayWiseResult[i].daysType==title){
                return i
            }
        }
        return -1
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }



     open fun MonthSelect(mMonth: String): Int {
        return if (mMonth === "January") {
            0
        } else if (mMonth.equals("February", ignoreCase = true)) {
            1
        } else if (mMonth.equals("March", ignoreCase = true)) {
            2
        } else if (mMonth.equals("April", ignoreCase = true)) {
            3
        } else if (mMonth.equals("May", ignoreCase = true)) {
            4
        } else if (mMonth.equals("June", ignoreCase = true)) {
            5
        } else if (mMonth.equals("July", ignoreCase = true)) {
            6
        } else if (mMonth.equals("August", ignoreCase = true)) {
            7
        } else if (mMonth.equals("September", ignoreCase = true)) {
            8
        } else if (mMonth.equals("October", ignoreCase = true)) {
            9
        } else if (mMonth.equals("Novermber", ignoreCase = true)) {
            10
        } else {
            11
        }
    }

    private fun DaySelect(mDay: String): Int {
        return if (mDay.equals("Monday", ignoreCase = true)) {
            Calendar.MONDAY
        } else if (mDay.equals("Tuesday", ignoreCase = true)) {
            Calendar.TUESDAY
        } else if (mDay.equals("Wednesday", ignoreCase = true)) {
            Calendar.WEDNESDAY
        } else if (mDay.equals("Thursday", ignoreCase = true)) {
            Calendar.THURSDAY
        } else if (mDay.equals("Friday", ignoreCase = true)) {
            Calendar.FRIDAY
        } else if (mDay.equals("Saturday", ignoreCase = true)) {
            Calendar.SATURDAY
        } else {
            Calendar.SUNDAY
        }
    }


}