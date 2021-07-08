package com.appentus.shedistrict.adapters


import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.FriendListBean
import com.appentus.shedistrict.network.AppConstant
import kotlinx.android.synthetic.main.item_friendlist.view.*

class FriendListAdapter(val context: Activity, val result: MutableList<FriendListBean.UserMobileBean>) : RecyclerView.Adapter<FriendListAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_friendlist, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        Log.e("DSsdss",result.size.toString())
        holder.ccRemove.visibility = View.GONE
        holder.ccAdd.visibility = View.VISIBLE
        holder.tvName.text=result[position].user_name
       // Utility().setImage(context,result[position].user_profile,holder.ivProfile)
        Utility().setImage(context, AppConstant.ImageUrl+result[position].user_profile, holder.ivProfile)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ccRemove : CardView = itemView.ccRemove
        var ccAdd: CardView = itemView.ccAdd
        var tvName: TextView = itemView.tvName
        var ivProfile: CircleImageView = itemView.ivProfile
    }


}