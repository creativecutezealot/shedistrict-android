package com.appentus.shedistrict.adapters

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.models.BaseApiResponse
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.ChatActivity
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.fragments.CeoProfile
import com.appentus.shedistrict.view.fragments.SearchProfile
import com.appentus.shedistrict.view.fragments.UserProfile
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_home.view.*


class AnnouncementAdapter(val context: Activity, val result: List<AnnouncmentBean.ResultBean>, val layout: Int) : RecyclerView.Adapter<AnnouncementAdapter.ItemHolder>(),ResponseListener{
    lateinit var apiController: ApiController
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        apiController = ApiController(context, this)

        holder.tvTitle.text = result[position].announcement_title
        holder.tvDis.text = result[position].announcement_desc.replace("\\s+".toRegex()," ").trim();

        Log.e("fgffg",result.size.toString())

        if(Utility().getUser()!!.user_id.equals(result[position].announcement_user_id)){

            holder.ivMsg.visibility=View.GONE
            holder.ivMore.visibility=View.GONE

        }
        if(!result[position].category.isNullOrEmpty()) {

            holder.view.setBackgroundColor(Color.parseColor("#" + result[position].category[0].color))
            holder.viewbelow.setBackgroundColor(Color.parseColor("#" + result[position].category[0].color))

        }

        if(!result[position].announcement_image.isNullOrEmpty()){
            if(result[position].image_type.equals("1")){
                Glide.with(context).load(AppConstant.ImageUrl+result[position].announcement_image)
                  .into(holder.ivImages)
              }
            else{
                Glide.with(context).load(result[position].announcement_image)
                        .into(holder.ivImages)
            }
        }
        when {
            Utility().getDateAgo(context.getString(R.string.yyyMMddHHMMSS), result[position].created.toString()) == 0 -> {
                holder.tvTime.text = "Today "+","+Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS),context.getString(R.string.HHMMA),result[position].created.toString())
            }
            Utility().getDateAgo(context.getString(R.string.yyyMMddHHMMSS), result[position].created.toString()) == 1 -> {
                holder.tvTime.text = Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS),context.getString(R.string.HHMMA),result[position].created.toString())
            }
            Utility().getDateAgo(context.getString(R.string.yyyMMddHHMMSS), result[position].created.toString()) == 3 -> {
                holder.tvTime.text = Utility().getDateFormated(context.getString(R.string.yyyMMddHHMMSS),context.getString(R.string.HHMMA),result[position].created.toString())

            }
        }
        if(!result[position].user.isNullOrEmpty()){
            holder.tvName.text = result[position].user[0].user_name
            if(result[position].user[0].user_social.isNullOrEmpty()){
                Glide.with(context).load(AppConstant.ImageUrl+result[position].user[0].user_profile)
                        .placeholder(R.drawable.ic_user)
                        .into(holder.ivProfile)
            }
            else if(result[position].user[0].user_profile.startsWith("./assets")){

                Glide.with(context).load(AppConstant.ImageUrl+result[position].user[0].user_profile)
                        .placeholder(R.drawable.ic_user)
                        .into(holder.ivProfile)

            }else{
                Glide.with(context).load(result[position].user[0].user_profile)
                        .placeholder(R.drawable.ic_user)
                        .into(holder.ivProfile)
            }
        }

        holder.ivMore.setOnClickListener(){
            var reason:String
            val dialog = Utility().fullScreenDialogWithLargeGap(R.layout.dialog_report_post,context)
            val ccDialog = dialog.findViewById<RelativeLayout>(R.id.ccDialog)
            val tvOffensive = dialog.findViewById<TextView>(R.id.tvOffensive)
            val tvInappropriate = dialog.findViewById<TextView>(R.id.tvInappropriate)
            val tvviolates = dialog.findViewById<TextView>(R.id.tvviolates)
            val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)

            ccDialog.background = RoundView(context.resources.getColor(R.color.white), Utility().getRadius(40f))

            tvOffensive.setOnClickListener(){
                dialog.dismiss()
               reason=tvOffensive.text.toString()
                SendReportreason(reason,result[position].announcement_user_id)
            }

            tvInappropriate.setOnClickListener(){
                dialog.dismiss()
                reason=tvInappropriate.text.toString()
                SendReportreason(reason, result[position].announcement_user_id)
            }

            tvviolates.setOnClickListener(){
                dialog.dismiss()
                reason=tvviolates.text.toString()
                SendReportreason(reason, result[position].announcement_user_id)
            }

            ivClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

        }
        Log.e("fgffg",result.size.toString())

        holder.ivMsg.setOnClickListener(){
            val nextIntent = Intent(context, ChatActivity::class.java)
            nextIntent.putExtra("text",result[position].user[0].user_name)
            nextIntent.putExtra("id",result[position].announcement_user_id)
            context.startActivity(nextIntent)
        }

        holder.setIsRecyclable(false)

        holder.ivProfile.setOnClickListener {

             if(result[position].user[0].role.equals("0")){
                 if(result[position].announcement_user_id.equals(Utility().getUser()!!.user_id)){
                     val fragment = UserProfile()
                     val bundle = Bundle()
                     bundle.putString("userid", result[position].announcement_user_id)
                     fragment.arguments = bundle
                     (context as HomeActivity).replaceFragment(fragment, "search")

                 }else {
                     val fragment = SearchProfile()
                     val bundle = Bundle()
                     bundle.putString("userid", result[position].announcement_user_id)
                     fragment.arguments = bundle
                     (context as HomeActivity).replaceFragment(fragment, "search")
                 }
                }else{
                    val fragment = CeoProfile()
                    val bundle = Bundle()
                    bundle.putString("userid",result[position].announcement_user_id)
                    fragment.arguments = bundle
                    (context as HomeActivity).replaceFragment(fragment, "ceoprofile")

                }
        }

    }

    private fun SendReportreason(reason: String, announcementUserId: String) {
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["friend_id"] =announcementUserId
        map["reason"] = reason
        map["type"] ="post"
        apiController.makeCall(AppConstant.user_report, map)

    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDis : TextView = itemView.tvDis
        val ivImages : ImageView = itemView.ivImages
        val tvName : TextView = itemView.tvName
        val tvTitle : TextView = itemView.tvTitle
        val tvTime : TextView = itemView.tvTime
        val ivProfile : CircleImageView = itemView.ivProfile
        val ivMore : ImageView = itemView.ivMore
        val ivMsg : ImageView = itemView.ivMsg
        val view : View = itemView.view
        val viewbelow : View = itemView.viewbelow
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onSuccess(tag: String, response: String) {
        if (tag == AppConstant.user_report) {
            val model = apiController.parseJson(response, BaseApiResponse::class.java)
             Utility().showSnackBar(context,"Report Send")
        }
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(context, msg)
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(context, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(context, msg)
    }


}