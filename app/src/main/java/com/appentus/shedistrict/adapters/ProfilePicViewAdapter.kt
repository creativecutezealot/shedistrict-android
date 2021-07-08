package com.appentus.shedistrict.adapters

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.models.ProfileViewerBean
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.fragments.CeoProfile
import com.appentus.shedistrict.view.fragments.SearchProfile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_profilepic.view.*

class ProfilePicViewAdapter(val context: Activity, val result: MutableList<ProfileViewerBean.ResultBean>,val resultplan: String?) : RecyclerView.Adapter<ProfilePicViewAdapter.ItemHolder>(){
    var returnValue=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePicViewAdapter.ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_profilepic, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
//        Log.e("bfcjsd",resultplan)
holder.ivPics.setOnClickListener {
    if(result[position].role.equals("0")){
        val fragment = SearchProfile()
        val bundle = Bundle()
        bundle.putString("userid",result[position].user_id)
        bundle.putString("chat", result[position].user_id)
        fragment.arguments = bundle
        (context as HomeActivity).replaceFragment(fragment, "search")
    }else{
        val fragment = CeoProfile()
        val bundle = Bundle()
        bundle.putString("userid", result[position].user_id)
        fragment.arguments = bundle
        (context as HomeActivity).replaceFragment(fragment, "ceoprofile")

    }
}
        holder.ivPicswithround.setOnClickListener {
            if(result[position].role.equals("0")){
                val fragment = SearchProfile()
                val bundle = Bundle()
                bundle.putString("userid",result[position].user_id)
                bundle.putString("chat", result[position].user_id)
                fragment.arguments = bundle
                (context as HomeActivity).replaceFragment(fragment, "search")
            }else{
                val fragment = CeoProfile()
                val bundle = Bundle()
                bundle.putString("userid", result[position].user_id)
                fragment.arguments = bundle
                (context as HomeActivity).replaceFragment(fragment, "ceoprofile")

            }
        }



