package com.appentus.shedistrict.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.network.AppConstant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class HomeItemAdapter(val context: Activity,val result: List<AnnouncmentBean.ResultBean>) : RecyclerView.Adapter<HomeItemAdapter.ItemHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        holder.tvTitle.text = result[position].announcement_title
        if(!result[position].announcement_image.isNullOrEmpty()){
            if(result[position].image_type.equals("1")){

            Glide.with(context).load(AppConstant.ImageUrl+result[position].announcement_image)
                    .into(holder.ivProfile)

            }else{
                Glide.with(context).load(result[position].announcement_image)
                        .into(holder.ivProfile)
            }
        }
        when {
            Utility().getDateAgo(context.getString(R.string.yyyMMddHHMMSS), result[position].created.toString()) == 0 -> {
                holder.tvTime.text = "Today " + Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS), context.getString(R.string.HHMMA), result[position].created.toString())
            }
            Utility().getDateAgo(context.getString(R.string.yyyMMddHHMMSS), result[position].created.toString()) == 1 -> {
                holder.tvTime.text = Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS), context.getString(R.string.HHMMA), result[position].created.toString())
            }
            Utility().getDateAgo(context.getString(R.string.yyyMMddHHMMSS), result[position].created.toString()) == 3 -> {
                holder.tvTime.text = Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS), context.getString(R.string.HHMMA), result[position].created.toString())

            }

        }
       /* holder.itemView.setOnClickListener {
            getView.postAnnouncement(position)
        }*/
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val tvTitle: TextView = itemView.tvName
        val tvTime : TextView = itemView.tvTime
        val ivProfile : CircleImageView =itemView.ivProfile

    }


}
