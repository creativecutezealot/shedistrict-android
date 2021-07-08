package com.appentus.shedistrict.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.IntroBean
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_home.view.*


class IntroPageAdapter1(val context: Activity, val result: List<IntroBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var viewValue = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home1, parent, false)
            ItemHolder1(itemView)
        }
        else{
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
            ItemHolder(itemView)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                val itemHolder: ItemHolder1? = holder as ItemHolder1
                itemHolder!!.tvTitle.text = result[position].title
                itemHolder?.tvDis!!.text = result[position].dis
            }
            else -> {

                val circularProgressDrawable = CircularProgressDrawable(context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()

                val itemHolder: ItemHolder? = holder as ItemHolder
                itemHolder?.tvDis!!.text = result[position].dis
                itemHolder.tvTitle.text = result[position].title
                Glide.with(context).load(result[position].image)
                        .placeholder(circularProgressDrawable).into(itemHolder.ivImages)
            }
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDis: TextView = itemView.tvDis
        var ivImages: ImageView = itemView.ivImages
        var tvTitle: TextView = itemView.tvTitle
    }

    inner class ItemHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.tvTitle
        var tvDis: TextView = itemView.tvDis
    }

    override fun getItemViewType(position: Int): Int {
        viewValue = when (position) {
            1 -> 1
            else -> 0
        }
        return viewValue

    }


}