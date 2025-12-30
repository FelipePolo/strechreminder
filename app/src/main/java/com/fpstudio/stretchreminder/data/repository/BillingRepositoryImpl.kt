package com.fpstudio.stretchreminder.data.repository

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.fpstudio.stretchreminder.domain.repository.BillingConnectionState
import com.fpstudio.stretchreminder.domain.repository.BillingRepository
import com.fpstudio.stretchreminder.domain.repository.BillingResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class BillingRepositoryImpl(
    private val context: Context
) : BillingRepository {
    
    private val _billingConnectionState = MutableStateFlow(BillingConnectionState.DISCONNECTED)
    override val billingConnectionState: StateFlow<BillingConnectionState> = _billingConnectionState.asStateFlow()
    
    private val _subscriptionProducts = MutableStateFlow<List<ProductDetails>>(emptyList())
    override val subscriptionProducts: StateFlow<List<ProductDetails>> = _subscriptionProducts.asStateFlow()
    
    private val _purchases = MutableStateFlow<List<Purchase>>(emptyList())
    override val purchases: StateFlow<List<Purchase>> = _purchases.asStateFlow()
    
    private var billingClient: BillingClient? = null
    
    companion object {
        const val PRODUCT_ID_ANNUAL = "strech-reminder-premium-annual-plan"
        const val PRODUCT_ID_MONTHLY = "strech-reminder-premium-monthly"
    }
    
    override suspend fun initialize() {
        if (billingClient != null) return
        
        _billingConnectionState.value = BillingConnectionState.CONNECTING
        
        billingClient = BillingClient.newBuilder(context)
            .setListener { billingResult, purchases ->
                handlePurchaseUpdate(billingResult, purchases)
            }
            .enablePendingPurchases()
            .build()
        
        startConnection()
    }
    
    private suspend fun startConnection() = suspendCancellableCoroutine { continuation ->
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: com.android.billingclient.api.BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    _billingConnectionState.value = BillingConnectionState.CONNECTED
                    continuation.resume(Unit)
                } else {
                    _billingConnectionState.value = BillingConnectionState.ERROR
                    continuation.resume(Unit)
                }
            }
            
            override fun onBillingServiceDisconnected() {
                _billingConnectionState.value = BillingConnectionState.DISCONNECTED
            }
        })
    }
    
    override suspend fun querySubscriptionProducts() {
        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PRODUCT_ID_ANNUAL)
                .setProductType(BillingClient.ProductType.SUBS)
                .build(),
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(PRODUCT_ID_MONTHLY)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        )
        
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()
        
        billingClient?.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                _subscriptionProducts.value = productDetailsList
            }
        }
    }
    
    override suspend fun launchPurchaseFlow(
        activity: Activity,
        productDetails: ProductDetails
    ): BillingResult = suspendCancellableCoroutine { continuation ->
        val offerToken = productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken
        
        if (offerToken == null) {
            continuation.resume(BillingResult.Error("No offer token available"))
            return@suspendCancellableCoroutine
        }
        
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .setOfferToken(offerToken)
                .build()
        )
        
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        
        val result = billingClient?.launchBillingFlow(activity, billingFlowParams)
        
        when (result?.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                // Purchase flow launched successfully
                // Result will come through PurchasesUpdatedListener
                continuation.resume(BillingResult.Success)
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                continuation.resume(BillingResult.Cancelled)
            }
            else -> {
                continuation.resume(
                    BillingResult.Error(
                        result?.debugMessage ?: "Unknown error",
                        result?.responseCode ?: -1
                    )
                )
            }
        }
    }
    
    private fun handlePurchaseUpdate(billingResult: com.android.billingclient.api.BillingResult, purchases: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            _purchases.value = purchases
            
            // Acknowledge purchases that haven't been acknowledged yet
            purchases.forEach { purchase ->
                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
                    acknowledgePurchaseInternal(purchase)
                }
            }
        }
    }
    
    private fun acknowledgePurchaseInternal(purchase: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        
        billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
            // Purchase acknowledged
        }
    }
    
    override suspend fun acknowledgePurchase(purchase: Purchase): BillingResult {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        
        return suspendCancellableCoroutine { continuation ->
            billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    continuation.resume(BillingResult.Success)
                } else {
                    continuation.resume(
                        BillingResult.Error(
                            billingResult.debugMessage,
                            billingResult.responseCode
                        )
                    )
                }
            }
        }
    }
    
    override suspend fun restorePurchases(): BillingResult {
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
        
        return suspendCancellableCoroutine { continuation ->
            billingClient?.queryPurchasesAsync(params) { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    _purchases.value = purchases
                    continuation.resume(BillingResult.Success)
                } else {
                    continuation.resume(
                        BillingResult.Error(
                            billingResult.debugMessage,
                            billingResult.responseCode
                        )
                    )
                }
            }
        }
    }
    
    override suspend fun hasActiveSubscription(): Boolean {
        // Debug bypass: always return true in debug builds
        if (com.fpstudio.stretchreminder.util.DebugBillingBypass.hasActiveSubscription()) {
            android.util.Log.d("BillingRepository", "Debug bypass: returning true for active subscription")
            return true
        }
        
        return _purchases.value.any { purchase ->
            purchase.purchaseState == Purchase.PurchaseState.PURCHASED &&
            (purchase.products.contains(PRODUCT_ID_ANNUAL) || 
             purchase.products.contains(PRODUCT_ID_MONTHLY))
        }
    }
    
    override fun endConnection() {
        billingClient?.endConnection()
        billingClient = null
        _billingConnectionState.value = BillingConnectionState.DISCONNECTED
    }
}
