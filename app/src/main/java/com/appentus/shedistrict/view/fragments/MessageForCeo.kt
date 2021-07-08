

package com.appentus.shedistrict.view.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.models.AppContentBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.appentus.shedistrict.view.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_message_for_ceo.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.json.JSONObject


class MessageForCeo : Fragment(),ResponseListener{

    lateinit var apiController: ApiController
    var json=JSONObject()
    var instagrams=""
    var twitters=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_for_ceo, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        apiController = ApiController(activity!!,this)
        titleTop.text = "CEO"
        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }
        apiController.loader.show()
        apiController.makeCallGET(AppConstant.app_content)


        ivlogo.setOnClickListener {
            val fragment = CeoProfile()
            val bundle = Bundle()
            fragment.arguments = bundle
            (context as HomeActivity).replaceFragment(fragment, "ceoprofile")
        }


        twitter.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(twitters)
            startActivity(i)
        }
        insta.setOnClickListener(){
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(instagrams)
            startActivity(i)
        }


    }
    override fun onSuccess(tag: String, response: String) {

        apiController.loader.dismiss()
        if(tag == AppConstant.app_content){
            val model = apiController.parseJson(response, AppContentBean::class.java)
            tvtitle2.text = model.result[0].ceo_msg

           json = JSONObject(model.result[0].ceo_social_link)
            val iter = json.keys()
            while (iter.hasNext()) {
                val key = iter.next()
                if(key=="instagram"){
                    instagrams=json.getString(key)
                    Log.e("ins",json.getString(key))
                }else{
                    twitters=json.getString(key)
                    Log.e("tw",json.getString(key))
                }

            }

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
