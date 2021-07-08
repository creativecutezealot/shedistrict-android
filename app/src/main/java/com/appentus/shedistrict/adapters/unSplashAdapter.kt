package com.appentus.shedistrict.adapters

import UnsplashBean
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.unspalced_image.view.*

class unSplashAdapter(val context: Activity, val result: MutableList<UnsplashBean>): RecyclerView.Adapter<unSplashAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): unSplashAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.unspalced_image, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: unSplashAdapter.ItemHolder, position: Int) {
        Glide.with(context).load(result[position].urls.small).placeholder(R.drawable.imageplaceholder).apply(RequestOptions.errorOf(R.drawable.imageplaceholder)).into(holder.ivimage)
        Log.e("ncsk",result[position].urls.small)
    }




    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivimage = itemView.ivimage!!
    }

    override fun getItemCount(): Int {
        return result.size
    }

}