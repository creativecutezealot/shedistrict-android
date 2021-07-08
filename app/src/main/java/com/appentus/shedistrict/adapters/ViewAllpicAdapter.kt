package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.UserByPreferenceBean
import com.appentus.shedistrict.network.AppConstant
import kotlinx.android.synthetic.main.item_user_photo.view.*

class ViewAllpicAdapter(val context: Activity, val result: ArrayList<UserByPreferenceBean.Data>): RecyclerView.Adapter<ViewAllpicAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllpicAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user_search, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        Utility().setImage(context, AppConstant.ImageUrl+result[position].user_profile, holder.ivFriend)


    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFriend: CircleImageView = itemView.ivFriend
    }
    override fun getItemCount(): Int {
        return result.size
    }



}