package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.PrefrenceBean
import kotlinx.android.synthetic.main.item_preference.view.*
import kotlinx.android.synthetic.main.item_show.view.*
import kotlinx.android.synthetic.main.item_single_text.view.*

class AddSubPrefrenceAdapter(context: Activity, val result: MutableList<PrefrenceBean.ResultBean.ValuesBean>) : RecyclerView.Adapter<AddSubPrefrenceAdapter.ItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSubPrefrenceAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_single_text, parent, false)
        return ItemHolder(itemView)
    }

    override fun getItemCount(): Int {
        return result?.size!!
    }

    override fun onBindViewHolder(holder: AddSubPrefrenceAdapter.ItemHolder, position: Int) {
        holder.tvName.text =result[position].value_name
        if (result[position].isSelect){
            holder.cbPersonalUpdate.isSelected = true
            holder.cbPersonalUpdate.setImageResource(R.drawable.prefrenceselect)
        }else{
            holder.cbPersonalUpdate.isSelected = false
            holder.cbPersonalUpdate.setImageResource(R.drawable.unselect)
        }

    }
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.tvName
        var root: LinearLayout = itemView.root
        var cbPersonalUpdate: ImageView = itemView.cbPersonalUpdate
    }

}