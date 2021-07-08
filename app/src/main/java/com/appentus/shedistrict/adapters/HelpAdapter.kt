package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.HelpBean
import kotlinx.android.synthetic.main.item_help.view.*


class HelpAdapter(val context: Activity, val result: MutableList<HelpBean.ResultBean>) : RecyclerView.Adapter<HelpAdapter.ItemHolder>() {

    var buttonCode = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_help, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        holder.tvtitle.text = result[position].question

        holder.tvtitle1.text = result[position].answer

        if(position == 0){
            holder.tvtitle1.visibility = View.VISIBLE
            holder.ivDrop.rotation = 180F
            buttonCode = 1
        }


        holder.itemView.setOnClickListener {

            if(buttonCode == 0) {
                holder.tvtitle1.visibility = View.VISIBLE
                holder.ivDrop.rotation = 180F
                buttonCode = 1
            }else{
                buttonCode = 0
                holder.tvtitle1.visibility = View.GONE
                holder.ivDrop.rotation = 0F
            }
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvtitle1: TextView = itemView.tvtitle1
        var tvtitle: TextView = itemView.tvtitle
        var ivDrop: ImageView = itemView.ivDrop

    }
}