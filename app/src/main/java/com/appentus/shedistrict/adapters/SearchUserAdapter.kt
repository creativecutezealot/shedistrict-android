package com.appentus.shedistrict.adapters

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.models.FriendListBean
import com.bumptech.glide.Glide
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.AppConstant
import kotlinx.android.synthetic.main.item_user_search.view.*

class SearchUserAdapter(val context: Activity, val result: MutableList<FriendListBean.ResultBean>, val clickEvent:RecyclerViewClick) : RecyclerView.Adapter<SearchUserAdapter.ItemHolder>() {
    var DURATION: Long = 100
    private var on_attach = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user_search, parent, false)

        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickEvent.onClickPos(position)
        }


        if(!result[position].user_profile.isNullOrEmpty()) {
            if (result[position].user_social.isNullOrEmpty()) {



                Utility().setImage(context, AppConstant.ImageUrl+result[position].user_profile, holder.ivFriend)
            }
            else{
                Utility().setImage(context, result[position].user_profile, holder.ivFriend)
            }
        }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val ivFriend: ImageView = itemView.ivFriend

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
