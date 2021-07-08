package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.appentus.nutrition.ui.fragments.YourAnnouncementFragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.AnnouncmentBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.fragments.Repostfragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.toolbar_title.*
import java.io.Serializable

class PostDetailActivity : Fragment(),ResponseListener{

    var result : AnnouncmentBean.ResultBean ? = null
    lateinit  var apiController: ApiController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_post_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!,this)


        ivBack.setColorFilter(this.resources.getColor(R.color.yellow))
        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        ivDelete.setColorFilter(this.resources.getColor(R.color.yellow))
        ivDelete.setOnClickListener {
             root.setAlpha(0.10f);
            showDialogDelete()
        }
        result = getArguments()?.getSerializable("list") as AnnouncmentBean.ResultBean
        getData()

        ccRepost.setOnClickListener {
            val fragment = Repostfragment()
            val bundle = Bundle()
            bundle.putSerializable("list",result as Serializable)
            fragment.arguments = bundle
            (activity as HomeActivity ).replaceFragment(fragment, "postdetail")

        }
         setstatus()

    }

    private fun setstatus() {
        val map: java.util.HashMap<String, String> = java.util.HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["status"] = "1"
        apiController.makeCallSilent(AppConstant.update_online_offline, map)

    }

    private  fun getData(){
        titleTop.text = result?.announcement_title
        if(!result?.user.isNullOrEmpty()){
            tvName.text = result?.user!![0].user_name
            if(result?.user!![0].user_social.isNullOrEmpty()){
                if(!result?.user!![0].user_profile.isNullOrEmpty()) {
                    Glide.with(this).load(AppConstant.ImageUrl + result?.user!![0].user_profile)
                            .into(ivProfile)
                }
            }else{
                if(!result?.user!![0].user_profile.isNullOrEmpty()) {
                    Glide.with(this).load(result?.user!![0].user_profile)
                            .into(ivProfile)
                }
            }

        }

        when {
            Utility().getDateAgo(getString(R.string.yyyMMddHHMMSS), result!!.created.toString()) == 0 -> {
                tvTime.text = "Today " + Utility().getDateFormated(getString(R.string.yyyMMddHHMMSS), getString(R.string.HHMMA), result?.created.toString())
            }
            Utility().getDateAgo(getString(R.string.yyyMMddHHMMSS), result?.created.toString()) == 1 -> {
                tvTime.text = Utility().getDateFormated(getString(R.string.yyyMMddHHMMSS), getString(R.string.HHMMA), result?.created.toString())
            }
            Utility().getDateAgo(getString(R.string.yyyMMddHHMMSS), result?.created.toString()) == 3 -> {
                tvTime.text = Utility().getDateFormated(getString(R.string.yyyMMddHHMMSS), getString(R.string.HHMMA), result?.created.toString())
            }

        }

        if(!result?.announcement_image.isNullOrEmpty()){
            if(result?.image_type.equals("1")) {
                Glide.with(this).load(AppConstant.ImageUrl+result?.announcement_image)
                        .into(ivImages)
            }else{

                Glide.with(this).load(result?.announcement_image)
                        .into(ivImages)
            }
        }
        tvDis.text = result?.announcement_desc
    }
    private fun showDialogDelete() {

        val dialog = Utility().fullScreenDialog(R.layout.warringdialog, activity!!)
        val ccDialog=dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvNo=dialog.findViewById<TextView>(R.id.tvNo)
        val tvYes=dialog.findViewById<TextView>(R.id.tvYes)
        ccDialog.background=RoundView(resources.getColor(R.color.white),Utility().getRadius(40f))
        tvYes.setOnClickListener {
            requestDelete()
            dialog.dismiss()

        }
        tvNo.setOnClickListener {
            dialog.dismiss()
            root.setAlpha(0.54f);
        }
        dialog.show()


    }

    private fun requestDelete(){
        val map = HashMap<String,String>()
        map["announcement_id"] = result?.announcement_id.toString()
        apiController.makeCall(AppConstant.announcement_delete,map)
    }

    private fun showDialogConfirm() {
        val dialog = Utility().fullScreenDialog(R.layout.warringdialog, activity!!)
        val ccDialog=dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg=dialog.findViewById<TextView>(R.id.tvMsg)
        val tvWarring=dialog.findViewById<TextView>(R.id.tvWarring)
        val tvNo=dialog.findViewById<TextView>(R.id.tvNo)
        val tvYes=dialog.findViewById<TextView>(R.id.tvYes)
        tvNo.visibility= View.GONE
        tvMsg.text="Your post has been"
        tvWarring.text="deleted!"
        tvYes.text = "Ok"
        ccDialog.background=RoundView(resources.getColor(R.color.white),Utility().getRadius(40f))
        tvYes.setTextColor(resources.getColor(R.color.yellow))
        tvYes.setOnClickListener {
            dialog.dismiss()
            activity!!.setResult(Activity.RESULT_OK)
            startActivity(Intent( activity!!,HomeActivity::class.java))
//            activity!!.finishAffinity()

        }
        dialog.show()
    }


    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.announcement_delete){
            showDialogConfirm()
        }
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.unSubscribe()
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(tag)
//            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
        fragmentTransaction.replace(R.id.framchild, fragment, tag)
        fragmentTransaction.commit()
    }
}
