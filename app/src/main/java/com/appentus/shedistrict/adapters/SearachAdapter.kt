package com.appentus.shedistrict.adapters

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.models.UserByPreferenceBean
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.fragments.AllPicPrefrence
import com.appentus.shedistrict.view.fragments.CeoProfile
import com.appentus.shedistrict.view.fragments.SearchProfile
import kotlinx.android.synthetic.main.item_search.view.*


class SearachAdapter(val context: Activity, val data: ArrayList<UserByPreferenceBean>, val count: Int) : RecyclerView.Adapter<SearachAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ItemHolder(itemView)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val redString = context.getResources().getString(R.string.moree)
        Log.e("fdrjkgbdj",data[position].toString())


        if(position==0){
               holder.tvTitle.text = data[0].name.replace("_"," ")
               holder.tvMore.visibility=View.VISIBLE
           }
           else {
               holder.tvTitle.text = data[position].name.replace("_"," ")
           }
        holder.rvSubItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.rvSubItems.adapter = SearchFriendAdapter(context, data[position].data)
        holder.rvSubItems.addOnItemTouchListener(RecyclerTouchListener(context, holder.rvSubItems, RecyclerTouchListener.ClickListener { View, pos->

         if(data[position].data[pos].role.equals("0")){
             val fragment = SearchProfile()
             val bundle = Bundle()
             bundle.putString("userid", data[position].data[pos].user_id)
             bundle.putString("chat", data[position].data[pos].user_id)
             fragment.arguments = bundle
             (context as HomeActivity).replaceFragment(fragment, "search")
         }else{
             val fragment = CeoProfile()
             val bundle = Bundle()
             bundle.putString("userid",data[position].data[pos].user_id)
             fragment.arguments = bundle
             (context as HomeActivity).replaceFragment(fragment, "ceoprofile")

         }
        }))

        holder.rvSubItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val a = isLastItemDisplaying(holder.rvSubItems)
                if (a) {
                    super.onScrolled(recyclerView, dx, dy)
                    val fragment = AllPicPrefrence()
                    val bundle = Bundle()
                    bundle.putString("name", data[position].name)
                    bundle.putSerializable("piclist", data[position].data.distinct().toList() as ArrayList<UserByPreferenceBean.Data>)
                    fragment.arguments = bundle
                    (context as HomeActivity).replaceFragment(fragment, "dknlk")
                }

            }
        })



    }

    override fun getItemCount(): Int {


     /*   if (count == 3) {
            return 3
        } else {*/
            return data.size


    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.tvTitle
        val tvMore: TextView = itemView.tvMore
        val rvSubItems: RecyclerView = itemView.rvSubItems
    }

    private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        if (recyclerView.adapter!!.itemCount != 0) {
            val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == 6)
                return true
        }
        return false
    }
}




