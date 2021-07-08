package com.appentus.shedistrict.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.appentus.nutrition.ui.fragments.SearchFragment

import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.adapters.ViewAllpicAdapter
import com.appentus.shedistrict.models.UserByPreferenceBean
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.activity.PreferencesActivity
import kotlinx.android.synthetic.main.fragment_all_pic_prefrence.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_view_all.*
import kotlinx.android.synthetic.main.fragment_view_all.ivFilter


/**
 * A simple [Fragment] subclass.
 */
class ViewAllFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        ivFilter.setColorFilter(this.resources.getColor(R.color.skyblue))

        ivFilter.setOnClickListener {
            val intent = Intent(context, PreferencesActivity::class.java)
            startActivityForResult(intent, 2)
        }

        val allPicList:ArrayList<UserByPreferenceBean.Data>
        allPicList= arguments?.getSerializable("piclist") as ArrayList<UserByPreferenceBean.Data>

        rvviewalls.layoutManager = GridLayoutManager(activity,4)
        rvviewalls.adapter = ViewAllpicAdapter(activity!!,allPicList)
        rvviewalls.addOnItemTouchListener(RecyclerTouchListener(context,rvviewalls, RecyclerTouchListener.ClickListener { _, _ ->

            val fragment = SearchProfile()
            val bundle = Bundle()
           // bundle.putString("userid",allPicList[position].user_id)
            fragment.arguments = bundle
            (context as HomeActivity).replaceFragment(fragment, "search")



        }))


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2 && resultCode== Activity.RESULT_OK) {

            Log.e("affdssf","Sfsfsafass")

            val fragment = SearchFragment()
            val bundle = Bundle()
            bundle.putString("Allpic","Allpic")
            bundle.putSerializable("piclist", data!!.getStringExtra("preferenceobj"))
            fragment.arguments = bundle
            (context as HomeActivity).replaceFragment(fragment, "dknlk")


        }
    }

}
