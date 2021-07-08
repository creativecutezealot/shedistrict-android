package com.appentus.shedistrict.view.fragments

import AddInterestAdapter
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
import android.widget.TextView
import androidx.cardview.widget.CardView
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
import com.appentus.shedistrict.view.activity.HomeActivity
import com.asksira.bsimagepicker.BSImagePicker
import com.asksira.bsimagepicker.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.fragment_search_profile.*
import kotlinx.android.synthetic.main.fragment_search_profile.ivInfo
import kotlinx.android.synthetic.main.fragment_search_profile.ivProfile
import kotlinx.android.synthetic.main.fragment_search_profile.rvFriends
import kotlinx.android.synthetic.main.fragment_search_profile.tvAbout
import kotlinx.android.synthetic.main.fragment_search_profile.tvMsg1
import kotlinx.android.synthetic.main.fragment_search_profile.tvMsg2
import kotlinx.android.synthetic.main.fragment_search_profile.tvName
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.toolbar_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File


class UserProfile : Fragment(), ResponseListener, BSImagePicker.OnSingleImageSelectedListener, BSImagePicker.ImageLoaderDelegate, select {
    lateinit var apiController: ApiController
    var flaglike = "0"
    var flag: Boolean = true
    var remove_or_replace = ""
    var selectss=""
    lateinit var jsonPersonal: JSONObject
    lateinit var jsonIntrests: JSONObject
    lateinit var username: String
    lateinit var picker: BSImagePicker
    lateinit var userid: String
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
    var date = ""
    var religion = ""
    var education = ""
    var twitters = ""
    lateinit var json: JSONObject
    var valuelist = java.util.ArrayList<String>()
    var personalvaluelist = java.util.ArrayList<String>()
    lateinit var personaljsonvalue: JSONObject
    lateinit var jsonvalue: JSONObject

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
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
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

        getPicker()
        apiController = ApiController(activity!!, this)
        apiController.makeCallGET(AppConstant.get_interest)
        apiController.makeCallGET(AppConstant.get_preference)
        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        getProfilePreference()

        ivViewProfileViews.setOnClickListener {
            val fragment = ProfileViews()
            (context as HomeActivity).replaceFragment(fragment, "profileView")


        }

