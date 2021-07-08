package app.notifyonlinestatus.billing

import android.app.Activity
import android.content.Context
import android.util.Log

import com.android.billingclient.api.*
import com.appentus.shedistrict.network.AppConstant
import com.appentus.shedistrict.view.fragments.PurchasesFragment


class BillingManager(context: Context,val purchasesFragment: PurchasesFragment) : BillingClientStateListener,SkuDetailsResponseListener,PurchasesUpdatedListener{

    //val dataReady: DataReady = dataReady
    var billingClient : BillingClient = BillingClient.newBuilder(context).setListener(this).build()
    var context: Context = context

    /*
    1. First step Start Connection
    2. Listen for the connection response
    3. if Connection established Successfully then query for SkuDetailsList
    4. after quering listen for the response
    5. with the particular skudetail(the required subs) call launchBillingFlow
    6. when the plan is purchased listen for the purchasedUpdated and handle for the response
    */

    init {
        billingClient.startConnection(this)
    }

    override fun onBillingServiceDisconnected() {
        Log.e("Billing manager","Disconneted")
    }

    override fun onBillingSetupFinished(responseCode: Int) {
        if (responseCode == BillingClient.BillingResponse.OK) {
            // The BillingClient is ready. You can query purchases here.
            querySkuDetails()
        }
    }

    private fun querySkuDetails() {
        val params = SkuDetailsParams.newBuilder().setSkusList(listOf(AppConstant.get_plan)).setType(BillingClient.SkuType.INAPP)
        billingClient.querySkuDetailsAsync(params.build(),this)
    }

    override fun onSkuDetailsResponse(responseCode: Int, skuDetailsList: MutableList<SkuDetails>?) {
        Log.wtf("Billing Manager","Data Recieved "+skuDetailsList)
        if(responseCode == BillingClient.BillingResponse.OK){
            Log.wtf("Billing Manager","Data Recieved "+skuDetailsList)
            purchasesFragment.readySkuDetailsList(skuDetailsList)
        }
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
            /*for (purchase in purchases) {
                handlePurchase(purchase)
            }*/
            Log.e("Purchased","purchased")
          //  dataReady.planPurchased(true)

        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            Log.e("Purchased","Cancled")
        } else {
            Log.e("Purchased","Do nothing")
        }
    }

    fun startBillingFlow(activity: Activity,sku : String){
        val flowParams = BillingFlowParams.newBuilder().setSku(sku).setType(BillingClient.SkuType.SUBS).build()
        val responseCode = billingClient.launchBillingFlow(activity, flowParams)
    }

}