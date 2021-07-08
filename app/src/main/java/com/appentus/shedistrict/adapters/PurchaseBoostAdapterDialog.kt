package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginLeft
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import kotlinx.android.synthetic.main.item_purchase.view.*

class PurchaseBoostAdapterDialog(val context: Activity, val results: MutableList<String>, val skuBoostListPrice : MutableList<String>): RecyclerView.Adapter<PurchaseBoostAdapterDialog.ItemHolder>() {
    var lastindex =-1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseBoostAdapterDialog.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_purchase, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: PurchaseBoostAdapterDialog.ItemHolder, position: Int) {
        val str= results[position]
        holder.tvName.text =if (str.contains(" ")) str.split(" ").get(0) else str
        holder.tvPrice.text = skuBoostListPrice?.get(position)

        if(lastindex== position){

            holder.rlBoosts.background = context.resources.getDrawable(R.drawable.background_boosts)

          }else{

              holder.rlBoosts.background = context.resources.getDrawable(R.color.white)

        }

        holder.rlBoosts.setOnClickListener {

            lastindex=position
            notifyDataSetChanged()

        }


    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tvName
        val tvPrice : TextView = itemView.tvPrice
        val rlBoosts : RelativeLayout = itemView.rlBoosts
       // val ccAllAccess : RelativeLayout = itemView.ccAllAccess

    }

    override fun getItemCount(): Int {
        return results.size
    }

}
