package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.animation.*
import androidx.fragment.app.Fragment
import com.appentus.nutrition.ui.fragments.*
import com.appentus.shedistrict.R

import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant.Companion.update_online_offline
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class HomeActivity : BaseActivity(), ResponseListener {
    private var currentFrag: Fragment? = null
    val activity: Activity = this
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        activeTab(0)
        apiController = ApiController(this,this)

        setstatus()
        ivTab1.setImageDrawable(getDrawable(R.drawable.bottomhome))

        val animation = AlphaAnimation(1.0f, 0.0f)
        animation.duration = 500
        animation.interpolator = LinearInterpolator() as Interpolator?
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE

        replaceFragment(HomeFragment(), "home")
        currentFrag = supportFragmentManager.findFragmentById(R.id.frame)

        ivTab1.setOnClickListener {
            currentFrag = supportFragmentManager.findFragmentById(R.id.frame)
            if (!(currentFrag is HomeFragment)) {
                replaceFragment(HomeFragment(), "home")
            }


        }
        ivTab2.setOnClickListener {
            currentFrag = supportFragmentManager.findFragmentById(R.id.frame)
            if (!(currentFrag is SearchFragment)) {
                replaceFragment(SearchFragment(), "search")
            }
        }

        ivTab3.setOnClickListener {
            currentFrag = supportFragmentManager.findFragmentById(R.id.frame)
            if (!(currentFrag is EventFragment)) {
                replaceFragment(EventFragment(), "report")
            }
        }
        ivTab4.setOnClickListener {
            currentFrag = supportFragmentManager.findFragmentById(R.id.frame)
            if (!(currentFrag is ConversionFragment)) {
                replaceFragment(ConversionFragment(), "message")
            }
        }
        ivTab5.setOnClickListener {
            currentFrag = supportFragmentManager.findFragmentById(R.id.frame)
            if (!(currentFrag is ProfileFragment)) {
                replaceFragment(ProfileFragment(), "more")
            }
        }
    }

    private fun setstatus() {
        val map: HashMap<String, String> = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["status"] = "1"
        apiController.makeCallSilent(update_online_offline, map)
    }

    public fun activeTab(pos: Int) {


        when (pos) {
            0 -> {

                ivTab1.setImageDrawable(getDrawable(R.drawable.bottomhome))
                ivTab2.setImageDrawable(getDrawable(R.drawable.ic_tab_two))
                ivTab3.setImageDrawable(getDrawable(R.drawable.ic_tab_three))
                ivTab4.setImageDrawable(getDrawable(R.drawable.ic_tab_four))
                ivTab5.setImageDrawable(getDrawable(R.drawable.ic_tab_five))


                ivActive2.visibility = View.GONE
                ivActive3.visibility = View.GONE
                ivActive4.visibility = View.GONE
                ivActive5.visibility = View.GONE
                ivActive1.visibility = View.VISIBLE
                ivActive1.setColorFilter(this.resources.getColor(R.color.yellow))
                ivTab1.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.blink))
            }
            1 -> {
                ivTab2.setImageDrawable(getDrawable(R.drawable.bottomsearch))
                ivTab1.setImageDrawable(getDrawable(R.drawable.ic_tab_one))
                ivTab3.setImageDrawable(getDrawable(R.drawable.ic_tab_three))
                ivTab4.setImageDrawable(getDrawable(R.drawable.ic_tab_four))
                ivTab5.setImageDrawable(getDrawable(R.drawable.ic_tab_five))

                ivActive1.visibility = View.GONE
                ivActive3.visibility = View.GONE
                ivActive4.visibility = View.GONE
                ivActive5.visibility = View.GONE
                ivActive2.visibility = View.VISIBLE
                ivActive2.setColorFilter(this.resources.getColor(R.color.skyblue))
                ivTab2.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.blink))
            }
            2 -> {
                ivTab2.setImageDrawable(getDrawable(R.drawable.ic_tab_two))
                ivTab1.setImageDrawable(getDrawable(R.drawable.ic_tab_one))
                ivTab3.setImageDrawable(getDrawable(R.drawable.bottomcalendar))
                ivTab4.setImageDrawable(getDrawable(R.drawable.ic_tab_four))
                ivTab5.setImageDrawable(getDrawable(R.drawable.ic_tab_five))
                ivActive1.visibility = View.GONE
                ivActive2.visibility = View.GONE
                ivActive4.visibility = View.GONE
                ivActive5.visibility = View.GONE
                ivActive3.visibility = View.VISIBLE
                ivActive3.setColorFilter(this.resources.getColor(R.color.lightblue))
                ivTab3.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.blink))
            }
            3 -> {
                ivTab2.setImageDrawable(getDrawable(R.drawable.ic_tab_two))
                ivTab1.setImageDrawable(getDrawable(R.drawable.ic_tab_one))
                ivTab3.setImageDrawable(getDrawable(R.drawable.ic_tab_three))
                ivTab4.setImageDrawable(getDrawable(R.drawable.bottommessage))
                ivTab5.setImageDrawable(getDrawable(R.drawable.ic_tab_five))
                ivActive1.visibility = View.GONE
                ivActive2.visibility = View.GONE
                ivActive3.visibility = View.GONE
                ivActive5.visibility = View.GONE
                ivActive4.visibility = View.VISIBLE
                ivActive4.setColorFilter(this.resources.getColor(R.color.txtDarkpink))
                ivTab4.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.blink))
            }
            4 -> {
                ivTab1.setImageDrawable(getDrawable(R.drawable.ic_tab_one))
                ivTab2.setImageDrawable(getDrawable(R.drawable.ic_tab_two))
                ivTab3.setImageDrawable(getDrawable(R.drawable.ic_tab_three))
                ivTab4.setImageDrawable(getDrawable(R.drawable.ic_tab_four))
                ivTab5.setImageDrawable(getDrawable(R.drawable.bottommore))


                ivActive1.visibility = View.GONE
                ivActive2.visibility = View.GONE
                ivActive3.visibility = View.GONE
                ivActive4.visibility = View.GONE
                ivActive5.visibility = View.VISIBLE
                ivActive5.setColorFilter(this.resources.getColor(R.color.txtPurpal))

                ivTab5.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.blink))
            }
        }


    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment, tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()

        /*  when (tag) {
              "home" ->  replaceFragment(ProfileFragment(), "home")
              "search" ->  replaceFragment(ProfileFragment(), "search")
              "report" ->  replaceFragment(ProfileFragment(), "report")
              "message" ->  replaceFragment(ProfileFragment(), "message")
              "more" ->  replaceFragment(ProfileFragment(), "more")
          }*/
    }


    override fun onBackPressed() {


        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()

        }

    }

    override fun onSuccess(tag: String, response: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(this,msg)
    }

    override fun onResume() {
        super.onResume()


    }


}
