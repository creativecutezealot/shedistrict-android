package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.InfoBean
import kotlinx.android.synthetic.main.item_info.view.*

class InfoAdapter(val context: Activity, val list : ArrayList<InfoBean>) : RecyclerView.Adapter<InfoAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.tvQue.text = list[position].que
        holder.tvAns.text = list[position].ans

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQue: TextView = itemView.tvQue
        val tvAns : TextView = itemView.tvAns

    }

}
