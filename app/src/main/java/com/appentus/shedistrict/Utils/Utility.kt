package com.appentus.shedistrict.Utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.SheDistrict.activity
import com.appentus.shedistrict.models.UserProfileBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern

public class Utility{

    var DeviceToken = "android"
    var DeviceType = "1"


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.statusBarColor = Color.parseColor("#ffffff")
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.BLACK
        }
    }


    fun getRadius(value: Float): FloatArray? {
        return floatArrayOf(value, value, value, value, value, value, value, value)
    }

    fun showSnackBar(activity: Activity, msg: String?) {
        Snackbar.make(activity.findViewById(android.R.id.content), msg!!, Snackbar.LENGTH_SHORT).show()
    }

    fun setHtmlText(textView: TextView, msg: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(msg, Html.FROM_HTML_MODE_COMPACT)
        } else {
            textView.text = Html.fromHtml(msg)
        }
    }

    fun isValidEmail(email: String?): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun emailValidator(email: String): Boolean {
        return if (email.isEmpty()) {
            false
        } else {
            val pattern: Pattern
            val matcher: Matcher
            val EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            pattern = Pattern.compile(EMAIL_PATTERN)
            matcher = pattern.matcher(email)
            matcher.matches()
        }
    }


    fun passValidator(pass: String): Boolean {
        return if (pass.isEmpty()) {
            false
        } else pass.length >= 6
    }


    fun fullScreenDialog(layoutId: Int, activity: Activity): Dialog {
        val dialog = Dialog(activity)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layoutId)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = (size.x * 0.94).toInt()
        dialog.show()
        dialog.window!!.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)

        return dialog
    }

    fun fullScreenDialogWithLargeGap(layoutId: Int, activity: Activity): Dialog {
        val dialog = Dialog(activity)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layoutId)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = (size.x * 0.94).toInt()
        val height= (size.y * 0.80).toInt()
        dialog.show()
        dialog.window!!.setLayout(width, height)

        return dialog
    }


    fun fullScreenDialogOnTop(layoutId: Int, activity: Activity): Dialog {
        val dialog = Dialog(activity)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layoutId)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setGravity(Gravity.TOP)
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = (size.x * 0.94).toInt()
        val height= (size.y * 0.80).toInt()
        dialog.show()
        dialog.window!!.setLayout(width, height)

        return dialog
    }





    fun getUser(): UserProfileBean.ResultBean? {
        val mUser = Gson().fromJson<UserProfileBean>(PrefManager.getString(AppConstant.userInfo), UserProfileBean::class.java)
        return mUser.result
    }

    fun getUserId():String?{
        return PrefManager.getString("UserId")
    }

    fun imageLoad(activity: Context) : CircularProgressDrawable{
        val circularProgressDrawable = CircularProgressDrawable(activity)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        return  circularProgressDrawable
    }


    fun getDateFormated(strInputFormate: String, strOutputFormate: String, dateValue: String): String {
        val inputFormate = SimpleDateFormat(strInputFormate)
        val outputFormat = SimpleDateFormat(strOutputFormate)
        val cdate = inputFormate.parse(dateValue)
        val cvalue = outputFormat.format(cdate!!)

        return cvalue
    }

    fun getDateAgo(strInputFormate: String,dateAgo: String): Int {
        val sdf = SimpleDateFormat(strInputFormate)
        try {
            val date = sdf.parse(dateAgo)
            val now = Date(System.currentTimeMillis())
            val days = getDateDiff(date, now, TimeUnit.DAYS)
            return when {
                days < 1 -> 0
                days < 2 -> 1
                else -> 3
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return 0
    }

    fun getDateDiff(date1: Date, date2: Date, timeUnit: TimeUnit): Long {
        val diffInMillies = date2.time - date1.time;
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    fun setImage(context: Context, s: String, ivFriend: ImageView) {

        Glide.with(context).load(s).apply(RequestOptions.errorOf(R.drawable.ic_user)).into(ivFriend)

    }

    fun setText( s: String, textView:TextView) {

       textView.setText(s)

    }






}