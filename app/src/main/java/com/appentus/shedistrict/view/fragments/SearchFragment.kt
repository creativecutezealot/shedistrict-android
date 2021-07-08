package com.appentus.nutrition.ui.fragments

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.SearachAdapter
import com.appentus.shedistrict.models.UserByPreferenceBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.activity.PreferencesActivity
import com.appentus.shedistrict.view.fragments.ViewAllFragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONObject


class SearchFragment : Fragment(), ResponseListener {
    var DURATION: Long = 100
    var count:Int=0
    var Preference:String=""
    lateinit var apiController: ApiController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!, this)

        if (isFirstTime()) {
            Handler().postDelayed(Runnable {
                if (isAdded) {

                    showDialogConfirm()

                }
            }, 1000)
        }

        ivFilter.setColorFilter(this.resources.getColor(R.color.skyblue))

        ivFilter.setOnClickListener {
            val intent = Intent(context, PreferencesActivity::class.java)
            startActivityForResult(intent, 2) //
           // startActivity(Intent(activity, PreferencesActivity::class.java))

            }

        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("prefrence", AppCompatActivity.MODE_PRIVATE)
        Preference= sharedPreferences.getString("preferenceobj","0").toString()


        if(arguments != null) {
            if (arguments!!.getString("Allpic").equals("Allpic")) {
                userPreference(arguments!!.getString("piclist").toString())
            }
        }
        else{
            userPreference(Preference)
        }
    }

    private fun isFirstTime(): Boolean {
        val preferences: SharedPreferences = activity!!.getPreferences(MODE_PRIVATE)
        val ranBefore = preferences.getBoolean("RanBefore", false)
        if (!ranBefore) { // first time
            val editor = preferences.edit()
            editor.putBoolean("RanBefore", true)
            editor.commit()
        }
        return !ranBefore
    }

    private fun userPreference(preference: String) {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        map["filter"] =preference
        Log.e("kdfk",Utility().getUser()?.user_id.toString())
        Log.e("ksk",preference)
        apiController.makeCall(AppConstant.User_by_preference, map)

    }
    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).activeTab(1)
    }

    private fun showDialogConfirm() {
        val dialog = Utility().fullScreenDialog(R.layout.warringdialog, activity!!)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvWarring = dialog.findViewById<TextView>(R.id.tvWarring)
        val tvNo = dialog.findViewById<TextView>(R.id.tvNo)
        val tvYes = dialog.findViewById<TextView>(R.id.tvYes)
        tvNo.visibility = View.GONE
        tvMsg.text = "Swipe right on a category to view all"
        val text = "<font color=#919191> users with the</font> <font color=#000000>most things in common.</font>"
        tvWarring.text = (Html.fromHtml(text))
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))
        tvYes.text = "Ok"
        tvYes.setTextColor(resources.getColor(R.color.yellow))
        tvYes.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    override fun onSuccess(tag: String, response: String) {
        val dataList = ArrayList<UserByPreferenceBean>()
        val  list = ArrayList<UserByPreferenceBean.Data>()

        val base = JSONObject(response)
        val jsonObject = base.getJSONObject("result")
        val iter: Iterator<String> = jsonObject.keys()


        while (iter.hasNext()) {
            val key = iter.next()

            val array = jsonObject.getJSONArray(key)
            if(array.length()>0){
                var  listTemp = ArrayList<UserByPreferenceBean.Data>()
                for (i in 1 until array.length()){
                    val obj = array.getJSONObject(i)
                    val data = UserByPreferenceBean.Data()
                    data.user_id = obj.getString("user_id")
                    data.user_name = obj.getString("user_name")
                    data.user_profile = obj.getString("user_profile")
                    data.role = obj.getString("role")

                    listTemp.add(data)
                    list.add(data)
                }

                val mainList = UserByPreferenceBean()
                mainList.name = key
                mainList.data =  listTemp
                dataList.add(mainList)
            }
        }

            count=dataList.size
            rvItems.layoutManager = LinearLayoutManager(activity)
            rvItems.adapter = SearachAdapter(activity!!,dataList,count)
            rvItems.addOnItemTouchListener(RecyclerTouchListener(context,rvItems, RecyclerTouchListener.ClickListener { _, pos->

            }))

        cvViewAll.setOnClickListener(){
            val fragment = ViewAllFragment()
            val bundle = Bundle()
          //  bundle.putString("name",data[position].name)
            bundle.putSerializable("piclist",list)
            fragment.arguments = bundle
            (context as HomeActivity).replaceFragment(fragment, "dknlk")


        }
    }

    override fun onFailure(tag: String, msg: String) {
        //Utility().showSnackBar(activity!!, msg)
       // Utility().showSnackBar(activity!!,"No Result found")
    }

    override fun onError(tag: String, msg: String) {
       /// Utility().showSnackBar(activity!!, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!, msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2 && resultCode==Activity.RESULT_OK) {

            userPreference(data!!.getStringExtra("preferenceobj"))
            Log.e("scfsdaf",data!!.getStringExtra("preferenceobj"))

        }
    }

}