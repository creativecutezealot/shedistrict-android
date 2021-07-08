package com.appentus.shedistrict.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.AppContentBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.toolbar_title.*

class AboutFragment : Fragment(),ResponseListener{
    lateinit var apiController: ApiController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        apiController = ApiController(activity!!,this)
        titleTop.text = "About SheDistrict"
        ivBack.setOnClickListener {

            activity!!.onBackPressed()
        }
        apiController.loader.show()
        apiController.makeCallGET(AppConstant.app_content)
    }

    override fun onSuccess(tag: String, response: String) {

        apiController.loader.dismiss()
        if(tag == AppConstant.app_content){
            val model = apiController.parseJson(response,AppContentBean::class.java)
            tvtitle1.text = model.result[0].app_about
        }
    }

    override fun onError(tag: String, msg: String) {

        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onFailure(tag: String, msg: String) {

        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onNoConnection(tag: String, msg: String) {

        apiController.loader.dismiss()
        Utility().showSnackBar(activity!!,msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.loader.dismiss()
        apiController.unSubscribe()
    }


}
