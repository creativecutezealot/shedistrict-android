package com.appentus.shedistrict.view.fragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.HelpAdapter
import com.appentus.shedistrict.models.HelpBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.ResetPassword
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.fragment_help.*
import kotlinx.android.synthetic.main.toolbar_title.*


class HelpFragment : Fragment(),ResponseListener{

    lateinit var apiController: ApiController
    var keyword=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiController = ApiController(activity!!,this)
        titleTop.text = "Help / FAQ"
        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }
        getfaq(keyword)

        etSearch.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.e("fssfds",etSearch.text.toString())

                 keyword= etSearch.text.toString()
                 getfaq(keyword)

                true
            } else {
                false
            }
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
             if(etSearch.text.toString().isNullOrEmpty()) {
                 keyword=""
                 getfaq(keyword)
             }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


    }

    private fun getfaq(keyword: String) {
        val map = HashMap<String,String>()
        map["keyword"] = keyword
        apiController.makeCall(AppConstant.app_faq,map)

    }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.app_faq){
            val model = apiController.parseJson(response,HelpBean::class.java)
            rvItem.layoutManager = LinearLayoutManager(activity)
            rvItem.adapter = HelpAdapter(activity!!, model.result)
        }
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }


}
