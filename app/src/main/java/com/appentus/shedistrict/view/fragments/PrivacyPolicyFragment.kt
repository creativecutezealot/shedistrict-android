package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.PolicyBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_privacy_policy.*
import kotlinx.android.synthetic.main.toolbar_title.*


/**
 * A simple [Fragment] subclass.
 */
class PrivacyPolicyFragment : Fragment(),ResponseListener{

    lateinit var apiController :ApiController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        apiController = ApiController(activity!!,this)
        titleTop.text = Html.fromHtml("<font color=\"#919191\">"+"Privacy Policy"+"</font>")
        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }
        apiController.loader.show()
        apiController.makeCallGET(AppConstant.get_privacy_terms)
    }


    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if(tag == AppConstant.get_privacy_terms) {
            try {
                val model = apiController.parseJson(response, PolicyBean::class.java)
                Utility().setHtmlText(tvShowAll,model.result.privacy_policy)
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

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
        Utility().showSnackBar(activity!!, msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiController.loader.dismiss()
        apiController.unSubscribe()
    }


}
