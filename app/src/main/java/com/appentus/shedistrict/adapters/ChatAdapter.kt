package com.appentus.shedistrict.adapters

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.MessageBean
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.view.activity.HomeActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_chat.view.*
import java.util.*


class ChatAdapter(val context: Activity, val result: MutableList<MessageBean.ResultBean>) : RecyclerView.Adapter<ChatAdapter.ItemHolder>() {

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ItemHolder(itemView)

    }

     @RequiresApi(Build.VERSION_CODES.O)
     override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val decodedBytes = Base64.getDecoder().decode(result[position].chat_message)
        val decodedString = String(decodedBytes)

        if(result[position].user_id == Utility().getUser()?.user_id){
            if(result[position].chat_file_type.equals("0")) {

                holder.tvMsg.text = decodedString
                holder.tvTime.text = Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS), context.getString(R.string.HHMMA), result[position].created.toString())
                holder.userRecive.visibility = View.GONE
                holder.userSent.visibility = View.VISIBLE
                holder.tvMsg.visibility = View.VISIBLE
                holder.ivPhotoSend.visibility = View.GONE
                Utility().setImage(context, AppConstant.ImageUrl + result[position].userprofile, holder.ivProfile)

            }else{
                holder.userRecive.visibility = View.GONE
                holder.userSent.visibility = View.VISIBLE
                holder.tvMsg.visibility = View.GONE
                holder.ivPhotoSend.visibility = View.VISIBLE

                //Utility().setImage(context, AppConstant.ImageUrl + result[position].chat_file, holder.ivPhotoSend)
               Utility().setImage(context, AppConstant.ImageUrl + result[position].userprofile, holder.ivProfile)

                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.loader)
                requestOptions.error(R.drawable.loader)

                Glide.with(context)
                        .load( AppConstant.ImageUrl+result[position].chat_file)
                        .apply(requestOptions)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into( holder.ivPhotoSend)

            } }
          else{
            if(result[position].chat_file_type.equals("0")) {
                holder.tvReceivedMsg.text = decodedString
                holder.tvTime1.text = Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS), context.getString(R.string.HHMMA), result[position].created.toString())
                holder.userRecive.visibility = View.VISIBLE
                holder.tvReceivedMsg.visibility = View.VISIBLE
                holder.userSent.visibility = View.GONE
                holder.ivPhotoRecived.visibility = View.GONE
                Utility().setImage(context, AppConstant.ImageUrl + result[position].userprofile, holder.ivReceivedProfile)
            }
            else{
                holder.userRecive.visibility = View.VISIBLE
                holder.userSent.visibility = View.GONE
                holder.tvReceivedMsg.visibility = View.GONE
                holder.ivPhotoRecived.visibility = View.VISIBLE
                Utility().setImage(context, AppConstant.ImageUrl + result[position].userprofile, holder.ivReceivedProfile)
                Utility().setImage(context, AppConstant.ImageUrl + result[position].chat_file, holder.ivPhotoRecived)

            }

            holder.ivReceivedProfile.setOnClickListener {
                val nextIntent = Intent(context, HomeActivity::class.java)
                nextIntent.putExtra("userid",result[position].user_id)
                nextIntent.putExtra("role",result[position].role)
                nextIntent.putExtra("viewprofile","viewprofile")
                context.startActivity(nextIntent)


            }

        }

        val animation: Animation = AnimationUtils.loadAnimation(context,
                if (position > lastPosition) R.anim.up_to_down else R.anim.down_to_up)
        holder.itemView.startAnimation(animation)
        lastPosition = position

    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvReceivedMsg : TextView = itemView.tvReceivedMsg
        var tvTime1 : TextView = itemView.tvTime1
        var tvMsg : TextView = itemView.tvMsg
        var tvTime : TextView = itemView.tvTime
        var userSent: LinearLayout = itemView.userSent
        var userRecive: LinearLayout = itemView.userReceived
        var ivProfile: CircleImageView = itemView.ivProfile
        var ivReceivedProfile: CircleImageView = itemView.ivReceivedProfile
        var ivPhotoSend: ImageView = itemView.ivPhotoSend
        var ivPhotoRecived: ImageView = itemView.ivPhotoRecived

    }


}