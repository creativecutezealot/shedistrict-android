package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.appentus.shedistrict.R
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_general.*
import kotlinx.android.synthetic.main.toolbar_title.*


class GeneralFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleTop.text = "General"

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        ccAbout.setOnClickListener {
            (activity as HomeActivity).replaceFragment(AboutFragment(), "more")
        }
        ccSheUpdate.setOnClickListener {
            (activity as HomeActivity).replaceFragment(SheUpdateFragment(), "more")
        }
        ccMessage.setOnClickListener {

            (activity as HomeActivity).replaceFragment(MessageForCeo(), "more")
        }

        ccSheProtest.setOnClickListener {
            (activity as HomeActivity).replaceFragment(SheProtests(), "more")
        }

        ccSheRule.setOnClickListener {

            (activity as HomeActivity).replaceFragment(SheRuleFragment(), "more")
        }
    }
}
