package com.appentus.shedistrict.adapters


import android.app.Activity
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.SheRuleBean
import kotlinx.android.synthetic.main.item_she_rule.view.*


class SheRuleAdapter(val context: Activity, val result: List<SheRuleBean.ResultBean>,val value : Int) : RecyclerView.Adapter<SheRuleAdapter.ItemHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_she_rule, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {


        if(value == 1){
            val sourceString =  "<font color=\"#4C1F5C\">"+result[position].rule_id+".&nbsp;"+result[position].rule_title+"</font>"
            holder.first.text = Html.fromHtml(sourceString)
            Utility().setHtmlText(holder.second,result[position].rule_discription)
        }

        else{
            val sourceString = result[position].rule_id + ".&nbsp;" + result[position].rule_title
            holder.first.text = Html.fromHtml(sourceString)
            Utility().setHtmlText(holder.second, result[position].rule_discription)
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val first : TextView = itemView.first
        val second : TextView = itemView.second
    }


}