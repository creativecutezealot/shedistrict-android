package com.appentus.shedistrict.view.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.*
import com.appentus.shedistrict.adapters.*
import com.appentus.shedistrict.models.*
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.ChatActivity
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_ceo_profile.*
import kotlinx.android.synthetic.main.fragment_message_for_ceo.*
import kotlinx.android.synthetic.main.fragment_message_for_ceo.insta
import kotlinx.android.synthetic.main.fragment_message_for_ceo.twitter
import kotlinx.android.synthetic.main.fragment_search_profile.*
import kotlinx.android.synthetic.main.fragment_search_profile.ivChat
import kotlinx.android.synthetic.main.fragment_search_profile.ivInfo
import kotlinx.android.synthetic.main.fragment_search_profile.ivMenu
import kotlinx.android.synthetic.main.fragment_search_profile.ivProfile
import kotlinx.android.synthetic.main.fragment_search_profile.rvFriends
import kotlinx.android.synthetic.main.fragment_search_profile.tvMsg1
import kotlinx.android.synthetic.main.fragment_search_profile.tvMsg2
import kotlinx.android.synthetic.main.fragment_search_profile.tvName
import kotlinx.android.synthetic.main.toolbar_title.*
import org.json.JSONObject
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class CeoProfile : Fragment(), ResponseListener {
    lateinit var apiController: ApiController
    var json= JSONObject()
    var instagrams=""
    var twitters=""
    var valuelist = java.util.ArrayList<String>()
    lateinit var jsonvalue: JSONObject
    lateinit var jsonPersonal: JSONObject
    lateinit var jsonIntrests: JSONObject
    var dateList: MutableList<DateAndTimeBean>? = null
    var month: MutableList<DateAndTimeBean>? = null
    var yearList: MutableList<DateAndTimeBean>? = null
    var username= ""
    var userid= ""
    lateinit var Profile: CircleImageView
    lateinit var replace: TextView
    var date = ""
    var religion = ""
    var education = ""
    lateinit var list: MutableList<String>
    var IList: MutableList<IntrestBean.ResultBean>? = null
    lateinit var personalInfoList: MutableList<PrefrenceBean.ResultBean>
    var createProfileFile: File? = null
    var pList: ArrayList<InfoBean> = ArrayList()
    var iList: ArrayList<InfoBean> = ArrayList()
    var imagesList: MutableList<UserProfileBean.ResultBean.UserPhotosBean> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ceo_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pList = ArrayList()
        iList = ArrayList()
        list = java.util.ArrayList()
        dateList = java.util.ArrayList()
        month = java.util.ArrayList()
        yearList = java.util.ArrayList()
        personalInfoList=ArrayList()

        apiController = ApiController(activity!!, this)
        apiController.makeCallGET(AppConstant.get_interest)
        apiController.makeCallGET(AppConstant.get_preference)
        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        apiController = ApiController(activity!!, this)
        titleTop.text = "CEO"
        ivBack.setOnClickListener {
            activity!!.onBackPressed()

        }

        apiController.loader.show()
        apiController.makeCallGET(AppConstant.app_content)

        twitter.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(twitters)
            startActivity(i)
        }

        insta.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(instagrams)
            startActivity(i)
        }

        getProfilePreference()

        ivInfo.setOnClickListener() {
            /*  if (getArguments()?.getString("profile").toString().equals("profile")) {

                  dialogPersonalIntreat()
              } else {*/
            dialogAddInterests()
            // }

        }


        ivChat.setOnClickListener() {
            val nextIntent = Intent(activity!!, ChatActivity::class.java)
            nextIntent.putExtra("text", username)
            nextIntent.putExtra("id", userid)
            startActivity(nextIntent)
        }

        ivMenu.setOnClickListener() {
            showDialogReport()
        }


    }

    private fun getProfilePreference() {
        val map = HashMap<String, String>()
        Log.e("gkjregtjr",getArguments()?.getString("userid"))
        Log.e("sjfvgjjgjhf", Utility().getUser()?.user_id)
        map["user_id"] = getArguments()?.getString("userid").toString()
        map["friend_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_user_profile, map)

    }

    fun dialogAddInterests() {
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_hobbies, activity!!)
        val ccDialog = dialog.findViewById<LinearLayout>(R.id.root)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))
        val hobby = dialog.findViewById<TextView>(R.id.tvPersonalInfo)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val interest = dialog.findViewById<TextView>(R.id.interest)
        val rvItem = dialog.findViewById<RecyclerView>(R.id.rvItem)
        val nvScroll = dialog.findViewById<NestedScrollView>(R.id.nvScroll)
        val ccSubmit = dialog.findViewById<CardView>(R.id.ccSubmit)

        ivClose.setColorFilter(Color.parseColor("#000000"))

        ccSubmit.visibility = View.GONE



        hobby.text = "Personal Info"

        rvItem.layoutManager = LinearLayoutManager(activity!!)
        rvItem.adapter = InfoAdapter(activity!!, pList)
        rvItem.isNestedScrollingEnabled = false

        hobby.setOnClickListener {
            interest.setTextColor(Color.parseColor("#919191"))
            hobby.setTextColor(Color.parseColor("#000000"))
            rvItem.layoutManager = LinearLayoutManager(activity!!)
            rvItem.adapter = InfoAdapter(activity!!, pList)
            Log.e("sdfjshj", pList.toString())
            rvItem.isNestedScrollingEnabled = false
            nvScroll.scrollTo(0, 0)
            interest.alpha = 0.6F
        }

        interest.setOnClickListener {
            interest.setTextColor(Color.parseColor("#000000"))
            hobby.setTextColor(Color.parseColor("#919191"))
            rvItem.layoutManager = LinearLayoutManager(activity!!)
            rvItem.adapter = InfoAdapter(activity!!, iList)
            rvItem.isNestedScrollingEnabled = false
            nvScroll.scrollTo(0, 0)
        }

        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDialogReport() {

        val dialog = Utility().fullScreenDialog(R.layout.dialog_report, activity!!)
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
            showDialogReasonUpdate("block")
            dialog.dismiss()
        }
        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDialogReasonReport() {

        val dialog = Utility().fullScreenDialog(R.layout.dialog_reason_report, activity!!)
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

        val reasonAdapter = ReasonAdapter(activity!!, list, object : ReasonAdapter.GetClick {
            override fun click(position: Int) {
                onReport(list[position])
                dialog.dismiss()
            }
        })
        // rvChat.layoutManager = LinearLayoutManager(activity!!)
        rvReason.adapter = reasonAdapter

        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun onReport(reason: String) {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["friend_id"] = getArguments()?.getString("userid").toString()
        map["reason"] = reason
        map["type"] = "user"
        apiController.makeCall(AppConstant.user_report, map)
    }

    private fun showDialogReasonUpdate(type: String) {

        val dialog = Utility().fullScreenDialog(R.layout.dialog_report_reason_update, activity!!)
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


    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if(tag == AppConstant.app_content){
            val model = apiController.parseJson(response, AppContentBean::class.java)
            tvtitle2.text = model.result[0].ceo_msg
            json = JSONObject(model.result[0].ceo_social_link)
            val iter = json.keys()
            while (iter.hasNext()) {
                val key = iter.next()
                if(key=="instagram"){
                    instagrams=json.getString(key)
                    Log.e("ins",json.getString(key))
                }else{
                    twitters=json.getString(key)
                    Log.e("tw",json.getString(key))
                }

            }

        }else if (tag == AppConstant.get_user_profile) {
            val model = apiController.parseJson(response, UserProfileBean::class.java)

            tvMsgCeo1.setText(model.result.about_me)
            tvMsg2ceo.setText(model.result.friend_like)
            tvNames.text=model.result.user_name
            username = model.result.user_name
            userid = model.result.user_id
            twitters= model.result.twitter_link

            tvAboutprofile.setText("About me")
            imagesList = model.result.user_photos

            Glide.with(this).load(AppConstant.ImageUrl+model.result.user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(ivProfileceo)
            rvFriends.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            rvFriends.adapter = UserPhotoAdapter(activity!!, imagesList, object : RecyclerViewClick {
                override fun onClickPos(pos: Int) {

                }

                })

            if (!model?.result.personal_info.isNullOrEmpty()) {
                jsonPersonal = JSONObject(model?.result.personal_info);
                val iter = jsonPersonal.keys()
                while (iter.hasNext()) {
                    val key = iter.next()
                    pList.add(InfoBean(key, jsonPersonal.getString(key)))
                    Log.e("sjgd", key)

                }
            }

            jsonIntrests = JSONObject(model?.result.interest_info);
            val iters = jsonIntrests.keys()
            while (iters.hasNext()) {
                val key = iters.next()
                iList.add(InfoBean(key, jsonIntrests.getString(key)))
            }
        /*    if (model.result.is_user_like.equals("0")) {
                ivFav.setImageResource(R.drawable.dislike)

            } else {
                ivFav.setImageResource(R.drawable.like)

            }*/
        }else if (tag == AppConstant.user_report) {
            val model = apiController.parseJson(response, BaseApiResponse::class.java)
            // Utility().showSnackBar(activity!!, model.message)
            showDialogReasonUpdate("reason")
        }else if (tag == AppConstant.get_interest) {
            val model = apiController.parseJson(response, IntrestBean::class.java)
            IList = model.result

        } else if (tag == AppConstant.get_preference) {
            val model = apiController.parseJson(response, PrefrenceBean::class.java)
            personalInfoList = model.result

        } else if (tag == AppConstant.update_user_profile) {
            val model = apiController.parseJson(response, EditProfileBean::class.java)
            PrefManager.putString(AppConstant.userInfo, response)
            PrefManager.putBoolean(AppConstant.userSession, true)
            PrefManager.putString("UserId", model.result.user_id)
            Utility().showSnackBar(activity!!,"Successfully Update")

        }
    }
    override fun onFailure(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onError(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!,msg)
    }

}
