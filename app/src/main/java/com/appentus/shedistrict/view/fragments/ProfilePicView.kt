package com.appentus.shedistrict.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.ProfilePicViewAdapter
import com.appentus.shedistrict.models.CurrentPlanBean
import com.appentus.shedistrict.models.ProfileViewerBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfilePicView : Fragment() ,ResponseListener {
    lateinit var apiController: ApiController

    var result:MutableList<ProfileViewerBean.ResultBean> =  ArrayList()
    var resultplan: String? =null
    var abc: String? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!, this)
        getProfilView()
        currentpurchase()

        imViewmore.setOnClickListener {

            val fragment =PurchasesFragment()
            val bundle = Bundle()
            bundle.putString("userid", activity!!.intent.getStringExtra("userid"))
            bundle.putString("viewprofile", "viewprofile")
            fragment.arguments = bundle
            (activity as HomeActivity).replaceFragment(fragment, "search")

        }

    }
    private fun currentpurchase() {
        val map = HashMap<String, String>()
        map["user_id"] =Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_my_plan, map)
    }

    private fun getProfilView() {
        val map = HashMap<String, String>()
        Log.e("sjfvgjjgjhf", Utility().getUser()?.user_id.toString())
        map["user_id"] =Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_viewers, map)

    }

    override fun onSuccess(tag: String, response: String) {
        if (tag == AppConstant.get_viewers) {
            val model = apiController.parseJson(response, ProfileViewerBean::class.java)
            result=model.result
            rvProfilePicsView.layoutManager = GridLayoutManager(activity!!, 5)
            rvProfilePicsView.adapter = ProfilePicViewAdapter(activity!!,result,resultplan)

        }
        else if (tag == AppConstant.get_my_plan) {
           val model = apiController.parseJson(response, CurrentPlanBean::class.java)
            resultplan=model.result.plan_id

            if(!resultplan.isNullOrEmpty()&& response.equals("plans_3"))
            {
                imViewmore.visibility=View.GONE
            }
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


}
