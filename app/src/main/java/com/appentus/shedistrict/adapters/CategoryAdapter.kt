package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(val context: Activity, val result: MutableList<String>, val getClick:GetClick) : RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            return ItemHolder(itemView)
        }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
       /* if(result[position].is_show.equals("0")){*/

            holder.tvName.text = result[position]



        holder.itemView.setOnClickListener {
            getClick.dataClick(position)

            this@CategoryAdapter.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.tvName!!
    }


    interface GetClick{
        fun dataClick(position: Int){}
    }


}