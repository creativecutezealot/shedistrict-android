package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.appentus.shedistrict.R
import com.appentus.shedistrict.adapters.AttendingEventAdapter
import com.appentus.shedistrict.adapters.ForumDetailAdapter
import kotlinx.android.synthetic.main.fragment_forum_detail.*

/**
 * A simple [Fragment] subclass.
 */
class ForumDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvItem.layoutManager= LinearLayoutManager(activity)
        rvItem.adapter= ForumDetailAdapter(activity!!)
    }

}
