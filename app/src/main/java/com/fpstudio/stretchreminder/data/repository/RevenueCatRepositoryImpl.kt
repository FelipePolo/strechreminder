package com.fpstudio.stretchreminder.data.repository

import android.app.Activity
import android.util.Log
import com.fpstudio.stretchreminder.domain.repository.RevenueCatRepository
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Offerings
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.PurchaseCallback
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import com.revenuecat.purchases.interfaces.ReceiveOfferingsCallback
import com.revenuecat.purchases.models.StoreTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RevenueCatRepositoryImpl : RevenueCatRepository {
    
    private val _customerInfo = MutableStateFlow<CustomerInfo?>(null)
    override val customerInfoFlow: StateFlow<CustomerInfo?> = _customerInfo.asStateFlow()
    
    init {
        loadCustomerInfo()
    }
    
    private fun loadCustomerInfo() {
        Purchases.sharedInstance.getCustomerInfo(object : ReceiveCustomerInfoCallback {
            override fun onReceived(customerInfo: CustomerInfo) {
                _customerInfo.value = customerInfo
                Log.d("RevenueCat", "Customer info loaded: ${customerInfo.entitlements.all}")
            }

            override fun onError(error: PurchasesError) {
                Log.e("RevenueCat", "Error loading customer info: ${error.message}")
            }
        })
    }
    
    override suspend fun getOfferings(): Result<Offerings> = suspendCoroutine { cont ->
        Purchases.sharedInstance.getOfferings(object : ReceiveOfferingsCallback {
            override fun onReceived(offerings: Offerings) {
                cont.resume(Result.success(offerings))
            }

            override fun onError(error: PurchasesError) {
                Log.e("RevenueCat", "Error getting offerings: ${error.message}")
                cont.resume(Result.failure(Exception(error.message)))
            }
        })
    }
    
    override suspend fun purchasePackage(
        activity: Activity, 
        packageToPurchase: Package
    ): Result<CustomerInfo> = suspendCoroutine { cont ->
        Purchases.sharedInstance.purchase(
            PurchaseParams.Builder(activity, packageToPurchase).build(),
            object : PurchaseCallback {
                override fun onCompleted(storeTransaction: StoreTransaction, customerInfo: CustomerInfo) {
                    _customerInfo.value = customerInfo
                    cont.resume(Result.success(customerInfo))
                }

                override fun onError(error: PurchasesError, userCancelled: Boolean) {
                    if (userCancelled) {
                        cont.resume(Result.failure(Exception("cancelled")))
                    } else {
                        cont.resume(Result.failure(Exception(error.message)))
                    }
                }
            }
        )
    }
    
    override suspend fun restorePurchases(): Result<CustomerInfo> = suspendCoroutine { cont ->
        Purchases.sharedInstance.restorePurchases(object : ReceiveCustomerInfoCallback {
            override fun onReceived(customerInfo: CustomerInfo) {
                _customerInfo.value = customerInfo
                cont.resume(Result.success(customerInfo))
            }

            override fun onError(error: PurchasesError) {
                cont.resume(Result.failure(Exception(error.message)))
            }
        })
    }
    
    override suspend fun getCustomerInfo(): Result<CustomerInfo> = suspendCoroutine { cont ->
        Purchases.sharedInstance.getCustomerInfo(object : ReceiveCustomerInfoCallback {
            override fun onReceived(customerInfo: CustomerInfo) {
                _customerInfo.value = customerInfo
                cont.resume(Result.success(customerInfo))
            }

            override fun onError(error: PurchasesError) {
                Log.e("RevenueCat", "Error getting customer info: ${error.message}")
                cont.resume(Result.failure(Exception(error.message)))
            }
        })
    }
    
    override fun hasActiveEntitlement(entitlementId: String): Boolean {
        val isActive = _customerInfo.value?.entitlements?.get(entitlementId)?.isActive == true
        Log.d("RevenueCat", "Checking entitlement '$entitlementId': $isActive")
        return isActive
    }
}
