package com.appentus.shedistrict.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.adapters.DescrimeAdapter.*
import com.appentus.shedistrict.models.DescribeMeBean
import com.appentus.shedistrict.view.activity.EditProfileActivity
import kotlinx.android.synthetic.main.item_category.view.*

class DescrimeAdapter(editProfileActivity: EditProfileActivity,val result:MutableList<DescribeMeBean.ResultBean>, val getClick: GetClick): RecyclerView.Adapter<DescrimeAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ItemHolder(itemView)
    }



    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.tvName.text = result[position].value

        holder.itemView.setOnClickListener {
            getClick.dataClick(position)

            this@DescrimeAdapter.notifyDataSetChanged()
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