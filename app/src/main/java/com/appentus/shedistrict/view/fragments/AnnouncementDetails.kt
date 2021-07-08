package com.appentus.shedistrict.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.AnnouncementDetailsAdapter
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_announcement.*
import kotlinx.android.synthetic.main.toolbar_title.*
import java.io.IOException
import java.text.ParseException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */

class AnnouncementDetails : Fragment(), ResponseListener {

    var result:MutableList<AnnouncmentBean.ResultBean> =  ArrayList()
    var results : AnnouncmentBean.ResultBean ? = null
    lateinit var apiController: ApiController
    var type=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!,this)


        titleTop.text = "Her Announcements"
        ivBack.setColorFilter(resources.getColor(R.color.sblue))

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        requestMyAnnouncement()
        results= getArguments()?.getSerializable("list") as AnnouncmentBean.ResultBean

        if(activity!!.intent.hasExtra("chat")){
            val fragment = SearchProfile()
            val bundle = Bundle()
            bundle.putString("userid",activity!!.intent.getStringExtra("userid"))
            fragment.arguments = bundle
            replaceFragment(fragment, "search")
            Log.e("kkk","kjjhk")
        }

    }

    fun requestMyAnnouncement() {
        val map = HashMap<String, String>()
        map["user_id"] = arguments?.getString("userid").toString()
        apiController.makeCall(AppConstant.my_announcements, map)
    }

    override fun onSuccess(tag: String, response: String) {
        if( tag == AppConstant.my_announcements){
            val model = apiController.parseJson(response, AnnouncmentBean::class.java)
            result = model.result
            val snapHelper: SnapHelper = PagerSnapHelper()
            introPager.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
            introPager.adapter = AnnouncementDetailsAdapter(activity!!,result)
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

        /*  when (tag) {
              "home" ->  replaceFragment(ProfileFragment(), "home")
              "search" ->  replaceFragment(ProfileFragment(), "search")
              "report" ->  replaceFragment(ProfileFragment(), "report")
              "message" ->  replaceFragment(ProfileFragment(), "message")
              "more" ->  replaceFragment(ProfileFragment(), "more")
          }*/
    }

}






