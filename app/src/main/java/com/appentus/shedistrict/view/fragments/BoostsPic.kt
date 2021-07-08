package com.appentus.shedistrict.view.fragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.appentus.shedistrict.R
import com.appentus.shedistrict.Utils.RecyclerTouchListener
import com.appentus.shedistrict.Utils.RoundView
import com.appentus.shedistrict.Utils.Utility
import com.appentus.shedistrict.adapters.BoostsPicAdapter
import com.appentus.shedistrict.adapters.PurchaseBoostAdapter
import com.appentus.shedistrict.adapters.PurchaseBoostAdapterDialog
import com.appentus.shedistrict.models.BoostViewBean
import com.appentus.shedistrict.models.BoostsListBean
import com.appentus.shedistrict.models.PurchaseHelper
import com.appentus.shedistrict.network.ApiController
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.network.ResponseListener
import com.kotlinpermissions.ifNotNullOrElse
import kotlinx.android.synthetic.main.activity_record_intro.*
import kotlinx.android.synthetic.main.fragment_boosts_pic.*
import kotlinx.android.synthetic.main.fragment_purchases.*
import kotlinx.android.synthetic.main.item_purchase.view.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass.
 */
class BoostsPic : Fragment(), ResponseListener, PurchaseHelper.PurchaseHelperListener{
   // var list:MutableList<PurchaseModel>? = null
    lateinit var purchaseHelper: PurchaseHelper
    private lateinit var skudemolist: MutableList<String>
     var exampleThread: Thread? =null
    var days: String? =null
    var diffmin: Int? =null
    var diffsec: Int? =null
    var numberofboosts=""
    var amount=""
    var amountlist: MutableList<String> = ArrayList()

    var skuBoostListPrice: MutableList<String> = ArrayList()
    var skuBoostListTitle: MutableList<String> = ArrayList()
    var skuBoostListProductid: MutableList<String> = ArrayList()
     var rvBoostslistdialog: RecyclerView? =null

    lateinit var apiController: ApiController
    var list:MutableList<BoostsListBean.ResultBean>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boosts_pic, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        purchaseHelper = PurchaseHelper(activity!!,this)
        apiController = ApiController(activity!!, this)
        skudemolist = ArrayList()

     //   getDialogBoosts()

        apiController.loader.show()
        skudemolist.add("plans_1")
        skudemolist.add("plans_2")
        skudemolist.add("plans_3")
        skudemolist.add("boosts_1")
        skudemolist.add("boosts_2")
        skudemolist.add("boosts_3")
        skudemolist.add("boosts_4")


