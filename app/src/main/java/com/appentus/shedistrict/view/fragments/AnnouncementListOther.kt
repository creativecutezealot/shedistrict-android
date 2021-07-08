package com.appentus.shedistrict.view.fragments

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.AnnouncmentListAdapter
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_announcement_list_other.*
import kotlinx.android.synthetic.main.fragment_your_announcement.rvItem
import kotlinx.android.synthetic.main.toolbar_title.*
import java.io.Serializable
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.ThreadPoolExecutor
 /**
 * A simple [Fragment] subclass.
 */
class AnnouncementListOther : Fragment() , ResponseListener {
    lateinit var apiController: ApiController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement_list_other, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!, this)


        tvNoAnnouncement.text= arguments?.getString("username")!!.capitalize()+" hasn't posted any announcement yet!"

        titleTop.text = "Her Announcements"
        ivBack.setColorFilter(resources.getColor(R.color.sblue))

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                requestMyAnnouncement()
            }
        }
        catch (e: RejectedExecutionException)
        {
            e.printStackTrace()
        }


    }

   /* @RequiresApi(Build.VERSION_CODES.P)
    fun execute(command:Runnable) {
        try {
            e.execute(command)
        } finally {
            reachabilityFence(this);
        }
    }*/

     fun String.capitalizeFirstCharacter(): String {
         return substring(0, 1).toUpperCase() + substring(1)
     }

    fun requestMyAnnouncement() {
        val map = HashMap<String, String>()
        map["user_id"] = arguments?.getString("id").toString()
        apiController.makeCall(AppConstant.my_announcements, map)
    }


    override fun onSuccess(tag: String, response: String) {
        if (tag == AppConstant.my_announcements) {
            val model = apiController.parseJson(response, AnnouncmentBean::class.java)

            Log.e("jbkdbsbf",model.result.toString())
            if( model.result.isNullOrEmpty()){
                rvItem.visibility=View.GONE
                tvNoAnnouncement.visibility=View.VISIBLE
            }
            else{
                rvItem.visibility=View.VISIBLE
                tvNoAnnouncement.visibility=View.GONE
            }

            rvItem.layoutManager = LinearLayoutManager(activity)
            rvItem.adapter = AnnouncmentListAdapter(activity!!, model.result)
            rvItem.addOnItemTouchListener(RecyclerTouchListener(activity, rvItem, RecyclerTouchListener.ClickListener { _, position ->
                val fragment = AnnouncementDetails()
                val bundle = Bundle()
                bundle.putSerializable("list", model.result[position] as Serializable)
                bundle.putString("userid",arguments?.getString("id").toString())
                fragment.arguments = bundle
                (activity as HomeActivity).replaceFragment(fragment, "AnnouncemenntDetails")


            }))

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
