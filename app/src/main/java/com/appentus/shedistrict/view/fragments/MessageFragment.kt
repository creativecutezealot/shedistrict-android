package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : Fragment(), ResponseListener {

    lateinit var apiController: ApiController
    lateinit var jsonmessage: JSONObject
    lateinit var jsonpremium: JSONObject
    var data: String? = null
    var allowList = ArrayList<String>()
    var premiumList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val messageArrayList = ArrayList<ImageView>(Arrays.asList(ivMessageEveryone, ivMessageLikeProfile, ivMessagePreference));
        val toggleButtonList = ArrayList<ToggleButton>(Arrays.asList(tvSwitch, tvSwitch1));
        titleTop.text = "Messages"
        ivBack.setOnClickListener {
            activity!!.onBackPressed()

        }
        val user = Utility().getUser();
        apiController = ApiController(activity!!, this)


        jsonpremium = JSONObject(user?.premium_data);

        jsonmessage = JSONObject(user!!.allow_msg);
        var i = 0;
        val iters = jsonmessage.keys()

        while (iters.hasNext()) {
            val key = iters.next()
            allowList.add(jsonmessage.getString(key))
            if (jsonmessage.getString(key) == "1") {
                messageArrayList[i].isSelected = true
                messageArrayList[i].setImageResource(R.drawable.check)

            } else {
                messageArrayList[i].isSelected = false
                messageArrayList[i].setImageResource(R.drawable.circle)
            }
            i++
        }

        for (i in 0 until llAllowMessage.childCount) {
            val cardView = llAllowMessage.getChildAt(i) as CardView
            cardView.setOnClickListener {
                if (messageArrayList[i].isSelected) {
                    messageArrayList[i].setImageResource(R.drawable.circle)
                    messageArrayList[i].isSelected = false
                    allowList[i] = "0"

                } else {
                    singledata(i,messageArrayList)
                }
              apicalls()

            }
        }

        // for premium
        var j = 0;
        val iter = jsonpremium.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            premiumList.add(jsonpremium.getString(key))
            Log.e("fehbej", key)
            toggleButtonList[j].isChecked = jsonpremium.getString(key) == "1"
            j++
        }

        for (i in 0 until llPremium.childCount) {
            val cardView = llPremium.getChildAt(i) as CardView
            cardView.setOnClickListener {
                if (toggleButtonList[i].isChecked) {
                    toggleButtonList[i].isChecked = false
                    premiumList[i] = "0"

                } else {
                    toggleButtonList[i].isChecked = true
                    premiumList[i] = "1"
                }
                apicalls()

            }
        }



    }

    private fun singledata(i: Int, messageArrayList: ArrayList<ImageView>) {

        for(j in  allowList.indices)
        {
            Log.e("hff",j.toString())
            Log.e("hjgjff",i.toString())
            if(j==i) {
                messageArrayList[j].setImageResource(R.drawable.check)
                messageArrayList[j].isSelected = true
                allowList[j]= "1"
            }
            else {
                messageArrayList[j].setImageResource(R.drawable.circle)
                messageArrayList[j].isSelected = false
                allowList[j] = "0"
            }
        }

    }

    private fun apicalls() {
        val obj = JSONObject()
        val objPremium = JSONObject()

        for (i in allowList.indices) {
            obj.put("${i + 1}", allowList[i])
        }

        for (i in premiumList.indices) {
            objPremium.put("${i + 1}", premiumList[i])
        }

        val map = HashMap<String, String>()
        map["status"] = obj.toString()
        map["premium_status"] = objPremium.toString()
        map["user_id"] = Utility().getUser()?.user_id.toString()



        apiController.makeCall(AppConstant.Update_message_setting, map)

    }

    override fun onSuccess(tag: String, response: String) {
        val model = apiController.parseJson(response, UserProfileBean::class.java)
        PrefManager.putString(AppConstant.userInfo, response)
        PrefManager.putBoolean(AppConstant.userSession, true)
        PrefManager.putString("UserId", model.result.user_id)

    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(activity!!, msg)
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(activity!!, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!, msg)
    }


}
