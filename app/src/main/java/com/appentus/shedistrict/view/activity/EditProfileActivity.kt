package com.appentus.shedistrict.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.SheDistrict.activity
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.DescrimeAdapter
import com.appentus.shedistrict.models.*
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.User
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.bestdescribemelist.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList
import kotlin.collections.set


class EditProfileActivity : AppCompatActivity(), ResponseListener {
    var IList: MutableList<IntrestBean.ResultBean>? = null
    var hList: MutableList<HobbiesBean.ResultBean>? = null
    var iSublist: MutableList<IntrestBean.ResultBean.ValueBean>? = null
    var personalInfoList: MutableList<PersonalInfoBean.ResultBean>? = null
    var list: MutableList<DescribeMeBean.ResultBean> = ArrayList()
    var describemeId: String? = null

    var id=2
    var userName: String? =null

    var AUTHURL = "https://api.instagram.com/oauth/authorize/"
    var TOKENURL = "https://api.instagram.com/oauth/access_token"
    var APIURL = "https://api.instagram.com/v1"
    var CALLBACKURL = "Your Redirect URI"

  //  private var twitterAuthClient: TwitterAuthClient? = null


    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        IList = ArrayList()
        hList = ArrayList()
        personalInfoList = ArrayList()
        apiController = ApiController(this, this)
        apiController.makeCallGET(AppConstant.get_hobbies)
        apiController.makeCallGET(AppConstant.get_interest)
        apiController.makeCallGET(AppConstant.Describe_me_data)
        apiController.makeCallGET(AppConstant.personal_info_data)

        sessioncheckintial()
        imTwitter.setOnClickListener {
            sessioncheck()
        }

        twitterloging.callback= object : com.twitter.sdk.android.core.Callback<TwitterSession?>() {
            override fun success(result: Result<TwitterSession?>?) {
                Log.e("fsbfbrsw",result?.data!!.userName)
                Toast.makeText(activity, "Connect successful.", Toast.LENGTH_SHORT).show()

                  getTwitterData(result.data)
                 tvTwitterConnect.text= "Disconnect"


            }

            override fun failure(exception: TwitterException?) {
                //if user failed to authorize then show toast
                Log.e("dfvbhbrtg",exception?.message)
                Toast.makeText(activity, "Failed to authenticate by Twitter. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }
        titleTop.text = "Edit Profile"

        ccUploadVideo.setOnClickListener() {
            startActivity(Intent(activity, RecordNewVideo::class.java).putExtra("Updateprofile", "update"))

        }

        tvUploadImage.setOnClickListener() {
            startActivity(Intent(activity, UploadSomePicActivity::class.java).putExtra("update","update"))
        }
        ccOption.setOnClickListener() {

            showCategoryDialog(list)
        }

        ccUserName.setOnClickListener {
            startActivity(Intent(this, ChangeProfileActivity::class.java))
        }


        /*   ccAddHobbies.setOnClickListener {
            dialogAddInterests()
        }
*/
        ccDeny.setOnClickListener() {
            activity.finish()

        }

     /*   ccAddPersonalinfo.setOnClickListener() {
            dialogAddPersonal()
        }*/
        ccAccept.setOnClickListener() {
            if (isValidation()) {
                editProfile()

            }
        }

        tvMsgAbout.setText(Utility().getUser()!!.about_me)
        tvMsgLookingPerson.setText(Utility().getUser()!!.friend_like)
           setstatus()

    }
    private fun setstatus() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["status"] = "1"
        apiController.makeCallSilent(AppConstant.update_online_offline, map)

    }
    fun sessioncheck() {
        val session: TwitterSession? = TwitterCore.getInstance().getSessionManager()
                    .getActiveSession() //get the active session
            if (session != null) {
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
                Toast.makeText(activity, "Disconnect successful.", Toast.LENGTH_SHORT).show()
                tvTwitterConnect.text= "Connect"
            }
            else {
                //if there is no active session then ask user to authenticate
                twitterloging.performClick()
            }

    }

    fun sessioncheckintial() {
        val session: TwitterSession? = TwitterCore.getInstance().getSessionManager()
                .getActiveSession() //get the active session
        if (session != null) {

            tvTwitterConnect.text= "Disconnect"
            getTwitterData(session)
        }
        else {
            //if there is no active session then ask user to authenticate
            tvTwitterConnect.text= "Connect"
        }

    }
      private fun getTwitterData(twitterSession: TwitterSession?) {
          val twitterApiClient = TwitterApiClient(twitterSession)
          val getUserCall: Call<User> = twitterApiClient.accountService.verifyCredentials(true, false, true)
          getUserCall.enqueue(object : com.twitter.sdk.android.core.Callback<User?>() {
              override fun success(result: Result<User?>?) {
                  val imageUrl = result!!.data!!.profileImageUrl
                  val email = result.data!!.email

                  userName = result.data!!.name
                  Log.e("userName",userName)
                  Log.e("jgjgjhfhfh","dssfsdfds")
              }

              override fun failure(exception: TwitterException?) {

              }
          })
      }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        twitterloging!!.onActivityResult(requestCode, resultCode, data)

    }

    private fun editProfile() {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["about_me"] = tvMsgAbout.text.toString()
        map["friend_like"] = tvMsgLookingPerson.text.toString()
        map["twitter_link"] = "https://twitter.com/"+userName
        map["describe_me"] = ""
        map["hobbies"] = ""
        map["interest_info"] = ""
        map["personal_info"] = ""
        apiController.makeCall(AppConstant.update_user_profile, map)
    }
    private fun showCategoryDialog(describeList: MutableList<DescribeMeBean.ResultBean>) {
      val view = layoutInflater.inflate(R.layout.bestdescribemelist, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)

        val describemeAdapter = DescrimeAdapter(this@EditProfileActivity, describeList, object :
                DescrimeAdapter.GetClick {
            override fun dataClick(position: Int) {
                dialog.dismiss()
                describemeId = describeList[position].id
                tvOption.text = describeList[position].value
                tvOption.setTextColor(Color.parseColor("#000000"))

            }
        })
        val manager: RecyclerView.LayoutManager =
                LinearLayoutManager(this)
        dialog.rvDescribemeList.layoutManager = manager
        dialog.rvDescribemeList.adapter = describemeAdapter
        dialog.show()
    }

    private fun isValidation(): Boolean {
        when {
            tvMsgAbout.text.toString().isNullOrEmpty() -> {
                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccAbout)
                return false
            }
            tvMsgLookingPerson.text.toString().isNullOrEmpty() -> {
                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(ccLookingFriend)
                return false
            }
            else -> return true
        }

    }


    override fun onSuccess(tag: String, response: String) {
        when (tag) {
            AppConstant.get_hobbies -> {
                val model = apiController.parseJson(response, HobbiesBean::class.java)
                hList = model.result
            }
            AppConstant.get_interest -> {
                val model = apiController.parseJson(response, IntrestBean::class.java)
                IList = model.result

            }
            AppConstant.Describe_me_data -> {
                val model = apiController.parseJson(response, DescribeMeBean::class.java)
                list = model.result

            }
            AppConstant.personal_info_data -> {
                val model = apiController.parseJson(response, PersonalInfoBean::class.java)
                personalInfoList = model.result
            }

            AppConstant.update_user_profile -> {
                val model = apiController.parseJson(response, EditProfileBean::class.java)
                PrefManager.putString(AppConstant.userInfo, response)
                PrefManager.putBoolean(AppConstant.userSession, true)
                PrefManager.putString("UserId", model.result.user_id)

            }
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

}
