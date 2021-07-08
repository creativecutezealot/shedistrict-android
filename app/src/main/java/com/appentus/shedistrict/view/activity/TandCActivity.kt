package com.appentus.shedistrict.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.SheRuleAdapter
import com.appentus.shedistrict.models.SheRuleBean
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_tand_c_.*
import kotlinx.android.synthetic.main.toolbar_logo.*


class TandCActivity : AppCompatActivity(), ResponseListener {

    val activity: Activity = this
    lateinit var apiController: ApiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tand_c_)
        apiController = ApiController(this, this)
        apiController.loader.show()
        apiController.makeCallGET(AppConstant.she_rules)
        ivSubmit.setColorFilter(this.resources.getColor(R.color.lightblue))
        ivBack.visibility = View.GONE
        Glide.with(this).load(R.drawable.sherules)
                .placeholder(R.drawable.sherules).into(imageView)
        ivSubmit.setOnClickListener {
            if(tvRemember.isChecked) {
                startActivity(Intent(activity, HomeActivity::class.java))
                finish()
            }
            else{

                YoYo.with(Techniques.Shake)
                        .duration(50)
                        .repeat(5)
                        .playOn(tvRemember)
            }
        }
    }

    override fun onSuccess(tag: String, response: String) {
        apiController.loader.dismiss()
        if (tag == AppConstant.she_rules) {
            try {
                val model = apiController.parseJson(response, SheRuleBean::class.java)
                rvItems.layoutManager = LinearLayoutManager(this)
                rvItems.adapter = SheRuleAdapter(this, model.result, 0)
                rvItems.isNestedScrollingEnabled = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    override fun onFailure(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onError(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        apiController.loader.dismiss()
        Utility().showSnackBar(this, msg)
    }

    override fun onDestroy() {
        apiController.loader.dismiss()
        super.onDestroy()
        apiController.unSubscribe()
    }


}
