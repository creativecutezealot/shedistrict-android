package com.appentus.shedistrict.adapters

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user_photo.view.*
import java.util.*


class UserPhotoAdapter(val context: Activity, val result: MutableList<UserProfileBean.ResultBean.UserPhotosBean>, val clickEvent: RecyclerViewClick) : RecyclerView.Adapter<UserPhotoAdapter.ItemHolder>() {
    var DURATION: Long = 100
    private var on_attach = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user_photo, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickEvent.onClickPos(position)
        }

        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

        //val colorPos: Int = rnd.nextInt(6)

        holder.ivFriend.borderColor = color
         if(!result[position].user_photos.isNullOrEmpty()) {
             Glide.with(context).load(AppConstant.ImageUrl+result[position].user_photos).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(holder.ivFriend)


         }
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFriend: CircleImageView = itemView.ivFriend

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
