package com.appentus.shedistrict.adapters

import android.app.Activity
import android.graphics.Color
import android.graphics.Color.*
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.network.AppConstant
import com.bumptech.glide.Glide
import com.sdsmdg.harjot.materialshadows.MaterialShadowViewWrapper
import kotlinx.android.synthetic.main.announcement_list.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.android.synthetic.main.item_user.view.tvName
import kotlinx.android.synthetic.main.item_user.view.tvTime

class AnnouncmentListAdapter(val context: Activity, val result: MutableList<AnnouncmentBean.ResultBean>) : RecyclerView.Adapter<AnnouncmentListAdapter.ItemHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncmentListAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.announcement_list, parent, false)
        return ItemHolder(itemView)

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: AnnouncmentListAdapter.ItemHolder, position: Int) {
        holder.tvTitle.text = result[position].announcement_title

        if(!result[position].category.isNullOrEmpty()) {
            holder.cclayouts.setBackgroundColor(Color.parseColor("#" + result[position].category[0].color))
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
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tvName
        val tvTime : TextView = itemView.tvTime
        val cclayouts : CardView = itemView.cclayouts

    }


    override fun getItemCount(): Int {
        return  result.size
    }

}

