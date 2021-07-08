package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.appentus.shedistrict.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_forum.*
import kotlinx.android.synthetic.main.toolbar_title.*

/**
 * A simple [Fragment] subclass.
 */
class ForumFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        titleTop.text = "Forum"
        tablayout.addTab(tablayout.newTab().setText("Forum"))
        tablayout.addTab(tablayout.newTab().setText("Latest Post"))
        tablayout.addTab(tablayout.newTab().setText("New Topics"))
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL)

        replaceFragment(ForumDetailFragment(),"forumDetail")
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {


            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                replaceFragment(ForumDetailFragment(),"forumDetail")
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
