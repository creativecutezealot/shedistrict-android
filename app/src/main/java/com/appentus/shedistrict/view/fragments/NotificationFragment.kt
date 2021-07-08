package com.appentus.shedistrict.view.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment(), ResponseListener {

    lateinit var apiController: ApiController

    lateinit var json: JSONObject

    var list = ArrayList<String>()
    var pushnotification=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Notificationarraylist = ArrayList<ImageView>(Arrays.asList(ivNotificationProfile, ivNotificationLikeProfile,
                ivNotificationLikepicture, ivNotificationCommentpicture, ivNotificationMessage, ivNotificationLikeComment, ivNotificationReplyComment,
                ivNotificationLikeCommentPost, ivNotificationDisikeCommentPost));
        apiController = ApiController(activity!!, this)
        titleTop.text = "Notifications"
        var user = Utility().getUser();

        pushnotification =  Utility().getUser()?.push_status.toString();

        tvSwitch1.isChecked = pushnotification=="1"


        tvSwitch1.setOnCheckedChangeListener {
            compoundButton: CompoundButton, b: Boolean ->
            if(tvSwitch1.isChecked) {
                pushnotification="1"
            }
            else{
                pushnotification="0"
            }
            apicalls();

        }

        json = JSONObject(user?.push_setting);
        var i = 0;
        val iter = json.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            list.add(json.getString(key))
            if (json.getString(key) == "1") {
                Notificationarraylist[i].isSelected = true
                Notificationarraylist[i].setImageResource(R.drawable.check)

            } else {
                Notificationarraylist[i].isSelected = false
                Notificationarraylist[i].setImageResource(R.drawable.circle)
            }
            i++
        }

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        Log.e("count", "${llAllNotification.childCount}")

        for (i in 0 until llAllNotification.childCount) {
            val cardView = llAllNotification.getChildAt(i) as CardView
            cardView.setOnClickListener {
                if (Notificationarraylist[i].isSelected) {
                    Notificationarraylist[i].setImageResource(R.drawable.circle)
                    Notificationarraylist[i].isSelected = false
                    list[i]="0"

                } else {
                    Notificationarraylist[i].setImageResource(R.drawable.check)
                    Notificationarraylist[i].isSelected = true
                    list[i]="1"
                }
                apicalls()
            }
        }
    }

    private fun apicalls() {
        val obj = JSONObject()
        Log.e("drjgejr",pushnotification)
        for (i in list.indices){
            obj.put("${i+1}",list[i])
        }

        val map = HashMap<String, String>()
        map["push_status"] = pushnotification
        map["push_setting"] = obj.toString()
        map["user_id"] =  Utility().getUser()?.user_id.toString()

        apiController.makeCall(AppConstant.notification, map)

    }

    override fun onSuccess(tag: String, response: String) {
        val model = apiController.parseJson(response, UserProfileBean::class.java)
        PrefManager.putString(AppConstant.userInfo,response)
        PrefManager.putBoolean(AppConstant.userSession,true)
        PrefManager.putString("UserId",model.result.user_id)


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
