package com.appentus.shedistrict.view.fragments


import android.app.Dialog
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.CallAdapter
import com.appentus.shedistrict.adapters.MessageAdapter
import com.appentus.shedistrict.models.ActiveDeactiveProfileBean
import com.appentus.shedistrict.models.CurrentFriendListBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_she_protests.*
import kotlinx.android.synthetic.main.toolbar_title.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SheProtests : Fragment(),ResponseListener{
    lateinit var apiController: ApiController
    lateinit var list: MutableList<CurrentFriendListBean.ResultBean>
    val rvItem: RecyclerView? = null
      var automsg=""
      var callOrmsg=""
     lateinit var  dialog:Dialog
    var sb = StringBuilder()
    var num: ArrayList<Int> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_she_protests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        apiController = ApiController(activity!!, this)

        titleTop.text = "SheProtects"

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        tvMsg.setOnClickListener {
            showScan()
        }

        ccFriendList.setOnClickListener {
            (activity as HomeActivity).replaceFragment(FriendListFragment(), "more")
        }

        ccCall.setOnClickListener {
            getFriendList()
            callOrmsg="call"
        }

        ccTextFriend.setOnClickListener {
            getFriendList()
            callOrmsg="msg"
        }

        if(Utility().getUser()!!.she_protect.equals("0"))
        {
            tvSwitch.isChecked=false
        }
        else{
            tvSwitch.isChecked=true
        }

        tvSwitch.setOnClickListener {
            if(tvSwitch.isChecked){
               etSheProtestActive.text = "SheProtects is currently active"
                activeInactive("1")
            }
            else{

               etSheProtestActive.text = "SheProtects is currently inactive"
                activeInactive("0")
            }
        }
    }



    private fun showScan() {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_sheprotest, activity!!)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvMsg1 = dialog.findViewById<TextView>(R.id.tvMsg1)

        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))

        ivClose.setColorFilter(activity!!.resources.getColor(R.color.grey))
        tvMsg1.text = getString(R.string.sheProtestTitle1)

        ivClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showCall(calllist: MutableList<CurrentFriendListBean.ResultBean>) {
        val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_callfriends, activity!!)
        val ccDialog = dialog.findViewById<LinearLayout>(R.id.ccDialog)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val rvItem = dialog.findViewById<RecyclerView>(R.id.rvItem)
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))

        ivClose.setColorFilter(activity!!.resources.getColor(R.color.txtPurpal))

        ivClose.setOnClickListener {
            dialog.dismiss()
        }

           rvItem!!.layoutManager = LinearLayoutManager(activity)
          rvItem.adapter = CallAdapter(activity!!,calllist)
          rvItem.isNestedScrollingEnabled = false


        dialog.show()


    }

    private fun showMessageshowCall(messagelist: MutableList<CurrentFriendListBean.ResultBean>) {
        if(callOrmsg.equals("msg")) {
             dialog = Utility().fullScreenDialog(R.layout.dialog_message, activity!!)
             val rvItemshowmsg= dialog.findViewById<RecyclerView>(R.id.rvItem)
            val chValue = dialog.findViewById<CheckBox>(R.id.chValue)
            val chValues = dialog.findViewById<CheckBox>(R.id.chValues)
            val llCose = dialog.findViewById<LinearLayout>(R.id.llCose)
            val tvSubmit = dialog.findViewById<TextView>(R.id.tvSubmit)
            val etWriteMessage = dialog.findViewById<EditText>(R.id.etWriteMessage)
            val tvDangerous = dialog.findViewById<TextView>(R.id.tvDangerous)
            val location= getCompleteAddressString(Utility().getUser()!!.user_lat.toDouble(),Utility().getUser()!!.user_lang.toDouble())

            llCose.setOnClickListener {
                dialog.dismiss()
        }



            Log.e("SDSsdsfsf", this.list.toString())
            rvItemshowmsg?.layoutManager = LinearLayoutManager(activity)
            val adapter=MessageAdapter(activity!!,messagelist)
            rvItemshowmsg?.adapter = adapter
            rvItemshowmsg?.isNestedScrollingEnabled = false
            sb.clear()

             if (chValue.isChecked) {
                 for (i in 0 until messagelist.size) {

                     sb.append(messagelist[i].user_id).append(",")
                 }

                 Log.e("vdfdfvd",sb.toString())
                 }

            tvDangerous.text="I'm in Danger! This is my location:"+location


            if(chValues.isChecked){



                automsg="I'm in Danger! This is my location:"+location
            }else{
                automsg=""
            }


            tvSubmit.setOnClickListener {
                 num = adapter.getpostion()
                for(i in 0 until messagelist.size){
                    if(!(num[i]==-1)){
                        sb.append(messagelist[i].user_id).append(",")
                    }

                }

                 Log.e("sfdsgdf",sb.toString())
                if(sb.toString().isNullOrEmpty()){
                    Utility().showSnackBar(activity!!,"Please select the Friend")
                }else {
                    val map = HashMap<String, String>()
                    map["user_ids"] = sb.toString()
                    map["id"] = Utility().getUser()?.user_id.toString()
                    map["message"] = etWriteMessage.text.toString()
                    map["auto_message"] = automsg
                    apiController.makeCall(AppConstant.active_she_protect, map)
                }
            }

            sb.clear()
            chValue.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if (chValue.isChecked) {
//                        chValue.isChecked = false
                        adapter.selectall(true)
//                        adapter.notifyDataSetChanged()
                        Log.e("ihihih",messagelist.size.toString())
                        for(i in 0 until messagelist.size){

                            sb.append(messagelist[i].user_id).append(",")
                        }

                        Log.e("Dddsdsdsf",sb.toString())

                    } else {
                        sb.clear()
//                        chValue.isChecked = true
                          adapter.selectall(false)
//                        adapter.notifyDataSetChanged()
                    }
                }
            })


            dialog.show()

        }else{

            showCall(messagelist)
        }

    }

    fun getFriendList() {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_she_friends, map)
    }

    fun activeInactive(i: String) {
        val map = HashMap<String, String>()
        Log.e("nfskn",i)
        map["status"] = i
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.active_deactive, map)
    }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.get_she_friends){
            val model = apiController.parseJson(response,CurrentFriendListBean::class.java)
            list = model.result
            showMessageshowCall(list)

        }else if(tag == AppConstant.active_deactive){
            val model = apiController.parseJson(response, ActiveDeactiveProfileBean::class.java)
            PrefManager.putString(AppConstant.userInfo,response)
            PrefManager.putBoolean(AppConstant.userSession,true)
            PrefManager.putString("UserId",model.result.user_id)
        }
        else if(tag == AppConstant.active_she_protect){
            Log.e("jjsfvdshf","ddfgadfg")
            Utility().showSnackBar(activity!!, "Message successfully sent to your friends")
            dialog.dismiss()

        }
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

    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String? {
        var strAdd = ""
        val geocoder = Geocoder(activity, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            try {
                if (addresses != null && addresses.size > 0) {
                    val returnedAddress = addresses[0]
                    val strReturnedAddress = java.lang.StringBuilder("")
                    for (i in 0..returnedAddress.maxAddressLineIndex) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                    }
                    strAdd = strReturnedAddress.toString()
                    // Log.w("My Current loction address", strReturnedAddress.toString());
                } else { //Log.w("My Current loction address", "No Address returned!");
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //  Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd
    }
}


