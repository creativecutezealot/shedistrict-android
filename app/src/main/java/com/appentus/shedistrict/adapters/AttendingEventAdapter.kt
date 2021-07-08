package com.appentus.shedistrict.adapters

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.models.DaysBaseModel
import com.appentus.shedistrict.models.EventBean
import com.appentus.shedistrict.network.AppConstant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_attenddate.view.*
import kotlinx.android.synthetic.main.item_event.view.*

class AttendingEventAdapter(val context: Activity, val result: MutableList<DaysBaseModel>) : RecyclerView.Adapter<AttendingEventAdapter.ItemHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_attenddate, parent, false)
        return ItemHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        if(result[position].daysType == "Today"){
            holder.tvDateStatus.setTextColor(Color.parseColor("#6071ed"))
        }else{
            holder.tvDateStatus.setTextColor(Color.parseColor("#000000"))
        }
        holder.tvDateStatus.text=result[position].daysType
        holder.rvItem.layoutManager= LinearLayoutManager(context)
        holder.rvItem.adapter=SubAttendingEventAdapter(context,result[position].listResult)


    }


    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvItem:RecyclerView = itemView.rvItem
        val tvDateStatus :TextView = itemView.tvDateStatus
    }


    class SubAttendingEventAdapter(val context: Activity,val result:List<EventBean.AttendingBean>) : RecyclerView.Adapter<SubAttendingEventAdapter.ItemHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
            return ItemHolder(itemView)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {

            holder.tvName.text = result[position].text
            holder.tvLoc.text = result[position].meeting_location
            holder.tvDate.text = result[position].meeting_date
            holder.tvTime2.text = result[position].meeting_time
            holder.tvMsg.text = result[position].meeting_reason

            Glide.with(context).load(AppConstant.ImageUrl+result[position].user_profile)
                    .into(holder.ivProfile)

            holder.itemView.setOnClickListener {
                if (holder.llDetails.visibility==View.GONE){
                    holder.llDetails.visibility=View.VISIBLE
                    holder.ivMore.rotation=270f
                }else{
                    holder.llDetails.visibility=View.GONE
                    holder.ivMore.rotation=180f
                }
            }
        }

        override fun getItemCount(): Int {
            return result.size
        }

        inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val llDetails: LinearLayout= itemView.llDetails
            val ivMore: ImageView= itemView.ivMore
            val tvName : TextView = itemView.tvName
            val tvLoc : TextView = itemView.tvLoc
            val tvDate : TextView = itemView.tvDate
            val tvTime2 : TextView = itemView.tvTime2
            val tvMsg : TextView = itemView.tvMsg
            val ivProfile : CircleImageView = itemView.ivProfile
        }

    }


}
