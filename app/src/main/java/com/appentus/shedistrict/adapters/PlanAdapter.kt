package com.appentus.shedistrict.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import kotlinx.android.synthetic.main.item_plans.view.*


class PlanAdapter(val context: FragmentActivity, val result: MutableList<String>) : RecyclerView.Adapter<PlanAdapter.ItemHolder>() {

    private val TYPE_FULL = 0
    private val TYPE_HALF = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_plans, parent, false)
        return ItemHolder(itemView)
    }


    override fun onBindViewHolder(holder: PlanAdapter.ItemHolder, position: Int) {

     //   holder.tvPrice.text="$"+result[position].plan_price
        val str= result[position]
        holder.tvPrice.text= if (str.contains(" ")) str.split(" ").get(0) else str


        if(position==(result.size-1))
        {
            holder.ccYear.minimumWidth=getScreenWidth()
        }
        else{
            holder.ccYear.minimumWidth=(getScreenWidth()/2)
        }
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().getDisplayMetrics().widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().getDisplayMetrics().heightPixels
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvPrice = itemView.tvPrice
        var ccYear = itemView.ccYear
    }

    override fun getItemCount(): Int {
      return result.size
    }

}