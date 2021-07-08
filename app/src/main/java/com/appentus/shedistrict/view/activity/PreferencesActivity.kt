package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.PreferencesAdapter
import com.appentus.shedistrict.models.PrefrenceBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.activity_preferences.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


class PreferencesActivity : AppCompatActivity(), ResponseListener {
    lateinit var result: MutableList<String>
    lateinit var apiController: ApiController
    var religion = ""
    var education = ""
    var Preference:String=""
    lateinit var jsonvalue: JSONObject
    var valuelist = ArrayList<String>()
    var keylist=ArrayList<String>()

    lateinit var prefrencelist: MutableList<PrefrenceBean.ResultBean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        ivBack.setColorFilter(resources.getColor(R.color.skyblue))
        titleTop.text = "Preferences"
        prefrencelist = ArrayList()
        apiController = ApiController(this, this)
        apiController.loader.show()
        apiController.makeCallGET(AppConstant.get_preference)

        titleTop.setTextColor(Color.BLACK)
        ivBack.setOnClickListener {

            onBackPressed()

        }

        ivReport.setImageResource(R.drawable.correct)
        ivReport.visibility = View.VISIBLE
        ivReport.setColorFilter(resources.getColor(R.color.skyblue))

        ivReport.setOnClickListener() {
            sendPreferences()

        }

          setstatus()

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("prefrence",MODE_PRIVATE)
        Preference= sharedPreferences.getString("preferenceobj","preferenceobj").toString()


        if(!Preference.isNullOrEmpty()) {
            if(!Preference.equals("preferenceobj")) {
                val content = Preference
                jsonvalue = JSONObject(content)
                Log.e("jsonvalue", jsonvalue.toString())
                var i = 0
                val iter = jsonvalue.keys()

                while (iter.hasNext()) {
                    val key = iter.next()
                    keylist.add(key)
                    Log.e("Cdsdd", keylist.toString())

                    valuelist.add(jsonvalue.getString(key))
                    Log.e("valuelist", valuelist.toString())
                    i++
                }
            }
        }

    }

    private fun setstatus() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["status"] = "1"
        apiController.makeCallSilent(AppConstant.update_online_offline, map)

    }

    private fun sendPreferences() {
        val jsonObj = JSONObject()
        for (i in 0 until prefrencelist.size) {
            if ( prefrencelist[i].selectedValue!=null) {
                jsonObj.put(prefrencelist[i].preference.replace(" ","_"), prefrencelist[i].selectedValue.replace("[", "").replace("]", "").replace("\"", ""))
            }
            else{
                jsonObj.put(prefrencelist[i].preference.replace(" ","_"),"")
            }
        }

        Log.e("datadsdadada",jsonObj.toString())


        val sharedPreferences: SharedPreferences = this.getSharedPreferences("prefrence",MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("preferenceobj",jsonObj.toString())
            editor.putString("userid",Utility().getUser()!!.user_id)
            editor.apply()
            editor.commit()

        val intent = Intent()
        intent.putExtra("preferenceobj", jsonObj.toString())
        intent.putExtra("prefrence", "prefrence")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
     override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if (tag == AppConstant.get_preference) {
            val model = apiController.parseJson(response, PrefrenceBean::class.java)
            prefrencelist = model.result

            rvItems.layoutManager = LinearLayoutManager(this)
            rvItems.adapter = PreferencesAdapter(this, prefrencelist,valuelist,keylist)
        }
    }

    override fun onFailure(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onError(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.loader.dismiss()
        apiController.unSubscribe()

    }


}
