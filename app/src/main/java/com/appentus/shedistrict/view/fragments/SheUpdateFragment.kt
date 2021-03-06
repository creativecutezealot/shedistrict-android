package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.appentus.shedistrict.R
import kotlinx.android.synthetic.main.toolbar_title.*

/**
 * A simple [Fragment] subclass.
 */
class SheUpdateFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_she_update, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTop.text = "SheUpdates"

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }
    }

}
