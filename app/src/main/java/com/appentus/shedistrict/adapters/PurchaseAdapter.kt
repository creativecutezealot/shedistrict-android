package com.appentus.shedistrict.adapters

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import kotlinx.android.synthetic.main.item_purchase.view.*

class PurchaseAdapter(val context: Activity, val result: MutableList<String>, val price: MutableList<String>) : RecyclerView.Adapter<PurchaseAdapter.ItemHolder>() {
    var DURATION: Long = 100
    private var on_attach = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_boost, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        setAnimation(holder.itemView, position)

        Log.e("kkiuiu",price.toString())
        Log.e("jbjbbj",result.toString())

        val str= result[position]
        holder.tvName.text =if (str.contains(" ")) str.split(" ").get(0) else str
        holder.tvPrice.text = price?.get(position)
    }

    override fun getItemCount(): Int {
        return result!!.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tvName
        val tvPrice : TextView = itemView.tvPrice
    }

    private fun setAnimation(itemView: View, value: Int) {
        var i = value
        if (!on_attach) {
            i = -1
        }
        val isNotFirstItem = i == -1
        i++
        itemView.alpha = 0f
        val animatorSet = AnimatorSet()
        val animator = ObjectAnimator.ofFloat(itemView, "alpha", 0f, 0.5f, 1.0f)
        ObjectAnimator.ofFloat(itemView, "alpha", 1f).start()
        animator.startDelay = if (isNotFirstItem) DURATION / 2 else i * DURATION / 3
        animator.duration = 100
        animatorSet.play(animator)
        animator.start()
    }
}
