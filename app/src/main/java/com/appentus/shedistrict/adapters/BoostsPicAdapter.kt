package com.appentus.shedistrict.adapters

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.models.BoostViewBean
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.fragments.CeoProfile
import com.appentus.shedistrict.view.fragments.SearchProfile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_profilepic.view.*

class BoostsPicAdapter(val context: Activity, val result: List<BoostViewBean.ResultBean>) : RecyclerView.Adapter<BoostsPicAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoostsPicAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_profilepic, parent, false)
        return ItemHolder(itemView)
    }


     override fun onBindViewHolder(holder: BoostsPicAdapter.ItemHolder, position: Int) {

         holder.ivPics.setOnClickListener {

                 val fragment = SearchProfile()
                 val bundle = Bundle()
                 bundle.putString("userid",result[position].user_id)
                 bundle.putString("chat", result[position].user_id)
                 fragment.arguments = bundle
                 (context as HomeActivity).replaceFragment(fragment, "search")

         }

        if(!result[position].user_profile.isNullOrEmpty()) {

            Glide.with(context).load(AppConstant.ImageUrl+result[position].user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(holder.ivPicswithround)
        }



    }
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPics : CircleImageView = itemView.ivPics
        val ivPicswithround : CircleImageView = itemView.ivPicswithround

    }

    override fun getItemCount(): Int {
     return result.size
    }

}
