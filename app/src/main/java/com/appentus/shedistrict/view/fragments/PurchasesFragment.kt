package com.appentus.shedistrict.view.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.PlanAdapter
import com.appentus.shedistrict.adapters.PurchaseAdapter
import com.appentus.shedistrict.models.CurrentPlanBean
import com.appentus.shedistrict.models.PlansBean
import com.appentus.shedistrict.models.PurchaseHelper
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import kotlinx.android.synthetic.main.fragment_purchases.*
import kotlinx.android.synthetic.main.toolbar_title.*
import java.text.SimpleDateFormat


class PurchasesFragment : Fragment(), ResponseListener, PurchaseHelper.PurchaseHelperListener {
    lateinit var apiController: ApiController

    private val mBillingClient: BillingClient? = null
    lateinit var purchaseHelper: PurchaseHelper
    private lateinit var skudemolist: MutableList<String>

    var skuBoostListPrice: MutableList<String> = ArrayList()
    var skuBoostListTitle: MutableList<String> = ArrayList()
    var skuBoostListProductid: MutableList<String> = ArrayList()

    var skuPlanListPrice: MutableList<String> = ArrayList()
    var skuPlanListtitle: MutableList<String> = ArrayList()
    var amountlist: MutableList<String> = ArrayList()
    var skuPlanListProductid: MutableList<String> = ArrayList()
    var amount=""
    var numberofboosts=""
    var validity=""
    var cancelid=""
    var plan_id=""
    var validityrestore=""
    var amountrestore=""
    var restore=""

    var allAdvantageslist:MutableList<PlansBean.ResultBean> = ArrayList()
    var planlist:MutableList<PlansBean.ResultBean> =  ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        purchaseHelper = PurchaseHelper(activity!!,this)
        apiController = ApiController(activity!!, this)

        planlist = ArrayList()
        skudemolist = ArrayList()
        titleTop.text = "Purchases"
        apiController.loader.show()


        skudemolist.add("plans_1")
        skudemolist.add("plans_2")
        skudemolist.add("plans_3")
        skudemolist.add("boosts_1")
        skudemolist.add("boosts_2")
        skudemolist.add("boosts_3")
        skudemolist.add("boosts_4")

        ivBack.setOnClickListener {
            activity!!.onBackPressed()
        }

         currentpurchase()

        tvRestorePurchases.setOnClickListener {
            if(!plan_id.isNullOrEmpty()) {
                restore="1"
                purchaseHelper.launchBillingFLow(BillingClient.SkuType.INAPP, plan_id)

            }
            else{
                Utility().showSnackBar(activity!!,"You don't have any plan to restore")
            }
          }

