package com.fpstudio.stretchreminder.domain.repository

import android.app.Activity
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Offerings
import com.revenuecat.purchases.Package
import kotlinx.coroutines.flow.Flow

interface RevenueCatRepository {
    /**
     * Get current offerings to display paywall
     */
    suspend fun getOfferings(): Result<Offerings>
    
    /**
     * Purchase a package
     */
    suspend fun purchasePackage(activity: Activity, packageToPurchase: Package): Result<CustomerInfo>
    
    /**
     * Restore purchases
     */
    suspend fun restorePurchases(): Result<CustomerInfo>
    
    /**
     * Get current customer info
     */
    suspend fun getCustomerInfo(): Result<CustomerInfo>
    
    /**
     * Check if user has active entitlement
     */
    fun hasActiveEntitlement(entitlementId: String = "Premium"): Boolean
    
    /**
     * Flow of customer info updates
     */
    val customerInfoFlow: Flow<CustomerInfo?>
}
