package com.appentus.shedistrict.view.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.AuthActivity
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_privacy_profile.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.json.JSONObject
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class PrivacyProfileFragment : Fragment(),ResponseListener {

    lateinit var apiController:ApiController
    var hideprofile=""
    var hideprofileactivity=""
    var pauseinvitation=""
    var hidelocation=""
    var hideage=""
    var hidedeactivate=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_privacy_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleTop.text = "Profile"
        apiController= ApiController(activity!!,this)


        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }
        var user = Utility().getUser();
        hideprofile=user?.hide_profile.toString()
        hideprofileactivity=user?.hide_activity.toString()
        pauseinvitation=user?.stop_invite.toString()
        hidelocation=user?.hide_location.toString()
        hideage=user?.hide_age.toString()

        tbHideProfile.isChecked = hideprofile=="1"
        tbHideProfileActivity.isChecked = hideprofileactivity=="1"
        tbIncomingInvitation.isChecked = pauseinvitation=="1"
        tbHideLocation.isChecked = hidelocation=="1"
        tbHideAge.isChecked = hideage=="1"

        tbHideProfile.setOnCheckedChangeListener {
            compoundButton: CompoundButton, b: Boolean ->
            if(tbHideProfile.isChecked) {
                hideprofile="1"
            }
            else{
                hideprofile="0"
            }
            apicalls();

        }
        tbHideProfileActivity.setOnCheckedChangeListener {
            compoundButton: CompoundButton, b: Boolean ->
            if(tbHideProfileActivity.isChecked) {
                hideprofileactivity="1"
            }
            else{
                hideprofileactivity="0"
            }
            apicalls(); }

        tbIncomingInvitation.setOnCheckedChangeListener {
            compoundButton: CompoundButton, b: Boolean ->
            if(tbIncomingInvitation.isChecked) {
                pauseinvitation="1"
            }
            else{
                pauseinvitation="0"
            }
            apicalls();
        }

        tbHideLocation.setOnCheckedChangeListener {
            compoundButton: CompoundButton, b: Boolean ->
            if(tbHideLocation.isChecked) {
                hidelocation="1"
            }
            else{
                hidelocation="0"
            }
            apicalls();
        }


        tbHideAge.setOnCheckedChangeListener {
            compoundButton: CompoundButton, b: Boolean ->
            if(tbHideAge.isChecked) {
                hideage="1"
            }
            else{
                hideage="0"
            }
            apicalls();
        }

        tvDeactivateAccount.setOnClickListener {
            showDialogDeactivate(getString(R.string.deactivateAccount))
        }
        tvDeleteAccount.setOnClickListener {
            showDialogDeactivate(getString(R.string.deleteAccount))
        }
    }

    private fun apicalls() {

        val map = HashMap<String, String>()
        map["hide_profile"] = hideprofile
        map["hide_activity"] = hideprofileactivity
        map["stop_invite"] = pauseinvitation
        map["hide_location"] = hidelocation
        map["hide_age"] =  hideage
        map["user_id"] =  Utility().getUser()?.user_id.toString()

        apiController.makeCall(AppConstant.Update_profile_setting, map)

    }


    private fun showDialogDeactivate(message:String) {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_deactiveaccount, activity!!)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvYes = dialog.findViewById<TextView>(R.id.tvYes)
        val tvNo = dialog.findViewById<TextView>(R.id.tvNo)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))

        tvMsg.text = message

        tvYes.setOnClickListener {
            dialog.dismiss()
            apicallsfordeactivate()

        }

        tvNo.setOnClickListener {

            dialog.dismiss()
        }
        dialog.show()
    }

    private fun apicallsfordeactivate() {
        val map = HashMap<String, String>()
        map["user_id"] =  Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.User_active_deactive, map)

    }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.Update_profile_setting) {
            val model = apiController.parseJson(response, UserProfileBean::class.java)
            PrefManager.putString(AppConstant.userInfo, response)
            PrefManager.putBoolean(AppConstant.userSession, true)
            PrefManager.putString("UserId", model.result.user_id)
        }
        else if(tag == AppConstant.User_active_deactive)
        {
            PrefManager.clear()
            val Intent = Intent(activity, LoginActivity::class.java)
            startActivity(Intent)
            activity?.finishAffinity()
        }



    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }


}
