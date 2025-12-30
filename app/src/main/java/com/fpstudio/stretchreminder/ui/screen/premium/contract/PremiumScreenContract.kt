package com.fpstudio.stretchreminder.ui.screen.premium.contract

import com.revenuecat.purchases.Package

object PremiumScreenContract {
    
    data class UiState(
        val selectedPlan: SubscriptionPlan = SubscriptionPlan.ANNUAL,
        val isLoading: Boolean = false,
        val billingConnected: Boolean = true, // RevenueCat is always connected basically
        val annualPackage: Package? = null,
        val monthlyPackage: Package? = null,
        val errorMessage: String? = null,
        val purchaseState: PurchaseState = PurchaseState.IDLE
    )
    
    enum class SubscriptionPlan {
        ANNUAL,
        MONTHLY
    }
    
    enum class PurchaseState {
        IDLE,
        PURCHASING,
        SUCCESS,
        ERROR
    }
    
    sealed class Intent {
        data class SelectPlan(val plan: SubscriptionPlan) : Intent()
        object StartTrial : Intent()
        object RestorePurchases : Intent()
        object Close : Intent()
        object DismissError : Intent()
    }
    
    sealed class SideEffect {
        object NavigateBack : SideEffect()
        object ShowPurchaseSuccess : SideEffect()
        data class ShowPurchaseError(val message: String) : SideEffect()
        object ShowRestoreSuccess : SideEffect()
        data class ShowRestoreError(val message: String) : SideEffect()
    }
}