        if(tvTimer.text.equals("Results - 00.00")&& !Utility().getUser()!!.boosts.isNullOrEmpty()){
           tvBoostAgain.visibility= View.VISIBLE
            tvBoostAgain.setOnClickListener {
                boostAgain()
            }
        } else if(tvTimer.text.equals("Results - 00.00")&& Utility().getUser()!!.boosts.isNullOrEmpty()){

                 getDialogBoosts()

        }

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                val map = HashMap<String, String>()
                map["user_id"] =Utility().getUser()?.user_id.toString()
                apiController.makeCall(AppConstant.boosts_users, map)


            }
        }, 3000000)

        }


        private fun boostAgain() {
        val map = HashMap<String, String>()
        map["user_id"] =Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.boosts_again, map)

        }

    private fun getDialogBoosts() {
        val dialog = Utility().fullScreenDialog(R.layout.boosts_dialog, activity!!)
        val rldialog = dialog.findViewById<LinearLayout>(R.id.rldialog)
        val ccDeny = dialog.findViewById<CardView>(R.id.ccDeny)
        rvBoostslistdialog = dialog.findViewById<RecyclerView>(R.id.rvBoostslist)

        rldialog.background = RoundView(activity!!.resources.getColor(R.color.white), Utility().getRadius(40f))

        ccDeny.setOnClickListener(){
            dialog.dismiss()
        } }

      override fun onSkuQueryResponse(skuDetails: MutableList<SkuDetails>?) {
        Log.e("jcbvcjb",skuDetails.toString())
        apiController.loader.dismiss()

        for(i in 0 until skuDetails!!.size){
            // val jsonObject1: JSONObject = jsonArray.getJSONObject(i)
            if(skuDetails[i].sku.startsWith("boosts"))
            {
                skuBoostListTitle.addAll(listOf(skuDetails[i].title))
                skuBoostListPrice.addAll(listOf(skuDetails[i].price))
                skuBoostListProductid.addAll(listOf(skuDetails[i].sku))
                amountlist.addAll(listOf(skuDetails[i].price))

            }

        }
        setAdapter(skuBoostListTitle,skuBoostListPrice)
        if(rvBoostslistdialog!=null){
        setAdapterfordialog(skuBoostListTitle,skuBoostListPrice)

        }



    }
    private fun setAdapter(skuBoostListTitle: MutableList<String>, skuBoostListPrice: MutableList<String>) {

        Log.e("skuBoostListTitle",skuBoostListTitle.toString())
        // setAdapter(skuBoostListTitle,skuBoostListPrice)
        rvBoostslist.layoutManager= LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
        rvBoostslist.adapter = PurchaseBoostAdapter(activity!!,skuBoostListTitle,skuBoostListPrice)

        rvBoostslist.addOnItemTouchListener(RecyclerTouchListener(context,rvBoostslist, RecyclerTouchListener.ClickListener { _, pos ->

            purchaseHelper.launchBillingFLow(BillingClient.SkuType.INAPP, skuBoostListProductid[pos])
            numberofboosts= skuBoostListTitle[pos]
            amount=amountlist[pos]

        }))

    }

    private fun setAdapterfordialog(skuBoostListTitle: MutableList<String>, skuBoostListPrice: MutableList<String>) {

        Log.e("skuBoostListTitle",skuBoostListTitle.toString())
        // setAdapter(skuBoostListTitle,skuBoostListPrice)
        rvBoostslistdialog!!.layoutManager= GridLayoutManager(context,2,RecyclerView.HORIZONTAL,false)
        rvBoostslistdialog!!.adapter = PurchaseBoostAdapterDialog(activity!!,skuBoostListTitle,skuBoostListPrice)
        rvBoostslistdialog!!.addOnItemTouchListener(RecyclerTouchListener(context,rvBoostslistdialog, RecyclerTouchListener.ClickListener { view, pos ->

         //   view.rlBoosts.background=resources.getDrawable(R.drawable.background_boosts)
            purchaseHelper.launchBillingFLow(BillingClient.SkuType.INAPP, skuBoostListProductid[pos])
            numberofboosts= skuBoostListTitle[pos]
            amount=amountlist[pos]

        })) }

    override fun onServiceConnected(resultCode: Int) {
        purchaseHelper.getSkuDetails(skudemolist, BillingClient.SkuType.INAPP)
    }

    override fun onPurchasehistoryResponse(purchasedItems: MutableList<Purchase>?) {


    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        val map = HashMap<String, String>()
        map["user_id"] =Utility().getUser()?.user_id.toString()
        apiController.makeCall(AppConstant.boosts_users, map)
        if(purchases!= null){
            Purchasessend(purchases)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSuccess(tag: String, response: String) {
        if (tag == AppConstant.boosts_users) {
            val model = apiController.parseJson(response, BoostViewBean::class.java)

            rvBoosViewlist.layoutManager = GridLayoutManager(activity!!, 5)
            rvBoosViewlist.adapter = BoostsPicAdapter(activity!!,model.result)

            for(i in 0 until model.result.size) {
                if (Utility().getUser()!!.user_id.equals(model.result[i].user_id)) {
                    Log.e("modedsvdgdgdls",model.result[i].user_id)

                    val format1 = SimpleDateFormat("yyyy-MM-dd")
                    val format2 =  SimpleDateFormat("dd/MM/yyyy")
                    val dates= model.result[i].created.substring(0, model.result[i].created.indexOf(" "))
                    val date = format1.parse(dates)
                    val  enddate=format2.format(date)

                    val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                     days = getDaysBetweenDates(enddate, currentDate,"dd/MM/yyyy")
                    Log.e("compareTo",days)


                    val duration= model.result[i].created.split(" ")[1]
                    val names = duration
                    val time = names.split(":")

                    val minutes = time [1]
                    val seconds = time[2]

                    val dt =  Date()
                    val cminutes = dt.getMinutes()
                    val cseconds = dt.getSeconds()

                    val diffmin= cminutes-minutes.toInt()
                    val diffsec=cseconds-seconds.toInt()

                    if(days!!.toInt()==1 && diffmin<=5 ){
                        tvBoostAgain.visibility= View.GONE
                        exampleThread= thread(start = true) {
                           val actualminut= 5-diffmin
                            for (i in actualminut downTo 0){
                                for(j in 60-diffsec downTo 0){

                                    try
                                    {
                                        Thread.sleep(1000)
                                        if (activity != null)
                                            activity!!.runOnUiThread(Runnable {

                                                tvTimer.text="Results - "+String.format("%02d",i)+":"+String.format("%02d",j)

                                            })


                                    }
                                    catch (e:InterruptedException)
                                    {
                                        Thread.currentThread().interrupt(); // restore interrupted status
                                    }
                                }


                            }
                        } }
                }
            }
            if(model.result.isNullOrEmpty()){
                llBoosts.visibility=View.VISIBLE
                tvTimer.visibility=View.GONE
                tvBoostAgain.visibility=View.GONE

            }else{
                llBoosts.visibility=View.GONE
                tvTimer.visibility=View.VISIBLE

            }
            }
        else if(tag == AppConstant.boosts_again){
            val model = apiController.parseJson(response, BoostViewBean::class.java)
            apiController.makeCallGET(AppConstant.boosts_users)
            tvBoostAgain.visibility= View.GONE
            exampleThread = thread(start = true) {

                 for (i in 5 downTo 0) {
                     for (j in 59 downTo 0) {
                          try
                                {
                                     Thread.sleep(1000)
                                    if (activity != null)
                                        activity!!.runOnUiThread(Runnable {

                                        tvTimer.text = "You've been boosted! - " + String.format("%02d", i) + ":" + String.format("%02d", j)

                                    })


                                }
                                    catch (e:InterruptedException)
                                     {
                                      Thread.currentThread().interrupt(); // restore interrupted status
                                        }
                     } }
             }

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDisplayValue(ms: Long): String {
        val duration = Duration.ofMillis(ms)
        val minutes = duration.toMinutes()
        val seconds = duration.minusMinutes(minutes).seconds

        return "${minutes}:${seconds}"
    }

    fun getDaysBetweenDates(firstDateValue: String, secondDateValue: String, format: String): String
     {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
         val firstDate = sdf.parse(firstDateValue)
        val secondDate = sdf.parse(secondDateValue)

        if (firstDate == null || secondDate == null)
            return 0.toString()

        return (((secondDate.time - firstDate.time) / (1000 * 60 * 60 * 24)) + 1).toString()

    }

        @Override
        override fun onResume() {
        super.onResume()
            val map = HashMap<String, String>()
            map["user_id"] =Utility().getUser()?.user_id.toString()
            apiController.makeCall(AppConstant.boosts_users, map)

        }

    @Override
    override fun onPause() {
        super.onPause()
        if(exampleThread!=null){

            exampleThread!!.interrupt()
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
                map["validity"] =""
            }
            map["plan_type"]="product"

        }

        map["amount"] =amount
        map["validity"] =""

        apiController.makeCall(AppConstant.purchase_plan,map)
    }


}


