package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.SecurityQuestionBean
import kotlinx.android.synthetic.main.item_purchase.view.tvName
import kotlinx.android.synthetic.main.item_single_text.view.*

class SequrityQuestionAdapter(context: Activity, val result: MutableList<SecurityQuestionBean.ResultBean>?): RecyclerView.Adapter<SequrityQuestionAdapter.ItemHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SequrityQuestionAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_single_text, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: SequrityQuestionAdapter.ItemHolder, position: Int) {

        holder.tvName.text = result?.get(position)?.question
        if (result?.get(position)!!.isSelect){
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
    }

    override fun getItemCount(): Int {
    return result!!.size
    }



}