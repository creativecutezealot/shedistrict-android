package com.appentus.nutrition.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.HomeFilterAdapter
import com.appentus.shedistrict.adapters.RecyclerViewClick
import com.appentus.shedistrict.models.ChooseCategoryBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import com.appentus.shedistrict.view.activity.NewPostActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*


class

HomeFragment : Fragment(), ResponseListener {

    lateinit var apiController: ApiController
    var list: MutableList<ChooseCategoryBean.ResultBean> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiController = ApiController(activity!!, this)

        apiController.makeCallGET(AppConstant.post_category)

        list = ArrayList()
        tablayout.addTab(tablayout.newTab().setText("Announcements"))
        tablayout.addTab(tablayout.newTab().setText("Your Announcements"))
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL)

        //replaceFragment(AnnouncementFragment(), "announcement")

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {


            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (tablayout.selectedTabPosition == 0) {
                    ivActiveAnn.visibility = View.VISIBLE
                    ivActiveYouAnn.visibility = View.INVISIBLE
                    apiController.makeCallGET(AppConstant.post_category)

                    //replaceFragment(AnnouncementFragment(), "announcement")
                } else {
                    ivActiveAnn.visibility = View.INVISIBLE
                    ivActiveYouAnn.visibility = View.VISIBLE
                    replaceFragment(YourAnnouncementFragment(), "yourannouncement")
                }
            }
        })




        ivAdd.setOnClickListener {

            (activity as HomeActivity).replaceFragment(NewPostActivity(), "Newpost")
        }
    }

    private fun filterAnnouncement(type: String, categoryName: String) {
        val fragment = AnnouncementFragment()
        val bundle = Bundle()
        bundle.putString("type", type)
        bundle.putString("categoryName", categoryName)
        bundle.putString("filter", "filter")
        fragment.arguments = bundle
        replaceFragment(fragment, "announcement")

    }


    override fun onResume() {
        super.onResume()

        (activity as HomeActivity).activeTab(0)
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(tag)
//            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
        fragmentTransaction.replace(R.id.cc, fragment, tag)
        fragmentTransaction.commit()
    }

    override fun onSuccess(tag: String, response: String) {

            val model = apiController.parseJson(response, ChooseCategoryBean::class.java)
            list = model.result
            Log.e("sjvhs",list.toString())
            filterAnnouncement(list[0].category_id, list[0].category_name)

        rvTop.layoutManager = GridLayoutManager(context, 3)
        rvTop.adapter = HomeFilterAdapter(activity!!, list, object : RecyclerViewClick {
            override fun onClickPos(pos: Int) {

                filterAnnouncement(list[pos].category_id,list[pos].category_name)
            }
        })

       /*
        tvShowAll.setOnClickListener {

            if (rvTop.visibility == View.GONE) {
                ivArr.rotation = 270f
                rvTop.visibility = View.VISIBLE
                rvTop.adapter!!.notifyDataSetChanged()
            } else {
                rvTop.visibility = View.GONE
                ivArr.rotation = 90f
            }
        }*/

    }

    override fun onFailure(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!, msg)
    }

    override fun onError(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!, msg)
    }


    override fun onNoConnection(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!,msg)
    }

    }

