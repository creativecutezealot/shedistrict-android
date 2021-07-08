package com.appentus.shedistrict.adapters

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.ChatBean
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.fragments.CeoProfile
import com.appentus.shedistrict.view.fragments.SearchProfile
import kotlinx.android.synthetic.main.item_conversation.view.*
import java.util.*


class InboxMsgAdapter(val context: Activity,val result:List<ChatBean.ResultBean>,val getClick:GetClick) : RecyclerView.Adapter<InboxMsgAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_conversation, parent, false)
        return ItemHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {


        val decodedBytes = Base64.getDecoder().decode(result[position].last_message)
        val decodedString = String(decodedBytes)


        holder.tvName.text = result[position].fri_name
        holder.tvMsg.text = decodedString
        holder.tvTime.text = Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS), context.getString(R.string.HHMMA), result[position].last_message_created)
        holder.itemView.setOnClickListener {
            getClick.getValue(position)
        }

        Utility().setImage(context, AppConstant.ImageUrl + result[position].fri_image, holder.ivProfile)

        if (result[position].sender_id.equals(Utility().getUser()?.user_id)) {
            holder.tvType.text = "Sent"
        } else {
            holder.tvType.text = "Received"
            holder.tvType.setTextColor(Color.parseColor("#E85AAB"));
        }

        holder.ivProfile.setOnClickListener {
            if (result[position].fri_role.equals("0")) {
                val fragment = SearchProfile()
                val bundle = Bundle()
                bundle.putString("userid", result[position].new_fri_id)
                //bundle.putString("chat", data[position].data[pos].user_id)
                fragment.arguments = bundle
                (context as HomeActivity).replaceFragment(fragment, "search")
            } else {
                val fragment = CeoProfile()
                val bundle = Bundle()
                bundle.putString("userid", result[position].new_fri_id)
                fragment.arguments = bundle
                (context as HomeActivity).replaceFragment(fragment, "ceoprofile")

            }

            val fragment = SearchProfile()
            val bundle = Bundle()
            bundle.putString("userid", result[position].new_fri_id)
            fragment.arguments = bundle
            (context as HomeActivity).replaceFragment(fragment, "search")
        }

    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tvName
        var tvMsg: TextView = itemView.tvMsg
        var tvTime: TextView = itemView.tvTime
        var tvType: TextView = itemView.tvType
        var ivProfile: CircleImageView = itemView.ivProfile
    }

    interface GetClick {
        fun getValue(position: Int) {

        }
    }

}
