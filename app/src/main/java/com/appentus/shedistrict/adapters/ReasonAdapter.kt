package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R

class ReasonAdapter(val context: Activity, val result: MutableList<String>, val getClick: GetClick ) : RecyclerView.Adapter<ReasonAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_reason_report, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.etText.text = result[position]

        holder.itemView.setOnClickListener {
            getClick.click(position)
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etText : TextView = itemView.findViewById(R.id.etText)
    }


    interface GetClick{
        fun click(position: Int)
    }

}