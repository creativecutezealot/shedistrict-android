package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.IntrestBean
import kotlinx.android.synthetic.main.item_single_text.view.*

class AddsubIntrestAdapter(context: Activity,  val result: MutableList<IntrestBean.ResultBean.ValueBean>): RecyclerView.Adapter<AddsubIntrestAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddsubIntrestAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_single_text, parent, false)
        return ItemHolder(itemView)
    }

        override fun getItemCount(): Int {
            return result.size
    }

    override fun onBindViewHolder(holder: AddsubIntrestAdapter.ItemHolder, position: Int) {
        holder.tvName.text = result[position].interest_value


        if (result[position].isSelected){
            holder.cbPersonalUpdate.isSelected = true
            holder.cbPersonalUpdate.setImageResource(R.drawable.select)
        }else{
            holder.cbPersonalUpdate.isSelected = false
            holder.cbPersonalUpdate.setImageResource(R.drawable.unselect)
        }


    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.tvName
        var root = itemView.root
        var cbPersonalUpdate = itemView.cbPersonalUpdate
      //  var icon = itemView.ivSelected
    }

}