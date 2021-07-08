package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.FriendListBean
import com.appentus.shedistrict.network.AppConstant
import kotlinx.android.synthetic.main.item_meet.view.*

class MeetAdapter(val context: Activity, val result: MutableList<FriendListBean.ResultBean>,val getClick:GetClick) : RecyclerView.Adapter<MeetAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_meet, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.tvName.text = result[position].user_name


        if(!result[position].user_profile.isNullOrEmpty()) {
            if (result[position].user_social.isNullOrEmpty()) {
                Utility().setImage(context, AppConstant.ImageUrl+result[position].user_profile, holder.ivProfile)
            }
            else{
                Utility().setImage(context, result[position].user_profile, holder.ivProfile)
            }
        }


        holder.itemView.setOnClickListener {
            getClick.dataClick(position)

            this@MeetAdapter.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.tvName
        val ivProfile = itemView.ivProfile
    }


    interface GetClick{
        fun dataClick(position: Int){}
    }


}