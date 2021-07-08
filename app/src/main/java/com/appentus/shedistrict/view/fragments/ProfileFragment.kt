package com.appentus.nutrition.ui.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.CircleImageView
import com.appentus.shedistrict.Utils.PrefManager
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.RecyclerViewClick
import com.appentus.shedistrict.adapters.UserPhotoAdapter
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.AuthActivity
import com.appentus.shedistrict.view.activity.EditProfileActivity
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.activity.SplashActivity
import com.appentus.shedistrict.view.fragments.*
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.fragment_more.*


class ProfileFragment : Fragment(),ResponseListener{

    lateinit var apiController:ApiController

    private var currentAnimator: Animator? = null

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private var shortAnimationDuration: Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiController = ApiController(activity!!,this)
        tvName.text = Utility().getUser()?.user_name
        getProfile()

        if(!Utility().getUser()?.user_profile.isNullOrEmpty()) {
            Glide.with(this).load(AppConstant.ImageUrl + Utility().getUser()?.user_profile)
                    .placeholder(Utility().imageLoad(activity!!)).into(ivProfile)
        }

        if(Utility().getUser()?.user_like.isNullOrEmpty() || Utility().getUser()?.user_like == "0"){
            tvLike.visibility = View.GONE
        }
        else{
            tvLike.text = Utility().getUser()?.user_like
        }

        ivProfile.setOnClickListener {

            if(Utility().getUser()?.role.equals("0")){
                val fragment = UserProfile()
                val bundle = Bundle()
                bundle.putString("userid",Utility().getUser()?.user_id)
                bundle.putString("profile","profile")
                fragment.arguments = bundle
                (context as HomeActivity).replaceFragment(fragment, "search")
            }else{
                val fragment = CeoProfile()
                val bundle = Bundle()
                bundle.putString("userid",Utility().getUser()?.user_id)
                fragment.arguments = bundle
                (context as HomeActivity).replaceFragment(fragment, "ceoprofile")

            }


        }

        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)


        tvedit.setOnClickListener {
            startActivity(Intent(activity!!,EditProfileActivity::class.java))
        }
        ccForum.setOnClickListener {
            (activity as HomeActivity).replaceFragment(ForumFragment(), "more")
        }


        ccSetting.setOnClickListener {
            (activity as HomeActivity).replaceFragment(SettingFragment(), "more")
        }

     /*   ccScan.setOnClickListener {
            showScan()
        }*/

        ccHelp.setOnClickListener {

            (activity as HomeActivity).replaceFragment(HelpFragment(), "more")
        }

        ccLogout.setOnClickListener {
            logout()
        }

        getActivity()!!.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        val view: View? = activity!!.getCurrentFocus()
        if (view != null) {
            val inputManager = activity!!
                    .getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }

    }


    fun getProfile(){
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()?.user_id.toString()
        map["friend_id"] = Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.get_user_profile, map)
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).activeTab(4)
        /*getProfile()*/
        val view: View? = activity!!.getCurrentFocus()
        if (view != null) {
            val inputManager = activity!!
                    .getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun showScan() {
        val dialog = Utility().fullScreenDialog(R.layout.dialog_scan, activity!!)
        val ccDialog = dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        ivClose.setColorFilter(activity!!.resources.getColor(R.color.grey))
        ccDialog.background = RoundView(resources.getColor(R.color.white), Utility().getRadius(40f))

        ivClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun logout() {
        val dialog = Utility().fullScreenDialog(R.layout.warringdialog, activity!!)
        val ccDialog=dialog.findViewById<ConstraintLayout>(R.id.ccDialog)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        val tvMsg1 = dialog.findViewById<TextView>(R.id.tvWarring)
        val tvNo=dialog.findViewById<TextView>(R.id.tvNo)
        val tvYes=dialog.findViewById<TextView>(R.id.tvYes)

        tvMsg.text = "You're trying to log out..."
        tvMsg1.text = "Are you sure?"


        ccDialog.background=RoundView(resources.getColor(R.color.white),Utility().getRadius(20f))
        tvYes.setOnClickListener {
            setstatus()
            val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("prefrence", AppCompatActivity.MODE_PRIVATE)
            sharedPreferences.edit().clear().commit()
            dialog.dismiss()
            logoutTask()

        }

        tvNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun logoutTask() {
        if (PrefManager.getString("social")=="1"){
            //facebook logout
            LoginManager.getInstance().logOut()
        }

        PrefManager.clear()
        val nextIntent = Intent(activity, SplashActivity::class.java)
        startActivity(nextIntent)
        activity?.finishAffinity()
    }


    override fun onSuccess(tag: String, response: String) {
        if(tag == AppConstant.get_user_profile){
            PrefManager.putString(AppConstant.userInfo,response)
            val model = apiController.parseJson(response, UserProfileBean::class.java)
            tvMsg.text = model.result.user_details[0].user_bio

            Glide.with(this).load(AppConstant.ImageUrl+model.result.user_profile).
                   placeholder(R.drawable.ic_user).into(ivProfile)

            rvFriends.layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            rvFriends.adapter= model.result.user_photos.let {
                UserPhotoAdapter(activity!!, it, RecyclerViewClick {

                })
            }

        }

    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!,msg)
    }

    private fun zoomImageFromThumb(thumbView: View, imageResId: Int) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()

        // Load the high-resolution "zoomed-in" image.
        val expandedImageView: CircleImageView = activity!!.findViewById(R.id.ivProfile)
        expandedImageView.setImageResource(imageResId)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        activity!!.findViewById<View>(R.id.container)
                .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(
                    expandedImageView,
                    View.X,
                    startBounds.left,
                    finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
               start()
             }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        expandedImageView.setOnClickListener {
            currentAnimator?.cancel()

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }

     private fun setstatus() {
        val map: java.util.HashMap<String, String> = java.util.HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["status"] = "0"
        apiController.makeCallSilent(AppConstant.update_online_offline, map)

    }



}