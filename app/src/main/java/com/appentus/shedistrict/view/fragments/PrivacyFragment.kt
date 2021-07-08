package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.appentus.shedistrict.R
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_privacy.*
import kotlinx.android.synthetic.main.toolbar_title.*

/**
 * A simple [Fragment] subclass.
 */
class PrivacyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_privacy, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleTop.text = "Privacy"

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        ccMessage.setOnClickListener {
            (activity as HomeActivity).replaceFragment(MessageFragment(), "more")
        }
        ccProfile.setOnClickListener {

            (activity as HomeActivity).replaceFragment(PrivacyProfileFragment(), "more")
        }

        ccBlocked.setOnClickListener {
            (activity as HomeActivity).replaceFragment(BlockedFragment(), "more")
        }

        ccPrivacy.setOnClickListener {
            (activity as HomeActivity).replaceFragment(PrivacyPolicyFragment(), "more")
        }
    }

}
