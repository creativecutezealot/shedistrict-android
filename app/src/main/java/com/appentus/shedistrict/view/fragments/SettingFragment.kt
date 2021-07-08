package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.appentus.shedistrict.R
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.toolbar_title.*

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleTop.text = "Settings"

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }
        ccGeneral.setOnClickListener {
                (activity as HomeActivity).replaceFragment(GeneralFragment(), "more")

        }

        ccPrivacy.setOnClickListener {

            (activity as HomeActivity).replaceFragment(PrivacyFragment(), "more")
        }

        ccNotification.setOnClickListener {
            (activity as HomeActivity).replaceFragment(NotificationFragment(), "more")
        }

        ccPurchase.setOnClickListener {
            (activity as HomeActivity).replaceFragment(PurchasesFragment(), "more")
        }
    }
}
