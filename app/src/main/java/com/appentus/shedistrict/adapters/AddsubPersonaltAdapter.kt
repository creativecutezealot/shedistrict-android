package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.PrefrenceBean
import kotlinx.android.synthetic.main.item_single_text.view.*
import org.json.JSONObject

class AddsubPersonaltAdapter(context: Activity, val result: MutableList<PrefrenceBean.ResultBean.ValuesBean>): RecyclerView.Adapter<AddsubPersonaltAdapter.ItemHolder>()  {

    lateinit var json: JSONObject
    lateinit var list : MutableList<String>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddsubPersonaltAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_single_text, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: AddsubPersonaltAdapter.ItemHolder, position: Int) {
        holder.tvName.text =result[position].value_name

        if (result[position].isSelect){
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
        //var icon = itemView.ivSelected
    }

    override fun getItemCount(): Int {
        return result.size
    }
}