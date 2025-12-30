package com.fpstudio.stretchreminder.domain.repository

import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import kotlinx.coroutines.flow.Flow

interface BillingRepository {
    
    /**
     * Flow that emits the current billing connection state
     */
    val billingConnectionState: Flow<BillingConnectionState>
    
    /**
     * Flow that emits available subscription products
     */
    val subscriptionProducts: Flow<List<ProductDetails>>
    
    /**
     * Flow that emits active purchases
     */
    val purchases: Flow<List<Purchase>>
    
    /**
     * Initialize the billing client
     */
    suspend fun initialize()
    
    /**
     * Query subscription products from Play Store
     */
    suspend fun querySubscriptionProducts()
    
    /**
     * Launch purchase flow for a subscription
     * @param activity The activity to launch the billing flow from
     * @param productDetails The product to purchase
     */
    suspend fun launchPurchaseFlow(
        activity: android.app.Activity,
        productDetails: ProductDetails
    ): BillingResult
    
    /**
     * Restore previous purchases
     */
    suspend fun restorePurchases(): BillingResult
    
    /**
     * Check if user has an active subscription
     */
    suspend fun hasActiveSubscription(): Boolean
    
    /**
     * Acknowledge a purchase
     */
    suspend fun acknowledgePurchase(purchase: Purchase): BillingResult
    
    /**
     * End billing client connection
     */
    fun endConnection()
}

enum class BillingConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    ERROR
}

sealed class BillingResult {
    object Success : BillingResult()
    data class Error(val message: String, val code: Int = 0) : BillingResult()
    object Cancelled : BillingResult()
}
