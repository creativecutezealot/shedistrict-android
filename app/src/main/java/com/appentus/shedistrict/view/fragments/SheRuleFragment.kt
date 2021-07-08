package com.appentus.shedistrict.view.fragments



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.SheRuleAdapter
import com.appentus.shedistrict.models.SheRuleBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_she_rule.*
import kotlinx.android.synthetic.main.toolbar_title.*


/**
 * A simple [Fragment] subclass.
 */
class SheRuleFragment : Fragment(), ResponseListener {

    lateinit var apiController : ApiController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_she_rule, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTop.text = "SheRules"

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

        apiController = ApiController(activity!!,this)
        apiController.loader.show()
        apiController.makeCallGET(AppConstant.she_rules)

    }


    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if(tag == AppConstant.she_rules) {
            try {
                val model = apiController.parseJson(response, SheRuleBean::class.java)
                rvDes.layoutManager = LinearLayoutManager(activity!!)
                rvDes.adapter = SheRuleAdapter(activity!!, model.result,1)
                rvDes.isNestedScrollingEnabled = false
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
