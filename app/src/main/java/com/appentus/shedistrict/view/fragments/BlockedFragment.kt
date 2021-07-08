package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.BlockedAdapter
import com.appentus.shedistrict.models.BlockedUserBeans
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_blocked.*
import kotlinx.android.synthetic.main.toolbar_title.*

/**
 * A simple [Fragment] subclass.
 */
class BlockedFragment : Fragment(),ResponseListener {

    lateinit var apiController: ApiController
    var list: MutableList<BlockedUserBeans.ResultArrayBean> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blocked, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController= ApiController(activity!!,this)


        titleTop.text = "Blocked Users"

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        blockuserlist();




    }

    private fun blockuserlist() {
       val map= HashMap<String,String>()
        map["user_id"]= Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.Block_user_list,map)

    }

    override fun onSuccess(tag: String, response: String) {
        val model=apiController.parseJson(response,BlockedUserBeans::class.java)
        list=model.result_array
        rvBlockedItems.layoutManager= LinearLayoutManager(activity)
        rvBlockedItems.adapter=BlockedAdapter(activity!!,list)
       Log.e("hcvgj","jfgcsduj");

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
