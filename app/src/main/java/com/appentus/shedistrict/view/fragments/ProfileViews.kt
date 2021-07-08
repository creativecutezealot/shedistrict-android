package com.appentus.shedistrict.view.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appentus.nutrition.ui.fragments.AnnouncementFragment
import com.appentus.nutrition.ui.fragments.YourAnnouncementFragment

import com.appentus.shedistrict.R
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.tablayout
import kotlinx.android.synthetic.main.fragment_profile_views.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileViews : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_views, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        tablayout.addTab(tablayout.newTab().setText("Profile Views"))
        tablayout.addTab(tablayout.newTab().setText("Boosts"))

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL)
        replaceFragment(ProfilePicView(), "ProfileViews")

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (tablayout.selectedTabPosition == 0) {
                    ivProfiledot.visibility = View.VISIBLE
                    ivBoostsdot.visibility = View.INVISIBLE

                    replaceFragment(ProfilePicView(), "ProfileViews")
                } else {
                    ivBoostsdot.visibility = View.VISIBLE
                    ivProfiledot.visibility = View.INVISIBLE
                    replaceFragment(BoostsPic(), "Boosts")
                }
            }
        })
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
        fragmentTransaction.replace(R.id.framchild, fragment, tag)
        fragmentTransaction.commit()
    }
}
