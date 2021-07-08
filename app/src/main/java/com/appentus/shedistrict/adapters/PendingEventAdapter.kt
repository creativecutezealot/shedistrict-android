package com.appentus.shedistrict.adapters

import android.app.Activity
import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.EventBean
import com.appentus.shedistrict.network.AppConstant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class PendingEventAdapter(val context: Activity,val result: MutableList<EventBean.PendingBean>,val getClick: GetClick) : RecyclerView.Adapter<PendingEventAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.tvTime.text = result[position].meeting_time
        val strModified = result[position].fri_name.capitalize()

        Glide.with(context).load(AppConstant.ImageUrl+result[position].user_profile)
                .into(holder.ivProfile)

        holder.itemView.setOnClickListener {
            getClick.getStatus(position)
        }
        if(result[position].type == "received"){
            val text = "<font color=#000000>$strModified</font> <font color=#919191>invited you to an event</font>"
            holder.tvName.text = (Html.fromHtml(text))
            holder.ivMore.visibility = View.VISIBLE
        }else{
            val text = "<font color=#919191>You have invited</font> <font color=#000000>$strModified</font>"
            holder.tvName.text = (Html.fromHtml(text))
            holder.ivMore.visibility = View.GONE
        }

        holder.ivMore.setTextColor(Color.parseColor("#6071ed"))

    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tvName
        val tvTime : TextView = itemView.tvTime
        val ivMore : TextView = itemView.ivMore
        val ivProfile : ImageView = itemView.ivProfile
    }



    interface GetClick{
         fun getStatus(position: Int){}
    }
}