        if(!resultplan.isNullOrEmpty()) {
            if(resultplan.equals("plans_1")&& position<10){

                if(!result[position].user_profile.isNullOrEmpty()) {
                    Log.e("fdffgfdgf",result[position].is_boosts.toString())

                    if (result[position].is_boosts == 1) {
                        Log.e("jsjdjsbj", result[position].is_boosts.toString())
                        holder.ivPicswithround.visibility = View.VISIBLE
                        holder.ivPics.visibility = View.GONE
                        Glide.with(context.getApplicationContext())
                                .load(AppConstant.ImageUrl + result[position].user_profile)
                                .override(15, 15) // (change according to your wish)
                                .error(R.drawable.ic_user)
                                .placeholder(R.drawable.ic_user)
                                .into(holder.ivPicswithround)
                    }
                    else {
                        Log.e("sdsasas", result[position].is_boosts.toString())
                        Glide.with(context).load(AppConstant.ImageUrl + result[position].user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(holder.ivPics)
                        holder.ivPicswithround.visibility = View.GONE
                        holder.ivPics.visibility = View.VISIBLE
                    }
                }

            }else if(resultplan.equals("plans_2")&& position<20){
                if(!result[position].user_profile.isNullOrEmpty()) {
                    Log.e("fdffgfdgf", result[position].is_boosts.toString())

                    if (result[position].is_boosts == 1) {
                        Log.e("jsjdjsbj", result[position].is_boosts.toString())
                        holder.ivPicswithround.visibility = View.VISIBLE
                        holder.ivPics.visibility = View.GONE
                        Glide.with(context.getApplicationContext())
                                .load(AppConstant.ImageUrl + result[position].user_profile)
                                .placeholder(R.drawable.ic_user)
                                .override(15, 15) // (change according to your wish)
                                .error(R.drawable.ic_user)
                                .into(holder.ivPicswithround)
                    }
                    else {
                        Log.e("sdsasas", result[position].is_boosts.toString())
                        Glide.with(context).load(AppConstant.ImageUrl + result[position].user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(holder.ivPics)
                        holder.ivPicswithround.visibility = View.GONE
                        holder.ivPics.visibility = View.VISIBLE
                    }
                }
            }
            else if(resultplan.equals("plans_3")) {
                        if (!result[position].user_profile.isNullOrEmpty()) {
                            Log.e("fdffgfdgf", result[position].is_boosts.toString())

                            if (result[position].is_boosts == 1) {
                                Log.e("jsjdjsbj", result[position].is_boosts.toString())
                                holder.ivPicswithround.visibility = View.VISIBLE
                                holder.ivPics.visibility = View.GONE

                                Glide.with(context.getApplicationContext())
                                        .load(AppConstant.ImageUrl + result[position].user_profile)
                                        .override(15, 15) // (change according to your wish)
                                        .error(R.drawable.ic_user)
                                        .placeholder(R.drawable.ic_user)
                                        .into(holder.ivPicswithround)
                            }
                            else {
                                Log.e("sdsasas", result[position].is_boosts.toString())
                                Glide.with(context).load(AppConstant.ImageUrl + result[position].user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(holder.ivPics)
                                holder.ivPicswithround.visibility = View.GONE
                                holder.ivPics.visibility = View.VISIBLE
                            }
                        }

                    else {
                        Log.e("sdsasas", result[position].is_boosts.toString())
                        holder.ivPicswithround.visibility = View.GONE
                        holder.ivPics.visibility = View.VISIBLE

                        Glide.with(context.getApplicationContext())
                                .load(AppConstant.ImageUrl+result[position].user_profile)
                                .override(15, 15) // (change according to your wish)
                                .error(R.drawable.ic_user)
                                .placeholder(R.drawable.ic_user)
                                .into(holder.ivPics)

                    }

            }
            else{
                if(!result[position].user_profile.isNullOrEmpty()) {
                    Log.e("fdffgfdgf",result[position].is_boosts.toString())

                    if(result[position].is_boosts==1){
                        Log.e("jsjdjsbj",result[position].is_boosts.toString())
                        holder.ivPicswithround.visibility=View.VISIBLE
                        holder.ivPics.visibility=View.GONE

                    }
                    else {
                        Log.e("sdsasas", result[position].is_boosts.toString())
                        Glide.with(context).load(AppConstant.ImageUrl + result[position].user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(holder.ivPics)
                        holder.ivPicswithround.visibility = View.GONE
                        holder.ivPics.visibility = View.VISIBLE
                    }
                }
            }

        }
        else if(position<5) {
            if (!result[position].user_profile.isNullOrEmpty()) {
                Log.e("fdffgfdgf", result[position].is_boosts.toString())


                if (result[position].is_boosts == 1) {
                    Log.e("jsjdjsbj", result[position].is_boosts.toString())
                    holder.ivPicswithround.visibility = View.VISIBLE
                    holder.ivPics.visibility = View.GONE
                    Glide.with(context.getApplicationContext())
                            .load(AppConstant.ImageUrl + result[position].user_profile)
                            .override(15, 15) // (change according to your wish)
                            .error(R.drawable.ic_user)
                            .placeholder(R.drawable.ic_user)
                            .into(holder.ivPicswithround)
                } else {
                    Log.e("sdsasas", result[position].is_boosts.toString())
                    Glide.with(context).load(AppConstant.ImageUrl + result[position].user_profile).placeholder(R.drawable.ic_user).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(holder.ivPics)
                    holder.ivPicswithround.visibility = View.GONE
                    holder.ivPics.visibility = View.VISIBLE
                }
            }
        }else{
            Glide.with(context.getApplicationContext())
                    .load(AppConstant.ImageUrl + result[position].user_profile)
                    .override(15, 15) // (change according to your wish)
                    .error(R.drawable.ic_user)
                    .placeholder(R.drawable.ic_user)
                    .into(holder.ivPicswithround)

        }

    }

    override fun getItemCount(): Int {
        return result.size
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPics : CircleImageView = itemView.ivPics
       val ivPicswithround : CircleImageView = itemView.ivPicswithround

    }
}