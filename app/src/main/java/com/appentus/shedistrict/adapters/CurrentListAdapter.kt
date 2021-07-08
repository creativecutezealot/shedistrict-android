package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.CurrentFriendListBean
import com.appentus.shedistrict.network.AppConstant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_friendlist.view.*

class CurrentListAdapter(val context: Activity, val result: MutableList<CurrentFriendListBean.ResultBean>) : RecyclerView.Adapter<CurrentListAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_friendlist, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.ccAdd.visibility = View.GONE
        holder.ccRemove.visibility = View.VISIBLE
        holder.tvName.text=result[position].user_name
        Utility().setImage(context, AppConstant.ImageUrl+result[position].user_profile, holder.ivProfile)
       // Utility().setImage(context,result[position].user_profile,holder.ivProfile)

    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ccAdd: CardView = itemView.ccAdd
        var ccRemove : CardView = itemView.ccRemove
        var ivProfile : CircleImageView = itemView.ivProfile
        var tvName : TextView = itemView.tvName
    }


}