        CancelPurchase.setOnClickListener {
            if(!plan_id.isNullOrEmpty()) {

                val map = HashMap<String, String>()
                map["id"] = cancelid
                apiController.makeCall(AppConstant.cancel_plan, map)

            }
        }

    }

    private fun currentpurchase() {

        val map = HashMap<String, String>()
         map["user_id"] =Utility().getUser()?.user_id.toString()
         apiController.makeCall(AppConstant.get_my_plan, map)

    }

    private fun dialogsecondtime(allAdvantageslist: String, s: String) {
        val dialog = Utility().fullScreenDialog(R.layout.second_subscription, activity!!)
        val rldialog = dialog.findViewById<LinearLayout>(R.id.rldialog)
        val ccDeny = dialog.findViewById<CardView>(R.id.ccDeny)
        val tvAdvantage = dialog.findViewById<TextView>(R.id.tvAdvantage)
        val tvPrice = dialog.findViewById<TextView>(R.id.tvPrice)
        val cvOneMonth = dialog.findViewById<TextView>(R.id.tvOnemMonth)
        val cvSixMonth = dialog.findViewById<TextView>(R.id.tvSixmMonth)
        val cvOneYear = dialog.findViewById<TextView>(R.id.tvOneyear)
        val ccPurchase = dialog.findViewById<CardView>(R.id.ccPurchase)

        rldialog.background = RoundView(activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))

        cvOneMonth.setOnClickListener {
            validity="1"
        }

        cvSixMonth.setOnClickListener {
            validity="6"
        }

        cvOneYear.setOnClickListener {
            validity="12"
        }




        tvPrice.text=allAdvantageslist.split(" ").get(0)+"!"

        ccPurchase.setOnClickListener {
            if (plan_id.isNullOrEmpty()) {
                if(!validity.equals("")){
                    purchaseHelper.launchBillingFLow(BillingClient.SkuType.INAPP,s)
                }else{
                    Utility().showSnackBar(activity!!,"Please select the duration")
                }

            }else{

                dialog.dismiss()
                Utility().showSnackBar(activity!!, "You have already a plan")

            }
        }

        ccDeny.setOnClickListener(){

            dialog.dismiss()
        }

    }

    private fun dialogfirsttime(allAdvantageslist: String, s: String) {
        val dialog = Utility().fullScreenDialog(R.layout.first_subscription, activity!!)
        val rldialog = dialog.findViewById<RelativeLayout>(R.id.rldialog)
        val ccDeny = dialog.findViewById<CardView>(R.id.ccDeny)
        val cvOneMonth = dialog.findViewById<TextView>(R.id.tvOnemMonth)
        val cvSixMonth = dialog.findViewById<TextView>(R.id.tvSixmMonth)
        val cvOneYear = dialog.findViewById<TextView>(R.id.tvOneyear)
        val ccPurchase = dialog.findViewById<CardView>(R.id.ccPurchase)
        val tvPrice = dialog.findViewById<TextView>(R.id.tvPrice)

        cvOneMonth.setOnClickListener {
            validity="1"
        }

        cvSixMonth.setOnClickListener {
            validity="6"
        }

        cvOneYear.setOnClickListener {
            validity="12"
        }
        rldialog.background = RoundView(activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))

        tvPrice.text=allAdvantageslist.split(" ").get(0)+"!"

        ccPurchase.setOnClickListener {
            if(plan_id.isNullOrEmpty()) {
                purchaseHelper.launchBillingFLow(BillingClient.SkuType.INAPP, s)
            }
            else{
                dialog.dismiss()
                Utility().showSnackBar(activity!!,"You have already a plan")
            }

        }

        ccDeny.setOnClickListener(){

            dialog.dismiss()
        }

    }

    private fun dialogLifetime(allAdvantageslist: String, s: String) {

        val dialog = Utility().fullScreenDialog(R.layout.lifetime_subscription, activity!!)
        val rldialog = dialog.findViewById<RelativeLayout>(R.id.rldialog)
        val ccDeny = dialog.findViewById<CardView>(R.id.ccDeny)
        val ccPurchases = dialog.findViewById<CardView>(R.id.ccPurchases)
        val tvAdvantages = dialog.findViewById<TextView>(R.id.tvAdvantages)
        val tvPrice = dialog.findViewById<TextView>(R.id.tvPrice)

        tvPrice.text=allAdvantageslist.split(" ").get(0)+":"


        rldialog.background = RoundView(activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))
        ccDeny.setOnClickListener(){
            dialog.dismiss()
        }

            ccPurchases.setOnClickListener {
                if (plan_id.isNullOrEmpty()) {
                    purchaseHelper.launchBillingFLow(BillingClient.SkuType.INAPP, s)
                } else {
                    dialog.dismiss()
                    Utility().showSnackBar(activity!!, "You have already a plan")
                }
            }


    }

    override fun onSuccess(tag: String, response: String) {
        var enddate=""
        if (tag == AppConstant.get_my_plan) {
            val model = apiController.parseJson(response, CurrentPlanBean::class.java)
            Log.e("ddzzzsfs",model.result.amount)

            val format1 = SimpleDateFormat("yyyy-MM-dd")
             val format2 =  SimpleDateFormat("dd/MM/yyyy")
             val date = format1.parse(model.result.end_date)
            enddate=format2.format(date)

            if(model.result.duration.equals("12")){

                tvCurrentPurchases1.text="1 Year Subscription-Renews "+enddate

            }else{

                tvCurrentPurchases1.text=model.result.duration+"  Month Subscription-Renews "+enddate
            }

            cancelid=model.result.id
            plan_id=model.result.plan_id
            validityrestore=model.result.duration
            amountrestore=model.result.amount


        }else if(tag == AppConstant.cancel_plan){

            Utility().showSnackBar(activity!!,"Successfully Cancel")


        }else if(tag == AppConstant.purchase_plan){

         //   Utility().showSnackBar(activity!!,"Plan Purchases successfully")
        }


    }
      override fun onFailure(tag: String, msg: String) {
        Utility().showSnackBar(activity!!, msg)
    }

    override fun onError(tag: String, msg: String) {
        Utility().showSnackBar(activity!!, msg)
    }

    override fun onNoConnection(tag: String, msg: String) {
        Utility().showSnackBar(activity!!, msg)

    }

    fun readySkuDetailsList(skuDetailsList: MutableList<SkuDetails>?) {
        Log.e("efefefef",skuDetailsList.toString())

    }

    override fun onSkuQueryResponse(skuDetails: MutableList<SkuDetails>?) {
        Log.e("dfbdsjjkjv",skuDetails.toString())
        apiController.loader.dismiss()
        //val jsonArray = JSONArray(skuDetails)
        for(i in 0 until skuDetails!!.size){
           // val jsonObject1: JSONObject = jsonArray.getJSONObject(i)
            if(skuDetails[i].sku.startsWith("boosts")!!)
            {
                skuBoostListTitle.addAll(listOf(skuDetails[i].title))
                skuBoostListPrice.addAll(listOf(skuDetails[i].price))
                skuBoostListProductid.addAll(listOf(skuDetails[i].sku))

            }
            else{
                skuPlanListProductid.addAll(listOf(skuDetails[i].sku))
                skuPlanListPrice.addAll(listOf(skuDetails[i].price))
                skuPlanListtitle.addAll(listOf(skuDetails[i].title))

            }

            amountlist.addAll(listOf(skuDetails[i].price))

        }


       // skuDetailsList= skuDetails!!
      //  list=skuDetails!!

        val glm = GridLayoutManager(activity, 2)
        glm.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) {
                    2
                } else when (position % 4) {
                    1, 3 -> 1
                    0, 2 -> 1
                    else ->  //never gonna happen
                        -1
                }
            }
        }
        rvPlans.setLayoutManager(glm)

      //  rvPlans.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvPlans.adapter = PlanAdapter(activity!!,skuPlanListPrice)

        rvPlans.addOnItemTouchListener(RecyclerTouchListener(context,rvPlans, RecyclerTouchListener.ClickListener { _, pos ->
                val str = skuPlanListtitle[pos]
                amount = amountlist[pos]
                if (str.split(" ").get(0).equals("$79.99")) {
                    //  allAdvantageslist=  model.result
                    dialogLifetime(skuPlanListPrice[pos], skuPlanListProductid[pos])
                } else if (str.split(" ").get(0).equals("$9.99")) {

                    dialogfirsttime(skuPlanListPrice[pos], skuPlanListProductid[pos])

                } else {
                    dialogsecondtime(skuPlanListPrice[pos], skuPlanListProductid[pos])

                }

        }))

        rvItem.layoutManager= LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        rvItem.adapter = PurchaseAdapter(activity!!,skuBoostListTitle,skuBoostListPrice)
        rvItem.addOnItemTouchListener(RecyclerTouchListener(context,rvItem, RecyclerTouchListener.ClickListener { _, pos ->

            purchaseHelper.launchBillingFLow(BillingClient.SkuType.INAPP, skuBoostListProductid[pos])
            amount=amountlist[pos]
            numberofboosts= skuBoostListTitle[pos]

        }))

    }
    override fun onServiceConnected(resultCode: Int) {
        Log.e("cdvdfv",resultCode.toString())
        purchaseHelper.getSkuDetails(skudemolist,BillingClient.SkuType.INAPP)
       //First implement this
    }

    override fun onPurchasehistoryResponse(purchasedItems: MutableList<Purchase>?)
    {
              Log.e("sacscdsd",purchasedItems.toString())

    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        Log.e("csdbjd",purchases.toString())
        if(purchases!= null){
            if(restore.equals("1"))
            {
                Restore()

            }else {
                Purchasessend(purchases)
            }

        }
    }

    private fun Purchasessend(purchases: MutableList<Purchase>?) {
        val map = HashMap<String,String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["plan_id"] = if (purchases!![0].sku.contains(" ")) purchases!![0].sku.split(" ").get(0) else purchases!![0].sku
        if(purchases[0].sku.startsWith("boosts")) {

            map["boosts"] = numberofboosts
            map["plan_type"] ="product"

        }
        else{
            map["boosts"] =""
            if(purchases[0].sku.equals("plans_1")||purchases[0].sku.equals("plans_2")) {
                map["plan_type"] = "subscription"
                map["validity"] =validity
            }
             map["plan_type"]="product"

             }

        map["amount"] =amount
              map["validity"] =""

        apiController.makeCall(AppConstant.purchase_plan,map)
    }

    private fun Restore(){
        val map = HashMap<String, String>()
        map["user_id"] = Utility().getUser()!!.user_id
        map["plan_id"] = plan_id
        map["boosts"] =""
        if(plan_id.equals("plans_1")||plan_id.equals("plans_2")) {
            map["plan_type"] = "subscription"
            map["validity"] =validityrestore
        }
        map["plan_type"]="product"

        map["amount"] =amountrestore
        map["validity"] =""
        apiController.makeCall(AppConstant.purchase_plan, map)
    }

}
