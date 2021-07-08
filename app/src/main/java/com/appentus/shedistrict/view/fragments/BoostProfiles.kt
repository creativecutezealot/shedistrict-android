package com.appentus.shedistrict.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails

import com.appentus.shedistrict.R
import com.appentus.shedistrict.adapters.PurchaseAdapter
import com.appentus.shedistrict.adapters.PurchaseBoostAdapter
import com.appentus.shedistrict.models.BoostsListBean
import com.appentus.shedistrict.models.PurchaseHelper
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_boost_profiles.*
import kotlinx.android.synthetic.main.fragment_boosts_pic.*

/**
 * A simple [Fragment] subclass.
 */
class BoostProfiles : Fragment(), ResponseListener, PurchaseHelper.PurchaseHelperListener  {
    lateinit var purchaseHelper: PurchaseHelper
    private lateinit var skudemolist: MutableList<String>

    var skuBoostListPrice: MutableList<String> = ArrayList()
    var skuBoostListTitle: MutableList<String> = ArrayList()
    var skuBoostListProductid: MutableList<String> = ArrayList()

    var skuPlanListPrice: MutableList<String> = ArrayList()
    var skuPlanListProductid: MutableList<String> = ArrayList()

    lateinit var apiController: ApiController
    var list:MutableList<BoostsListBean.ResultBean>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boost_profiles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        purchaseHelper = PurchaseHelper(activity!!,this)
        apiController = ApiController(activity!!, this)
        skudemolist = ArrayList()

//        titleTop.text = "Purchases"

        apiController.loader.show()

        skudemolist.add("plans_1")
        skudemolist.add("plans_2")
        skudemolist.add("plans_3")
        skudemolist.add("boosts_1")
        skudemolist.add("boosts_2")
        skudemolist.add("boosts_3")
        skudemolist.add("boosts_4")



    }

    override fun onSuccess(tag: String, response: String) {

    }

    override fun onFailure(tag: String, msg: String) {

    }

    override fun onError(tag: String, msg: String) {

    }

    override fun onNoConnection(tag: String, msg: String) {

    }

    override fun onSkuQueryResponse(skuDetails: MutableList<SkuDetails>?) {
        apiController.loader.dismiss()

        for(i in 0 until skuDetails!!.size){
            // val jsonObject1: JSONObject = jsonArray.getJSONObject(i)
            if(skuDetails[i].sku.startsWith("boosts"))
            {
                skuBoostListTitle.addAll(listOf(skuDetails[i].title))
                skuBoostListPrice.addAll(listOf(skuDetails[i].price))
                skuBoostListProductid.addAll(listOf(skuDetails[i].sku))

            }
            else{
                skuPlanListProductid.addAll(listOf(skuDetails[i].sku))
                skuPlanListPrice.addAll(listOf(skuDetails[i].title))

            }
        }

        Log.e("skuBoostListTitle",skuBoostListTitle.toString())
        setAdapter(skuBoostListTitle,skuBoostListPrice)


    }

    override fun onServiceConnected(resultCode: Int) {
        purchaseHelper.getSkuDetails(skudemolist, BillingClient.SkuType.INAPP)
    }

    override fun onPurchasehistoryResponse(purchasedItems: MutableList<Purchase>?) {

    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {

    }
    private fun setAdapter(skuBoostListTitle: MutableList<String>, skuBoostListPrice: MutableList<String>) {

        rvBoosts.layoutManager= LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        rvBoosts.adapter = PurchaseAdapter(activity!!,skuBoostListTitle,skuBoostListPrice)

        /*rvBoostslist.addOnItemTouchListener(RecyclerTouchListener(context,rvBoostslist, RecyclerTouchListener.ClickListener { _, pos ->

            purchaseHelper.launchBillingFLow(BillingClient.SkuType.INAPP, skuBoostListProductid[pos])

        }))*/
    }
}
