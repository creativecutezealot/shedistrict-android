package com.appentus.shedistrict.view.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appentus.nutrition.ui.fragments.EventFragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.GenricTextWatcher
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.SheDistrict.activity
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.ChatAdapter
import com.appentus.shedistrict.adapters.ReasonAdapter
import com.appentus.shedistrict.models.BaseApiResponse
import com.appentus.shedistrict.models.MeetContentBean
import com.appentus.shedistrict.models.MessageBean
import com.appentus.shedistrict.models.SendMessageBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.fragments.SearchProfile
import com.appentus.shedistrict.view.fragments.UserProfile
import com.asksira.bsimagepicker.BSImagePicker
import com.asksira.bsimagepicker.Utils
import com.bumptech.glide.Glide
import com.google.android.gms.common.internal.Constants
import io.embrace.android.embracesdk.annotation.StartupActivity
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_create_profile.*
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@StartupActivity
class ChatActivity : AppCompatActivity(), ResponseListener,BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.ImageLoaderDelegate {
    lateinit var apiController: ApiController
    var result: List<MeetContentBean.ResultBean>? = null
    var chatResult: MutableList<MessageBean.ResultBean>? = null
    var friendId : String = "1"
    var   created : String = ""
    var   firstsend : String = ""
    var   meetingstatus : String = ""
    var   starteddatefirst : String = ""
    var   currentdate  : String = ""
    var   notificationid  : String = ""
    lateinit var picker: BSImagePicker
    var file: File? = null
    var uri:Uri? = null

    lateinit var  mReceiver: BroadcastReceiver
    private var chatAdapter: ChatAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        apiController = ApiController(this, this)

        getPicker()

