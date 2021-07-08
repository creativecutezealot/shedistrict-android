package com.appentus.shedistrict.adapters

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.ChooseCategoryBean
import kotlinx.android.synthetic.main.item_show.view.*


class HomeFilterAdapter(val context: Activity, val result: MutableList<ChooseCategoryBean.ResultBean>, val clickEvent:RecyclerViewClick) : RecyclerView.Adapter<HomeFilterAdapter.ItemHolder>() {
    var DURATION: Long = 100
    private var on_attach = true
    var selected = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        setAnimation(holder.itemView, position)

        if(!result[position].color.isNullOrEmpty()){
            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.roundback)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#"+result[position].color))
            holder.llCategory.background= wrappedDrawable
        }


        if(selected == position){
            holder.tvCategory.setTextColor(Color.parseColor("#ffffff"))
        }
        else{
            holder.tvCategory.setTextColor(Color.parseColor("#919191"))
        }

        Log.e("jcbjjdvjd",result[position].category_name)
        holder.tvCategory.text = result[position].category_name


        holder.tvCategory.setOnClickListener {
            holder.tvCategory.setTextColor(Color.parseColor("#ffffff"))
            clickEvent.onClickPos(position)
            selected = position
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.tvCategory
        val llCategory: LinearLayout = itemView.llCategory
    }

    private fun setAnimation(itemView: View, i: Int) {
        var i = i
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