        twitter.setOnClickListener {
            if(!twitters.isNullOrEmpty()) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(twitters)
                startActivity(i)
            }else{
                Utility().showSnackBar(activity!!,"Twitter account not attached")
            }

        }
        //ivActive1.setColorFilter(this.resources.getColor(R.color.green))
        ivProfile.setOnClickListener() {
            val dialog = Utility().fullScreenDialogOnTop(R.layout.dialog_imagechange, activity!!)
                remove = dialog.findViewById<TextView>(R.id.tvRemove)
                replace = dialog.findViewById<TextView>(R.id.tvReplace)
                Profile = dialog.findViewById<CircleImageView>(R.id.ivProfile)
                val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)

                if (!Utility().getUser()?.user_profile.isNullOrEmpty()) {

                    Glide.with(this).load(AppConstant.ImageUrl + Utility().getUser()?.user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(Profile)

                } else {
                    Glide.with(this).load(AppConstant.ImageUrl + Utility().getUser()?.user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(Profile)

                }
            remove.visibility=View.VISIBLE
            replace.visibility=View.VISIBLE

                remove.setOnClickListener() {
                    remove_or_replace = "1"
                    removeReplace()
                }

                replace.setOnClickListener() {
                    remove_or_replace = "2"
                    picker.show(childFragmentManager, "picker")
                }

                ivClose.setOnClickListener() {
                    dialog.dismiss()
                }


                dialog.show()
            }



        ivInfo.setOnClickListener() {
            dialogPersonalIntreat()

        }
    }

    private fun removeReplace() {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("user_id", getArguments()?.getString("userid").toString())
        builder.addFormDataPart("type", remove_or_replace)
        if (createProfileFile == null) {
            builder.addFormDataPart("profile", "")
        } else {
            builder.addFormDataPart("profile", createProfileFile?.name, RequestBody.create(MediaType.parse("multipart/form-data"), createProfileFile!!))
        }
        apiController.makeCall(AppConstant.replace_or_remove, builder.build())
    }



    private fun getProfilePreference() {
        val map = HashMap<String, String>()
        Log.e("sjfvgjf", getArguments()?.getString("userid").toString())
        Log.e("sjfvgjjgjhf", Utility().getUser()?.user_id.toString())
        map["user_id"] = getArguments()?.getString("userid").toString()
        map["friend_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_user_profile, map)

    }
    private fun dialogPersonalIntreat() {
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_hobbies, activity!!)
        val ccDialog = dialog.findViewById<LinearLayout>(R.id.root)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))
        val tvPersonalInfo = dialog.findViewById<TextView>(R.id.tvPersonalInfo)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val interest = dialog.findViewById<TextView>(R.id.interest)
        val rvItem = dialog.findViewById<RecyclerView>(R.id.rvItem)
        val ccSubmit = dialog.findViewById<CardView>(R.id.ccSubmit)
        val nvScroll = dialog.findViewById<NestedScrollView>(R.id.nvScroll)
        ivClose.setColorFilter(Color.parseColor("#000000"))
        rvItem.layoutManager = LinearLayoutManager(activity!!)


        val user = Utility().getUser();

        personaljsonvalue = JSONObject(user?.personal_info)
        val iter = personaljsonvalue.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            personalvaluelist.add(personaljsonvalue.getString(key))
        }


        user!!.personal_info.let {
            val json  = JSONObject(user.personal_info)
            personalInfoList.forEach {
                if (json.has(it.preference)){
                    it.selectedValue = json.getString(it.preference)
                }
            }
        }

        personalInfoList.let {
            rvItem.adapter = AddPersonalInfoAdapter(activity!!, personalInfoList, this, personalvaluelist)
        }
        rvItem.addOnItemTouchListener(RecyclerTouchListener(activity, rvItem, RecyclerTouchListener.ClickListener { view, position ->
        }))
        rvItem.isNestedScrollingEnabled = false
        var ishobbyClicked = true


        tvPersonalInfo.setOnClickListener {
            ishobbyClicked = true
            interest!!.setTextColor(Color.parseColor("#919191"))
            tvPersonalInfo!!.setTextColor(Color.parseColor("#000000"))
            rvItem.layoutManager = LinearLayoutManager(activity!!)



            rvItem.adapter = AddPersonalInfoAdapter(activity!!, personalInfoList, this,personalvaluelist)
            rvItem.addOnItemTouchListener(RecyclerTouchListener(activity, rvItem, object : RecyclerTouchListener.ClickListener {
                override fun onClick(view: View?, position: Int) {
                }
            }))
            rvItem.isNestedScrollingEnabled = false
            nvScroll.scrollTo(0, 0)
            interest.alpha = 0.6F
        }




        user!!.interest_info.let {
            if (!Utility().getUser()?.interest_info.toString().isNullOrEmpty()) {
                val json = JSONObject(Utility().getUser()?.interest_info)
                IList?.forEach {
                    if (json.has(it.interest)) {
                        it.selectedValue = json.getString(it.interest)
                    }
                }
            }
        }
        interest.setOnClickListener {
            ishobbyClicked = false
            interest!!.setTextColor(Color.parseColor("#000000"))
            tvPersonalInfo!!.setTextColor(Color.parseColor("#919191"))
            rvItem.layoutManager = LinearLayoutManager(activity!!)

            jsonvalue = JSONObject(Utility().getUser()?.interest_info)
            var i = 0;
            val iter = jsonvalue.keys()
            while (iter.hasNext()) {
                val key = iter.next()
                valuelist.add(jsonvalue.getString(key))

            }


            rvItem.adapter = AddInterestAdapter(activity!!, IList,valuelist)
            rvItem.isNestedScrollingEnabled = false
            nvScroll.scrollTo(0, 0)
        }

        ivClose.setOnClickListener() {
            Update()
            dialog.dismiss()
        }
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
            twitters = model.result.twitter_link
            titleTop.text = username.capitalize() + "'s" + " " + "Profile"
            tvAbout.setText("About me")
            imagesList = model.result.user_photos

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
            Log.e("fdsdf","fsdfd")
            PrefManager.putString(AppConstant.userInfo, response)
            PrefManager.putBoolean(AppConstant.userSession, true)
            PrefManager.putString("UserId", model.result.user_id)
            Utility().showSnackBar(activity!!,"Successfully Update")

            val fragment = UserProfile()
            val bundle = Bundle()
            bundle.putString("userid",Utility().getUser()?.user_id)
            bundle.putString("profile","profile")
            fragment.arguments = bundle
            (context as HomeActivity).replaceFragment(fragment, "search")

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
        removeReplace()
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
    override fun onselect(month: String?, day: String?, year: String?, pos: Int?,textView: TextView?) {
        if (!month.equals(null)&&!day.equals(null)&&!year.equals(null))
            if (!selectss.equals("Month/Date/Year")) {

                selectss="$month/$day/$year"
                Log.e("month",month)
                Log.e("day",day)
                Log.e("year",year)
                Log.e("yvhjear",selectss)
                Log.e("pofbjsfjfbds",pos.toString())
                textView!!.text =Utility().getDateFormated("MMM/dd/yyyy", "MM/dd/yyyy", selectss)

               // personalInfoList.get(pos!!)?.selectedValue = Utility().getDateFormated("MMM/dd/yyyy", "MM/dd/yyyy", selectss)

                Log.e("kjbj", Utility().getDateFormated("MMM/dd/yyyy", "MM/dd/yyyy", selectss))
                //  Log.e("kfjh",(getAge(tvYearValue!!.toInt(), tvMonthValue!!.toInt(),tvDayValue!!.toInt()).toString()))
            }
    }
}




