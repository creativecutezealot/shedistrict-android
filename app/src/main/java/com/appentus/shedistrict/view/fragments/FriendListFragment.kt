package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager

import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.SheDistrict
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.CurrentListAdapter
import com.appentus.shedistrict.adapters.FriendListAdapter
import com.appentus.shedistrict.models.CurrentFriendListBean
import com.appentus.shedistrict.models.FriendListBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_friend_list.*
import kotlinx.android.synthetic.main.toolbar_title.*

/**
 * A simple [Fragment] subclass.
 */
class FriendListFragment : Fragment(),ResponseListener {
    lateinit var apiController: ApiController
    lateinit var list: MutableList<FriendListBean.UserMobileBean>
    lateinit var Curentfriendlist: MutableList<CurrentFriendListBean.ResultBean>
    var keyword=""
    var adapter:CurrentListAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!, this)
        list = ArrayList()
        Curentfriendlist = ArrayList()
        titleTop.text = "Friends List"
        getfriendlist(keyword)
        getcurrentfriendlist()

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                keyword=etSearch.text.toString()
                getfriendlist(keyword)

                true
            } else {
                false
            }
        }
        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }


    }

    private fun getcurrentfriendlist() {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_she_friends, map)
    }

    fun getfriendlist(keyword: String) {
       val map = HashMap<String, String>()
       map["user_lat"] = SheDistrict.getLocation().latitude.toString()
       map["user_lang"] = SheDistrict.getLocation().longitude.toString()
       map["user_id"] = Utility().getUser()?.user_id.toString()
       map["keyword"] = keyword
       apiController.makeCall(AppConstant.get_friend_list, map)
   }



    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.get_friend_list){
            val model = apiController.parseJson(response,FriendListBean::class.java)
            list = model.user_mobile
         //   count.text = list.size.toString()
            Log.e("bnnn",list.size.toString())
            Log.e("sdsfdfddf",list.toString())
            rvItemAll.layoutManager = LinearLayoutManager(activity)
            rvItemAll.adapter = FriendListAdapter(activity!!,list)
            rvItemAll.addOnItemTouchListener(RecyclerTouchListener(context, rvItemAll,
                    RecyclerTouchListener.ClickListener { view, pos ->
                        Log.e("sfds",Curentfriendlist.size.toString())
                        if(Curentfriendlist.size>= 4){
                            Utility().showSnackBar(activity!!,"Maximum Four friennds allowed")
                        }else{

                            val map = HashMap<String, String>()
                            map["user_id"] = Utility().getUser()?.user_id.toString()
                            map["friend_id"] = model.user_mobile[pos].user_id.toString()
                            Log.e("hvhk",model.user_mobile[pos].user_id.toString())
                            apiController.makeCall(AppConstant.add_friend, map)
                        }

                    }
            ))
        }else if(tag == AppConstant.add_friend){
            Utility().showSnackBar(activity!!,"Successfully added")
            val map = HashMap<String, String>()
            map["user_id"] = Utility().getUser()?.user_id.toString()
            apiController.makeCall(AppConstant.get_she_friends, map)
        }
        else if(tag == AppConstant.get_she_friends){
            val model=apiController.parseJson(response,CurrentFriendListBean::class.java)
            Curentfriendlist=model.result
            rvItemCurrent.layoutManager= LinearLayoutManager(activity)
            adapter= CurrentListAdapter(activity!!,Curentfriendlist)
            rvItemCurrent.adapter= adapter

            rvItemCurrent.addOnItemTouchListener(RecyclerTouchListener(context, rvItemCurrent,
                    RecyclerTouchListener.ClickListener { view, pos ->
                        val map = HashMap<String, String>()
                        map["user_id"] = Utility().getUser()?.user_id.toString()
                        map["friend_id"] = model.result[pos].user_id.toString()
                        apiController.makeCall(AppConstant.remove_friend, map)

                    }
            ))

        } else if(tag == AppConstant.remove_friend){

            Utility().showSnackBar(activity!!,"Remove Successfully")
            val map = HashMap<String, String>()
            map["user_id"] = Utility().getUser()?.user_id.toString()
            apiController.makeCall(AppConstant.get_she_friends, map)

        }
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
