package com.appentus.shedistrict.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import kotlinx.android.synthetic.main.item_plans_advantages.view.*

class PlanAdvantagesAdapter(activity: FragmentActivity, val result: String, val planDescription: String) : RecyclerView.Adapter<PlanAdvantagesAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanAdvantagesAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_plans_advantages, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanAdvantagesAdapter.ItemHolder, position: Int) {

        Utility().setHtmlText(holder.tvAdvantage,result)
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvAdvantage = itemView.tvAdvantage
    }

    override fun getItemCount(): Int {
      return  1
    }

}