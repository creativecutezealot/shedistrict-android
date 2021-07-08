package com.appentus.shedistrict.adapters


import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.CurrentFriendListBean
import com.appentus.shedistrict.network.AppConstant
import kotlinx.android.synthetic.main.item_friendlist.view.ivProfile
import kotlinx.android.synthetic.main.item_friendlist.view.tvName
import kotlinx.android.synthetic.main.item_message.view.*


class MessageAdapter(val context: Activity, val result: MutableList<CurrentFriendListBean.ResultBean>) : RecyclerView.Adapter<MessageAdapter.ItemHolder>() {

    var selectall:Boolean=true
    val list: ArrayList<Int> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Log.e("ndkdn",result[position].user_name)
        Log.e("ndkxxccdn",result.size.toString())

        holder.tvName.text=result[position].user_name
        Utility().setImage(context,AppConstant.ImageUrl+result[position].user_profile,holder.ivProfile)

        if(selectall== true){
            holder.chValue.isChecked=true
        }else{
            holder.chValue.isChecked=false
        }

        for(i in 1 until result.size){
            list.add(-1)
        }


        holder.chValue.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(holder.chValue.isChecked)
                {
                    list[position]=position
                }
                else{
                    list[position]=-1
                }


            }
        })

    }

    fun getpostion(): ArrayList<Int> {

        return list
    }

     fun selectall(selectall: Boolean) {
        this.selectall =selectall
         notifyItemRangeChanged(0,result.size)
    }


    override fun getItemCount(): Int {
        if(result.size>=4)
            return 4
        else {
            return result.size
        }
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var tvName: TextView = itemView.tvName
        var ivProfile: CircleImageView = itemView.ivProfile
        var chValue: CheckBox = itemView.chValue
    }


}