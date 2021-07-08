package com.appentus.nutrition.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.AnnouncementAdapter
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.fragments.CeoProfile
import com.appentus.shedistrict.view.fragments.SearchProfile
import kotlinx.android.synthetic.main.fragment_announcement.*


class AnnouncementFragment : Fragment(),ResponseListener{

    var result:MutableList<AnnouncmentBean.ResultBean> =  ArrayList()
    lateinit var apiController: ApiController
    var type=""
    var layout:Int=R.layout.item_home

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_announcement, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!, this)


        val categoryName = arguments?.getString("categoryName").toString()
        Log.e("vnklnvlf", categoryName)

        type = arguments?.getString("type").toString()
        requestAnnouncement(type)

        if (categoryName.equals("From the CEO")) {
            layout = R.layout.item_home_ceo
        } else {
            layout = R.layout.item_home

        }

        if (activity!!.intent.hasExtra("viewprofile")) {
            if (activity!!.intent.getStringExtra("role").equals("0")) {
                val fragment = SearchProfile()
                val bundle = Bundle()
                bundle.putString("userid", activity!!.intent.getStringExtra("userid"))
                bundle.putString("viewprofile", "viewprofile")
                fragment.arguments = bundle
                replaceFragment(fragment, "search")
                Log.e("kkk", "kjjhk")
            } else {
                val fragment = CeoProfile()
                val bundle = Bundle()
                bundle.putString("userid", activity!!.intent.getStringExtra("userid"))
                fragment.arguments = bundle
                replaceFragment(fragment, "ceoprofile")
            }
        }
    }

    fun requestAnnouncement(type: String) {
        val map = HashMap<String,String>()
        map["type"] =type
        Log.e("jfgj",type)
        apiController.makeCall(AppConstant.get_announcements,map)
    }

    override fun onSuccess(tag: String, response: String) {
        if( tag == AppConstant.get_announcements){
            val model = apiController.parseJson(response,AnnouncmentBean::class.java)
            result = model.result
            val snapHelper: SnapHelper = PagerSnapHelper()
            introPager.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
            introPager.adapter = AnnouncementAdapter(activity!!,result, layout)
            ViewCompat.setNestedScrollingEnabled(introPager, false)
            snapHelper.attachToRecyclerView(introPager)
            pageIndicator.attachTo(introPager)

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

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment, tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()

    }

}
