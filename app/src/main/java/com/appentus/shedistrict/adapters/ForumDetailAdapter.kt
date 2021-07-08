package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import kotlinx.android.synthetic.main.item_forum.view.*
import kotlinx.android.synthetic.main.item_subforum.view.*


class ForumDetailAdapter(val context: Activity) : RecyclerView.Adapter<ForumDetailAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_forum, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.rvItems.layoutManager= LinearLayoutManager(context)
        holder.rvItems.adapter= SubItemAdapter(context)

    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvItems = itemView.rvItem
    }



    inner class SubItemAdapter(val context: Activity) : RecyclerView.Adapter<SubItemAdapter.ItemHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_subforum, parent, false)
            return ItemHolder(itemView)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {

            holder.title1.text  = "Rules"
            holder.title2.text = "4"
            holder.title3.visibility = View.INVISIBLE
        }

        override fun getItemCount(): Int {
            return 3
        }

        inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val title1 = itemView.tvstitle
            val title2 = itemView.tvstitle1
            val title3 = itemView.tvstitle2
        }
    }
}
