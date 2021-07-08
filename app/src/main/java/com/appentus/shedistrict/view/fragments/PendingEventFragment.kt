package com.appentus.nutrition.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.PendingEventAdapter
import com.appentus.shedistrict.models.EventBean
import com.appentus.shedistrict.models.UpdateEventBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_your_announcement.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


class PendingEventFragment : Fragment(),ResponseListener{

    lateinit var  apiController: ApiController
    var status:String? = null
    var  result : MutableList<EventBean.PendingBean>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_your_announcement, container, false) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!,this)
        result = ArrayList()
        getData()

    }

    fun getData(){
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_events, map)
    }

    fun setStatus(staus:String,meetingId : String){
        val map = HashMap<String, String>()
        map["status"] = staus
        map["meeting_id"] = meetingId
        apiController.makeCall(AppConstant.update_event_status, map)
    }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.get_events){
            val model = apiController.parseJson(response,EventBean::class.java)
            if(!model.pending.isNullOrEmpty()){
                result = model.pending
                rvItem.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout)
                rvItem.layoutManager = LinearLayoutManager(activity)
                rvItem.adapter = PendingEventAdapter(activity!!,result!!,object : PendingEventAdapter.GetClick{
                    override fun getStatus(position:Int) {
                        if(result!![position].type == "received"){
                            showDialog(position)

                        }else {

                        }
                    }
                })
            }

        }
        else if(tag== AppConstant.update_event_status){
            val model = apiController.parseJson(response,UpdateEventBean::class.java)
            val result = model.result

            getData()
            showDialogConfirm()

            val calIntent = Intent(Intent.ACTION_INSERT)
            calIntent.type = "vnd.android.cursor.item/event"
            calIntent.putExtra(CalendarContract.Events.TITLE,model.result.meeting_subject)
            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, result.meeting_location)
            calIntent.putExtra(CalendarContract.Events.DESCRIPTION, result.meeting_reason)
            calIntent.putExtra(CalendarContract.Events.ALLOWED_REMINDERS,true)

            val dateStrs = result.meeting_date.split("/")

            val calDate = GregorianCalendar(dateStrs[0].toInt(), dateStrs[1].toInt()-1, dateStrs[2].toInt())
            //calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, true)
            //var sdf = SimpleDateFormat("HH:mm:ss")
            //var date = Date(sdf.format(result[i].meeting_time)).time - (15*60)

           val time= result.meeting_date +" "+result.meeting_time
            Log.e("erfejtbjek",time)
            val  timeInMilSeconds = milliseconds(time)
            Log.e("timeInMilSeconds",timeInMilSeconds.toString())
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeInMilSeconds)
            startActivity(calIntent)


        }
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


    private fun showDialog(position:Int){
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_invited, activity!!)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val ccAccept = dialog.findViewById<CardView>(R.id.ccAccept)
        val ccDeny = dialog.findViewById<CardView>(R.id.ccDeny)
        val ccWaiting = dialog.findViewById<CardView>(R.id.ccWaiting)
        val tvMsgWhat = dialog.findViewById<TextView>(R.id.tvMsgWhat)
        val tvMsgWhere = dialog.findViewById<TextView>(R.id.tvMsgWhere)
        val tvMsgWhen = dialog.findViewById<TextView>(R.id.tvMsgWhen)
        val tvMsgTime = dialog.findViewById<TextView>(R.id.tvMsgTime)

        tvMsgWhat.text = result!![position].meeting_reason
        tvMsgWhere.text = result!![position].meeting_location
        tvMsgWhen.text = result!![position].meeting_date
        tvMsgTime.text = result!![position].meeting_time

        ivClose.setColorFilter(activity!!.resources.getColor(R.color.lightblue))
        ivClose.setOnClickListener { dialog.dismiss() }
        ccAccept.setOnClickListener {
            status = "accept"
            setStatus("1",result!![position].meeting_id)
            dialog.dismiss()
        }
        ccDeny.setOnClickListener {

            status = "deny"
            setStatus("2",result!![position].meeting_id)
            dialog.dismiss()
        }
        ccWaiting.setOnClickListener {
            status = "think"
            setStatus("3",result!![position].meeting_id)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDialogConfirm() {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_confirm_invitation, activity!!)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvWarring = dialog.findViewById<TextView>(R.id.tvWarring)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        if (status == "accept") {
            tvMsg.text = "Awesome!"
            tvWarring.text = "You accepted an invitation from \n " + "(username), Check under your \"events\" tab \n " + "to keep track of your upcoming and \n pending events!"
        } else if (status == "deny") {
            tvMsg.text = "Well that's okay! No pressure."
            tvWarring.text = "However, if you change your mind, you still  \n " + "can create your own event and invite \n" + "(username)."
        } else if (status == "think")
        {
            tvMsg.text = "You've been invited!"
            tvWarring.text = "If you change your mind, your invitation is \n " + "saved at pending invitations."
        }

        ccDialog.background = RoundView(activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))
        tvMsg.setTextColor(activity!!.resources.getColor(R.color.lightblue))
        ivClose.setColorFilter(activity!!.resources.getColor(R.color.lightblue))
        ivClose.setOnClickListener {

            dialog.dismiss()


        }
        dialog.show()
    }

    fun milliseconds(date: String): Long {

        val sdf =  SimpleDateFormat("yyyy/MM/dd hh:mm a")
        try {
            val mDate = sdf.parse(date)
            val timeInMilliseconds = mDate.getTime()
            println("Date in milli :: $timeInMilliseconds")
            return timeInMilliseconds

        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return 0
    }


}