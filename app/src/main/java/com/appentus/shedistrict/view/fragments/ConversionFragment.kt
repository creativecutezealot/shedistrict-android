package com.appentus.nutrition.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.InboxMsgAdapter
import com.appentus.shedistrict.adapters.RecyclerViewClick
import com.appentus.shedistrict.adapters.SearchUserAdapter
import com.appentus.shedistrict.models.ChatBean
import com.appentus.shedistrict.models.FriendListBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.ChatActivity
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_conversion.*


class ConversionFragment : Fragment(), ResponseListener {

    lateinit var controller: LayoutAnimationController
    lateinit var apiController: ApiController

    var list: MutableList<FriendListBean.ResultBean> = ArrayList()
    var list1: MutableList<ChatBean.ResultBean> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_conversion, container, false)

    }

      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiController = ApiController(activity!!, this)
        list = ArrayList()
        list1 = ArrayList()
        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout)
        getFriendList()
        getChat()
    }


    fun getFriendList() {
        val map = HashMap<String, String>()
        map["user_lat"] = SheDistrict.getLocation().latitude.toString()
        map["user_lang"] = SheDistrict.getLocation().longitude.toString()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        map["keyword"] = ""
        apiController.makeCall(AppConstant.get_friend_list, map)
    }

    fun getChat(){
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.chat_list, map)
    }

    fun setAdapter() {
        rvFriends.layoutAnimation = controller
        rvFriends.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvFriends.adapter = SearchUserAdapter(activity!!, list, object : RecyclerViewClick {
            override fun onClickPos(pos: Int) {
                val nextIntent = Intent(activity!!,ChatActivity::class.java)
                nextIntent.putExtra("text",list[pos].user_name)
                nextIntent.putExtra("id",list[pos].user_id)
                Log.e("ihgdi",list[pos].user_id)
                startActivity(nextIntent)
            }
        })
    }


    fun chatAdpater(){
        rvConversations.layoutAnimation = controller
        rvConversations.layoutManager = LinearLayoutManager(activity)
        rvConversations.adapter = InboxMsgAdapter(activity!!,list1,object:InboxMsgAdapter.GetClick{
            override fun getValue(position: Int) {
                val nextIntent = Intent(activity!!,ChatActivity::class.java)
                nextIntent.putExtra("text",list1[position].fri_name)
                nextIntent.putExtra("id",list1[position].new_fri_id)
                startActivity(nextIntent)
            }
        })
    }
    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).activeTab(3)
    }


    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.get_friend_list){
            val model = apiController.parseJson(response,FriendListBean::class.java)
            list = model.result
            count.text = list.size.toString()
            setAdapter()
        }
        else if(tag == AppConstant.chat_list){
            val model = apiController.parseJson(response, ChatBean::class.java)
            list1 = model.result
            chatAdpater()
        }
    }


    override fun onError(tag: String, msg: String) {
      //  Utility().showSnackBar(activity!!,msg)
    }

    override fun onFailure(tag: String, msg: String) {
      //  Utility().showSnackBar(activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }


    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }


}