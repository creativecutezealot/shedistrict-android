package com.appentus.nutrition.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.HomeItemAdapter
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.activity.PostDetailActivity
import com.appentus.shedistrict.view.fragments.ForumFragment
import kotlinx.android.synthetic.main.fragment_your_announcement.*
import java.io.Serializable


class YourAnnouncementFragment : Fragment(),ResponseListener{

    lateinit var apiController: ApiController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_your_announcement, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!,this)
        requestMyAnnouncement()
    }


    fun requestMyAnnouncement(){
        val map = HashMap<String,String>()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.my_announcements,map)
    }

    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.my_announcements) {
            val model = apiController.parseJson(response, AnnouncmentBean::class.java)

            rvItem.layoutManager = LinearLayoutManager(activity)
            rvItem.adapter=HomeItemAdapter(activity!!,model.result)
            rvItem.addOnItemTouchListener(RecyclerTouchListener(activity, rvItem, RecyclerTouchListener.ClickListener { _, position ->
                val fragment = PostDetailActivity()
                val bundle = Bundle()
                bundle.putSerializable("list",model.result[position] as Serializable)
                fragment.arguments = bundle
                (activity as HomeActivity ).replaceFragment(fragment, "postdetail")

            }))

          /*   rvItem.adapter=HomeItemAdapter(activity!!,model.result,object:HomeItemAdapter.GetView{
                override fun postAnnouncement(position: Int) {
                    val fragment = PostDetailActivity()
                    val bundle = Bundle()
                    bundle.putString("list", (model.result[position] as Serializable).toString())
                      fragment.arguments = bundle
                    replaceFragment(fragment, "postdetail")

                    //nextIntent.putExtra("list",model.result[position] as Serializable)
                   // startActivityForResult(nextIntent,2)
                }
            })*/

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 2 && resultCode == Activity.RESULT_OK){
            requestMyAnnouncement()
        }
    }
    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
        fragmentTransaction.replace(R.id.framchild, fragment, tag)
        fragmentTransaction.commit()
    }
}