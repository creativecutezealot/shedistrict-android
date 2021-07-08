package com.appentus.shedistrict.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.BaseApiResponse
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_change_profile.*
import kotlinx.android.synthetic.main.toolbar_title.*
import java.util.HashMap

class ChangeProfileActivity : AppCompatActivity(),ResponseListener{


    lateinit var apiController: ApiController
    var flag:Boolean = true
    var flag1:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile)
        apiController = ApiController(this,this)
        titleTop.text = "Profile"
        ivBack.setOnClickListener {
            onBackPressed()
        }

        setstatus()

        ivArrow.setOnClickListener {
            if(flag) {
                flag=false
                llChangeUsername.visibility = View.VISIBLE
                ccPreview.visibility=View.VISIBLE
                ccCurrentPassword.visibility=View.GONE
                ccChangePasswords.visibility=View.GONE
            }
            else{
                flag=true
                llChangeUsername.visibility = View.GONE
                ccPreview.visibility=View.GONE
            }
        }

        ccChangePassword.setOnClickListener {
            if(flag1) {
                flag1=false
                ccCurrentPassword.visibility = View.VISIBLE
                ccNewPassword.visibility = View.VISIBLE
                ccChangePasswords.visibility=View.VISIBLE
                ccPreview.visibility=View.GONE
                llChangeUsername.visibility = View.GONE

            }
            else{
                flag1=true
                ccCurrentPassword.visibility = View.GONE
                ccNewPassword.visibility = View.GONE
                ccChangePasswords.visibility=View.GONE
            }
        }
        ccChangePasswords.setOnClickListener(){
            if(isValidationPassword()){
                changePassword()
            }
        }

      /*  tvUploadImage.setOnClickListener(){

            startActivity(Intent(SheDistrict.activity, ResetPassword::class.java))
        }*/

        ccPreview.setOnClickListener {
            if(isValidation()){
                changeUserName()
            }
        }
    }

    private fun setstatus() {
            val map: HashMap<String, String> = HashMap<String, String>()
            map["user_id"] = Utility().getUser()!!.user_id
            map["status"] = "1"
            apiController.makeCallSilent(AppConstant.update_online_offline, map)

    }

    private fun changePassword() {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["password_old"] = etCurrentPassword.text.toString()
        map["password_new"] = etNewPassword.text.toString()
        apiController.makeCall(AppConstant.update_password, map)
    }

    private fun changeUserName(){
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["user_name_old"] = etCurrentName.text.toString()
        map["user_name_new"] = etNewName.text.toString()
        apiController.makeCall(AppConstant.update_user_name, map)
    }

    private fun isValidation():Boolean{
        if(etCurrentName.text.toString().isNullOrEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(50)
                    .repeat(5)
                    .playOn(ccCurrentUserName)
            return false
        }
        else if(etNewName.text.toString().isNullOrEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(50)
                    .repeat(5)
                    .playOn(ccNewUserName)
            return false
        }
        return true
    }

    private fun isValidationPassword():Boolean{
        if(etCurrentPassword.text.toString().isNullOrEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(50)
                    .repeat(5)
                    .playOn(ccCurrentPassword)
            return false
        }
        else if(etNewPassword.text.toString().isNullOrEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(50)
                    .repeat(5)
                    .playOn(ccNewPassword)
            return false
        } else if(etNewPassword.text.length < 6){
            YoYo.with(Techniques.Shake)
                    .duration(50)
                    .repeat(5)
                    .playOn(ccNewPassword)
            return false
        }
        return true
    }


    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.update_user_name){
            val model = apiController.parseJson(response,UserProfileBean::class.java)
            PrefManager.putString(AppConstant.userInfo,response)
            Utility().showSnackBar(this,model.message)
            finish()
        }else if(tag == AppConstant.update_password){
            val model = apiController.parseJson(response,UserProfileBean::class.java)
            PrefManager.putString(AppConstant.userInfo,response)
            Utility().showSnackBar(this,model.message)
            finish()
        }
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }
}
