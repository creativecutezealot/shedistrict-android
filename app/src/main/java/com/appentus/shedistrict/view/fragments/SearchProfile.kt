package com.appentus.shedistrict.view.fragments


import AddInterestAdapter
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
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
import com.appentus.shedistrict.view.activity.HomeActivity
import com.asksira.bsimagepicker.BSImagePicker
import com.asksira.bsimagepicker.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.fragment_search_profile.*
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SearchProfile : Fragment(), ResponseListener, BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.ImageLoaderDelegate {

    lateinit var apiController: ApiController
    var flaglike = "0"
    var flag: Boolean = true
    var remove_or_replace = ""
    var selectss=""
    var valuelist = java.util.ArrayList<String>()
    lateinit var jsonvalue: JSONObject
    lateinit var jsonPersonal: JSONObject
    lateinit var jsonIntrests: JSONObject
    var username= ""
    lateinit var picker: BSImagePicker
     var userid= ""
    lateinit var Profile: CircleImageView
    lateinit var remove: TextView
    lateinit var replace: TextView
    var hobbiesObj: JSONObject? = null
    var dateList: MutableList<DateAndTimeBean>? = null
    var month: MutableList<DateAndTimeBean>? = null
    var yearList: MutableList<DateAndTimeBean>? = null
    var tvDayValue: String? = null
    var tvMonthValue: String? = null
    var tvYearValue: String? = null
    var twitters=""
    var date = ""
    var religion = ""
    var education = ""
    var ethenicity = ""
    var relationship = ""
    var sexual = ""
    var Kids = ""
    var Drinking = ""
    var Smoking = ""
    var Political = ""
    var personalvaluelist = java.util.ArrayList<String>()
    lateinit var personaljsonvalue: JSONObject
    lateinit var json: JSONObject
    lateinit var list: MutableList<String>
    var IList: MutableList<IntrestBean.ResultBean>? = null
    lateinit var personalInfoList: MutableList<PrefrenceBean.ResultBean>
    var createProfileFile: File? = null
    var pList: ArrayList<InfoBean> = ArrayList()
    var iList: ArrayList<InfoBean> = ArrayList()
    var imagesList: MutableList<UserProfileBean.ResultBean.UserPhotosBean> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            if(arguments!=null) {
                 if (arguments!!.containsKey("aftrchat")|| arguments!!.containsKey("viewprofile")) {
                    val nextIntent = Intent(activity!!, ChatActivity::class.java)
                    nextIntent.putExtra("text", username)
                    nextIntent.putExtra("id", userid)
                    nextIntent.putExtra("chat","chat")
                    startActivity(nextIntent)
                     activity!!.finish()
                }
                else{
                     activity!!.onBackPressed()
                }
            }
        }

        getProfilePreference()

        if (Utility().getUser()?.user_like.isNullOrEmpty()) {
            ivFav.visibility = View.GONE
        } else {
            ivFav.visibility = View.VISIBLE
        }

        ivOnline.setColorFilter(this.resources.getColor(R.color.green))
        ivFav.setOnClickListener() {
            if (flaglike == "0") {
                like()
            }
            else {
                dislike()
            }
        }
            ivtwitter.setOnClickListener {
                if(!twitters.isNullOrEmpty()) {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(twitters)
                    startActivity(i)
                }else{
                    Utility().showSnackBar(activity!!,"Twitter account not attached")
                }

            }

        ivInfo.setOnClickListener() {

                dialogAddInterests()

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

        ivAnouncement.setOnClickListener {
            val fragment = AnnouncementListOther()
            val bundle = Bundle()
            bundle.putString("id",userid)
            bundle.putString("username",username)
            fragment.arguments = bundle
            (activity as HomeActivity).replaceFragment(fragment, "Announcment")
        }

        ivProfile.setOnClickListener() {

            val dialog = Utility().fullScreenDialogOnTop(R.layout.dialog_imagechange, activity!!)
            remove = dialog.findViewById<TextView>(R.id.tvRemove)
            replace = dialog.findViewById<TextView>(R.id.tvReplace)
            Profile = dialog.findViewById<CircleImageView>(R.id.ivProfile)
            val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)

            remove.visibility=View.GONE
            replace.visibility=View.GONE

            if (!Utility().getUser()?.user_profile.isNullOrEmpty()) {

                Glide.with(this).load(AppConstant.ImageUrl + Utility().getUser()?.user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(Profile)

            } else {
                Glide.with(this).load(AppConstant.ImageUrl + Utility().getUser()?.user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(Profile)

            }



            ivClose.setOnClickListener() {
                dialog.dismiss()
            }


            dialog.show()
        }




    }



    private fun dislike() {
        val map = HashMap<String, String>()
        map["friend_id"] = arguments?.getString("userid").toString()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.DisLike, map)
    }

    private fun like() {
        val map = HashMap<String, String>()
        Log.e("sjfvgjf", getArguments()?.getString("userid").toString())

        map["friend_id"] = getArguments()?.getString("userid").toString()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.Like, map)
    }

    private fun getProfilePreference() {
        val map = HashMap<String, String>()
        Log.e("sjfvgjf", getArguments()?.getString("userid").toString())
        Log.e("sjfvgjjgjhf", Utility().getUser()?.user_id.toString())
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

    private fun Update() {
        val hobbiesObj = JSONObject()
        for (i in 0 until personalInfoList.size) {
           if(personalInfoList[i].selectedValue==null){
               hobbiesObj.put(personalInfoList[i].preference.replace(" ","_"),"")
           }else{
               hobbiesObj.put(personalInfoList[i].preference.replace(" ","_"), personalInfoList[i].selectedValue)
           }
        }


        val intrestObj = JSONObject()
        for (i in 0 until IList!!.size) {
            if(IList!![i].selectedValue==null){
                intrestObj.put(IList!![i].interest.replace(" ","_"),"")
            }else{
                intrestObj.put(IList!![i].interest.replace(" ","_"), IList!![i].selectedValue)
            }
        }

        Log.e("skahjb",hobbiesObj.toString())
        Log.e("sjbbkkahjb",intrestObj.toString())
        val map = HashMap<String, String>()
        map["about_me"] = ""
        map["friend_like"] = ""
        map["describe_me"] = ""
        map["hobbies"] = ""
        map["personal_info"] = hobbiesObj.toString()
        map["user_id"] = Utility().getUser()!!.user_id
        map["interest_info"] = intrestObj.toString()
        apiController.makeCall(AppConstant.update_user_profile, map)
    }

    override fun onSuccess(tag: String, response: String) {
        if (tag == AppConstant.get_user_profile) {
            val model = apiController.parseJson(response, UserProfileBean::class.java)
            tvMsg1.setText(model.result.about_me)
            tvMsg2.setText(model.result.friend_like)
            tvName.setText(model.result.user_name)
            username = model.result.user_name
            userid = model.result.user_id
             twitters= model.result.twitter_link
            titleTop.text = username.capitalize() + "'s" + " " + "Profile"
            tvAbout.setText("About me")
            imagesList = model.result.user_photos

            if(model.result.is_online.equals("1")){
                ivOnline.visibility=View.VISIBLE
            }else{
                ivOnline.visibility=View.GONE
            }

            Glide.with(this).load(AppConstant.ImageUrl + model.result.user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(ivProfile)
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
            flaglike = model.result.is_user_like
            if (model.result.is_user_like.equals("0")) {
                ivFav.setImageResource(R.drawable.dislike)

            } else {
                ivFav.setImageResource(R.drawable.like)

            }
        } else if (tag == AppConstant.Like) {
            ivFav.setImageResource(R.drawable.like)
            flaglike = "1"

        } else if (tag == AppConstant.DisLike) {
            ivFav.setImageResource(R.drawable.dislike)
            flaglike = "0"

        } else if (tag == AppConstant.user_report) {
            val model = apiController.parseJson(response, BaseApiResponse::class.java)
            // Utility().showSnackBar(activity!!, model.message)
            showDialogReasonUpdate("reason")
        } else if (tag == AppConstant.replace_or_remove) {
            val model = apiController.parseJson(response, RemoveReplaceBean::class.java)
            PrefManager.putString(AppConstant.userInfo, response)
            PrefManager.putBoolean(AppConstant.userSession, true)
            PrefManager.putString("UserId", model.result.user_id)
            remove.visibility = View.GONE
            replace.visibility = View.GONE
            Glide.with(this).load(AppConstant.ImageUrl + model.result.user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(Profile)
            Glide.with(this).load(AppConstant.ImageUrl + model.result.user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(ivProfile)
            Log.e("dhflkgklg", "kjghdfgi")
        } else if (tag == AppConstant.get_interest) {
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
      //  Utility().showSnackBar(activity!!, msg)
    }

    override fun onError(tag: String, msg: String) {
       // Utility().showSnackBar(activity!!, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
       // Utility().showSnackBar(activity!!, msg)
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        createProfileFile = File(uri?.path!!)
        createProfileFile = Compressor(activity).compressToFile(createProfileFile)
        Glide.with(this).load(uri)
                .placeholder(R.drawable.ic_logo).into(Profile)
       // removeReplace()
    }

    override fun loadImage(imageFile: File?, ivImage: ImageView?) {
        Glide.with(this).load(imageFile)
                .placeholder(R.drawable.ic_logo).into(ivImage!!)

    }

    private fun getPicker() {
        picker = BSImagePicker.Builder("com.appentus.shedistrict.fileprovider")
                .setSpanCount(3)
                .hideGalleryTile()
                .setGridSpacing(Utils.dp2px(2))
                .setPeekHeight(Utils.dp2px(360))
                .build()
    }

    }

fun getMonthShortName(monthNumber: Int): String{
    var monthName = ""
    if (monthNumber >= 0 && monthNumber < 12) try {
        val calendar = Calendar.getInstance()
        calendar[Calendar.MONTH] = monthNumber
        val simpleDateFormat = SimpleDateFormat("MMM")
        simpleDateFormat.calendar = calendar
        monthName = simpleDateFormat.format(calendar.time)
    } catch (e: Exception) {
        e?.printStackTrace()
    }
    return monthName
}



