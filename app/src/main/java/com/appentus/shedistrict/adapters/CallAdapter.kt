package com.appentus.shedistrict.adapters


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.CurrentFriendListBean
import com.appentus.shedistrict.network.AppConstant
import kotlinx.android.synthetic.main.item_callfriend.view.*
import kotlinx.android.synthetic.main.item_friendlist.view.ivProfile
import kotlinx.android.synthetic.main.item_friendlist.view.tvName


class CallAdapter(val context: Activity, val result: MutableList<CurrentFriendListBean.ResultBean>) : RecyclerView.Adapter<CallAdapter.ItemHolder>() {

   var mIntent:Intent? =null
    val MY_PERMISSIONS_REQUEST_CALL_PHONE:Int=1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_callfriend, parent, false)
        return ItemHolder(itemView)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.tvName.text=result[position].user_name
        Utility().setImage(context, AppConstant.ImageUrl+result[position].user_profile, holder.ivProfile)
      //  Utility().setImage(context,result[position].user_profile,holder.ivProfile)


        holder.ivCall.setOnClickListener {
            // val number = "tel:" + numTxt.getText()
            mIntent =  Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", result[position].user_mobile, null));
           // mIntent!!.setData(Uri.parse(result[position].user_mobile))

// Here, thisActivity is the current activity
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CALL_PHONE),
                        MY_PERMISSIONS_REQUEST_CALL_PHONE)
                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
// app-defined int constant. The callback method gets the
// result of the request.
            } else { //You already have permission
                try {
                    context.startActivity(mIntent)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }

        }


    }

    override fun getItemCount(): Int {
        if(result.size>=4)
        return 4
       else {
            return result.size
        }
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.tvName
        var ivProfile: CircleImageView = itemView.ivProfile
        var ivCall: TextView = itemView.ivCall
    }


}