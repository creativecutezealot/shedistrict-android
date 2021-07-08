package com.appentus.shedistrict.adapters


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.BlockedUserBeans
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.fragments.BlockedFragment
import com.appentus.shedistrict.view.fragments.PrivacyFragment
import kotlinx.android.synthetic.main.item_blocked.view.*

class BlockedAdapter(val context: Activity, val result: MutableList<BlockedUserBeans.ResultArrayBean>) : RecyclerView.Adapter<BlockedAdapter.ItemHolder>(),ResponseListener {
    lateinit var apiController: ApiController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_blocked, parent, false)
        return ItemHolder(itemView)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        apiController= ApiController(context!!,this)

        Utility().setImage(context, AppConstant.ImageUrl+result[position].user_profile, holder.ivProfile)
        Utility().setText(result[position].user_name,holder.tvName)
        Utility().setText(result[position].created,holder.tvMsg)

        holder.tvUnblock.setOnClickListener(){
             unblockuser(result[position].block_user_id)

        }



    }

    private fun unblockuser(resultArrayBean: String) {
       val map=HashMap<String,String>()
        map["user_id"]= Utility().getUser()?.user_id.toString()
        map["block_user_id"] = resultArrayBean
        apiController.makeCall(AppConstant.User_unblock,map)

    }


    override fun getItemCount(): Int {
        return result.size
    }



    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfile: ImageView = itemView.ivProfile
        val tvName: TextView = itemView.tvName
        val tvMsg: TextView = itemView.tvMsg
        val tvUnblock: TextView = itemView.tvUnblock

    }
    override fun onSuccess(tag: String, response: String) {
        (context as HomeActivity).replaceFragment(BlockedFragment(), "more")
    }

    override fun onFailure(tag: String, msg: String) {
     Utility().showSnackBar(context,msg)
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(context,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(context,msg)
    }


}