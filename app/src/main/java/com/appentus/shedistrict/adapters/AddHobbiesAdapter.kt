package com.appentus.shedistrict.adapters


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.HobbiesBean
import kotlinx.android.synthetic.main.item_single_text.view.*

class AddHobbiesAdapter(val context: Activity, val result: MutableList<HobbiesBean.ResultBean>) : RecyclerView.Adapter<AddHobbiesAdapter.ItemHolder>() {

     var lastPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_single_text, parent, false)
       return ItemHolder(itemView)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.tvName.text = result[position].hobbies_name

        holder.itemView.setOnClickListener{
            if (result[position].isSelected){
                result[position].isSelected = false
                notifyDataSetChanged()
            }else{
                result[position].isSelected = true
                notifyDataSetChanged()
            }
        }

        setAnimation(holder.root,position)
    }
    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.tvName
        var root = itemView.root
    }


    private fun setAnimation(viewToAnimate: View, position: Int) { // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(context,R.anim.item_animation_fall_down)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}