        result = ArrayList()
        chatResult = ArrayList()
        apiController.makeCallGET(AppConstant.get_meet_content)
        getData()
        getMessage()
        mReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.getStringExtra("type") ==AppConstant.NotificationEventFilter.invitation_send) {
                //  requestGetChatList(sID!!,true)
                showDialogWeekMeet("yes")
            }
            if (intent.getStringExtra("type") == AppConstant.NotificationEventFilter.invitation_accept) {
                //  requestGetChatList(sID!!,true)
                showDialogWeekMeet("yes")

            }
        }
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mReceiver,
                IntentFilter(AppConstant.NotificationEventFilter.invitation_send)
        )


        setstatus()

        ivCamera.setOnClickListener {

            picker.show(supportFragmentManager, "picker")

        }

    }

    private fun getPicker() {
        picker = BSImagePicker.Builder("com.appentus.shedistrict.fileprovider")
                .setSpanCount(3)
                .hideGalleryTile()
                .setGridSpacing(Utils.dp2px(2))
                .setPeekHeight(Utils.dp2px(360))
                .build()
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        ivImageSend.visibility=View.VISIBLE
        etMessage.visibility=View.GONE
        file = File(uri!!.path!!)
        this.uri = uri
        Glide.with(this).load(uri)
                .placeholder(R.drawable.ic_logo).into(ivImageSend)

    }


    private fun setstatus() {
        val map: java.util.HashMap<String, String> = java.util.HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["status"] = "1"
        apiController.makeCallSilent(AppConstant.update_online_offline, map)
    }

    fun getData() {
        ivBack.setColorFilter(this.resources.getColor(R.color.txtpink))
        ivBack.setOnClickListener {
            onBackPressed()
        }

        etMessage.addTextChangedListener(GenricTextWatcher("message", etMessage,ivSend))

        ivReport.visibility = View.VISIBLE

        if (intent != null) {
            titleTop.text = intent.getStringExtra("text")
            titleTop.setTextColor(Color.parseColor("#000000"))
            friendId = intent.getStringExtra("id")

            if (intent.getStringExtra("type") ==AppConstant.NotificationEventFilter.invitation_send) {
                notificationid=intent.getStringExtra("notificationid")
                showDialogWeekMeet("yes")
            }

           if(intent.getStringExtra("type") ==AppConstant.NotificationEventFilter.invitation_accept){

                showDialogWeekMeet("yes")

            }
            if(intent.getStringExtra("type") ==AppConstant.NotificationEventFilter.invitation_deny){

                showDialogWeekMeet("first")

            }

        }

        if(intent.hasExtra("type")){
            titleTop.text = intent.getStringExtra("text")
            titleTop.setTextColor(Color.parseColor("#000000"))
            friendId = intent.getStringExtra("id")
        }

        titleTop.setOnClickListener {
            val nextIntent = Intent(this!!,HomeActivity::class.java)
            nextIntent.putExtra("userid",friendId)
            nextIntent.putExtra("aftrchat","aftrchat")
            startActivity(nextIntent)
        }

        ivReport.setOnClickListener {

            showDialogReport()
        }

        ivCamera.setColorFilter(this.resources.getColor(R.color.txtpink))



        llSend.setOnClickListener {
            var message = etMessage.getText().toString()

            Log.e("etMessage",etMessage.getText().toString().trim().length.toString())
            Log.e("file",file.toString())
            Log.e("etMessagetxt",etMessage.getText().toString())

           if(etMessage.getText().toString().trim().length ==0 && file==null && etMessage.text.toString().isNullOrEmpty() ){


           }else if(etMessage.getText().toString().trim().length ==0 && file==null) {

           }else{

               createMessage(etMessage.text.toString())
               etMessage.text=null
               file=null
           }

            Log.e("edittext",etMessage.text.toString())
        }

        /*ivLoading.visibility= View.VISIBLE
        Glide.with(this).load(uri)
                .placeholder(R.drawable.ic_logo).into(ivLoading)*/

    }

    fun setAdapter() {
         chatAdapter = ChatAdapter(this,chatResult!!)
        val mamager = LinearLayoutManager(this)
        mamager.stackFromEnd=true
        rvChat.layoutManager=mamager
        rvChat.adapter = chatAdapter
        val  itemAnimator : (SimpleItemAnimator)
        itemAnimator= rvChat.getItemAnimator() as SimpleItemAnimator
        itemAnimator.supportsChangeAnimations=false
    }


    private fun showDialogReport() {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_report, this)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvReport = dialog.findViewById<TextView>(R.id.tvReport)
        val tvBlock = dialog.findViewById<TextView>(R.id.tvBlock)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))

        tvReport.setOnClickListener {
            showDialogReasonReport()
            dialog.dismiss()
        }

        tvBlock.setOnClickListener {
            blockapi()

            dialog.dismiss()
        }
        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun blockapi() {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["block_user_id"] = friendId
        map["reason"] = ""
        apiController.makeCall(AppConstant.user_block, map)
    }

    private fun showDialogReasonReport() {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_reason_report, this)
        val ccDialog = dialog.findViewById<RelativeLayout>(R.id.ccDialog)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val rvReason = dialog.findViewById<RecyclerView>(R.id.rvReason)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))

        val list = ArrayList<String>()
        list.add("She's harassing me")
        list.add("She's annoying")
        list.add("Wants to meet too soon")
        list.add("I think her profile is fake")
        list.add("She's being inappropriate")

        val reasonAdapter = ReasonAdapter(this, list, object : ReasonAdapter.GetClick {
            override fun click(position: Int) {
                onReport(list[position])
                dialog.dismiss()
            }
        })
        rvChat.layoutManager = LinearLayoutManager(this)
        rvReason.adapter = reasonAdapter

        ivClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun showDialogReasonUpdate(type: String) {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_report_reason_update, this)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvMsg1 = dialog.findViewById<TextView>(R.id.tvMsg2)
        val tvMsg2 = dialog.findViewById<TextView>(R.id.tvMsg3)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))

        if (type == "reason") {
            tvMsg.text = getString(R.string.reportUpdateMessage)
            tvMsg1.text = getString(R.string.reportUpdateMessage1)
            tvMsg2.text = getString(R.string.reportUpdateMessage2)
        } else if (type == "block") {
            tvMsg.text = getString(R.string.blockUpdateMessage)
            tvMsg1.text = getString(R.string.blockUpdateMessage1)
            tvMsg2.text = getString(R.string.blockUpdateMessage2)
        }
        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun showDialogWeekMeet(type: String) {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_twoweek, this)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(30f))
        val tvYes = dialog.findViewById<TextView>(R.id.tvYes)
        val tvNo = dialog.findViewById<TextView>(R.id.tvNo)
        val tvHow = dialog.findViewById<TextView>(R.id.tvHow)
        val ccYes = dialog.findViewById<CardView>(R.id.ccYes)

        val ccNo = dialog.findViewById<CardView>(R.id.ccNo)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvMsg2 = dialog.findViewById<TextView>(R.id.tvMsg2)
        val tvMsg3 = dialog.findViewById<TextView>(R.id.tvMsg3)
        if (type == "yes") {
            tvMsg.text = "Paused!"
            tvMsg2.visibility = View.GONE
            tvMsg3.visibility = View.GONE
            tvYes.text = "Ready to meet"
            tvNo.text = "Don't want to meet"
            tvHow.text = "Why is our conversation paused?"
        }


        ccNo.setOnClickListener {
            if(intent.getStringExtra("type") ==AppConstant.NotificationEventFilter.invitation_send){
                meetRequest("2")
                dialog.dismiss()
            }
            else{
                startActivity(Intent( this!!,HomeActivity::class.java))
            }
        }

        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        tvHow.setOnClickListener {
            if (type == "first") {
                showHow()
                dialog.dismiss()
            } else if (type == "yes") {

                dialog.dismiss()
            }

        }

        ccYes.setOnClickListener {
            if (type == "first") {
                //showDialogWeekMeet("yes")
                dialog.dismiss()
                sendRequest()
            } else if (type == "yes") {
                if(intent.getStringExtra("type") ==AppConstant.NotificationEventFilter.invitation_send){
                    meetRequest("1")
                    dialog.dismiss()
                }
                if (intent.getStringExtra("type") ==AppConstant.NotificationEventFilter.invitation_accept) {
                    val fragment = EventFragment()
                    val bundle = Bundle()
                    bundle.putString("friendId",friendId)
                    bundle.putString("friendname", intent.getStringExtra("text"))
                    bundle.putString("userid",Utility().getUser()?.user_id)
                    bundle.putString("chatmeet","chatmeet")
                    fragment.arguments = bundle
                    (this as HomeActivity).replaceFragment(fragment, "search")

                }
               // dialog.dismiss()

            }
        }
        dialog.show()
    }

    private fun meetRequest(s: String) {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["id"] = notificationid
        map["type"] = s
        apiController.makeCall(AppConstant.status_meet_request, map)
    }

    private fun sendRequest() {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["friend_id"] = friendId
        apiController.makeCall(AppConstant.send_meet_request, map)

    }


    private fun showHow() {

        val dialog = Utility().fullScreenDialog(R.layout.dialog_how, this)
        val ccDialog = dialog.findViewById<RelativeLayout>(R.id.ccDialog)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvMsg2 = dialog.findViewById<TextView>(R.id.tvMsg2)
        val tvMsg4 = dialog.findViewById<TextView>(R.id.tvMsg4)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))
        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        Utility().setHtmlText(tvMsg2, result?.get(0)!!.how_its_work!!)
        Utility().setHtmlText(tvMsg4, result?.get(0)!!.why!!)

        tvMsg4.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf("Lisa@SheDistrict.com")
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.type = "text/html"
            intent.setPackage("com.google.android.gm")
            startActivity(Intent.createChooser(intent, "Send mail"))
        }

        dialog.show()
    }

    fun onReport(reason:String) {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["friend_id"] = friendId
        map["reason"] = reason
        map["type"] = "user"
        apiController.makeCall(AppConstant.user_report, map)
    }

    fun createMessage(text: String) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("user_id", Utility().getUser()!!.user_id)
        builder.addFormDataPart("fri_id", friendId)
        if(text==null){

            builder.addFormDataPart("message","")
        }else{
            builder.addFormDataPart("message", text)
        }

        Log.e("usercknskid", Utility().getUser()!!.user_id)
        Log.e("frientidsasad",friendId)
        Log.e("text",text)
        if (file == null) {
            builder.addFormDataPart("file", "")
        } else {
            builder.addFormDataPart("file", file?.name, RequestBody.create(MediaType.parse("multipart/form-data"), file!!))
        }

        apiController.makeCall(AppConstant.send_message, builder.build())

                 }

    fun getMessage(){
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["fri_id"] = friendId
        apiController.makeCall(AppConstant.get_message, map)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSuccess(tag: String, response: String) {
        if (tag == AppConstant.get_meet_content) {
            val model = apiController.parseJson(response, MeetContentBean::class.java)
            result = model.result
        }

        else if (tag == AppConstant.send_message) {
            apiController.loader.dismiss()
            ivImageSend.visibility=View.GONE
            etMessage.visibility=View.VISIBLE

            val data = apiController.parseJson(response, SendMessageBean::class.java)
            val msg = MessageBean.ResultBean()
         /*   val encodedString: String = Base64.getEncoder().encodeToString(data.result.chat_message.toByteArray())
*/
            msg.chat_id= data.result.chat_id
            msg.chat_room_id= data.result.chat_room_id
            msg.chat_message= data.result.chat_message
            msg.chat_file= data.result.chat_file
            msg.chat_file_type= data.result.chat_file_type
            msg.created= data.result.created
            msg.room_id= data.result.room_id
            msg.fri_id= data.result.received_id
            msg.user_id= data.result.sender_id
            msg.userprofile= Utility().getUser()!!.user_profile
            chatResult!!.add(msg)
            etMessage.setText("")
            rvChat.adapter!!.notifyItemInserted(chatResult!!.size-1)
            rvChat.scrollToPosition(chatResult!!.size-1)
            val  itemAnimator : (SimpleItemAnimator)
            itemAnimator= rvChat.getItemAnimator() as SimpleItemAnimator
            itemAnimator.supportsChangeAnimations=false
          //  rvChat.adapter!!.notifyItemRangeInserted(chatResult!!.size-1,1)

         //   rvChat.adapter!!.notifyDataSetChanged()

        }
        else if(tag == AppConstant.get_message){
            val model = apiController.parseJson(response, MessageBean::class.java)
            chatResult = model.result
            setAdapter()
            created=model.result[0].created
            firstsend=model.result[0].first_send
            meetingstatus=model.meeting_status
            Log.e("created",created)
            Log.e("firstsenddsad",firstsend)

             starteddatefirst= created.substring(0, created.indexOf(" "))

            val format1 = SimpleDateFormat("yyyy-MM-dd")
            val format2 =  SimpleDateFormat("dd/MM/yyyy")
            val date = format1.parse(starteddatefirst)
            val  enddate=format2.format(date)
            Log.e("starteddate",starteddatefirst)
            Log.e("enddate",enddate)



            val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
          //  currentdate = Utility().getDateFormated("dd/MM/yyyy", "MM/dd/yyyy", currentDate)
            Log.e("currentdate", enddate.compareTo(currentDate).toString())
            Log.e("currentdate",currentdate)

            if(Utility().getUser()!!.user_id.equals(firstsend)&& enddate.compareTo(currentDate)>14) {
                Handler().postDelayed(Runnable {

                    showDialogWeekMeet("first")

                }, 30)

            }
            if(meetingstatus.equals("2")){
                showDialogWeekMeet("first")
            }

        }
        else if(tag == AppConstant.user_report){
            val model = apiController.parseJson(response, BaseApiResponse::class.java)
            Utility().showSnackBar(this, model.message)
            showDialogReasonUpdate("reason")
        }else if(tag==AppConstant.user_block){
            showDialogReasonUpdate("block")
        }else if(tag==AppConstant.send_meet_request){
            Utility().showSnackBar(this,"Request send Successfully")
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


    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(tag)
//            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
        fragmentTransaction.replace(R.id.cc, fragment, tag)
        fragmentTransaction.commit()
    }

    override fun loadImage(imageFile: File?, ivImage: ImageView?) {
        Glide.with(this).load(imageFile)
                .placeholder(R.drawable.ic_logo).into(ivImage!!)
    }
}
