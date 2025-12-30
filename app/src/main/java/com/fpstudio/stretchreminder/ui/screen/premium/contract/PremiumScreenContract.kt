package com.fpstudio.stretchreminder.ui.screen.premium.contract

object PremiumScreenContract {
    
    data class UiState(
        val selectedPlan: SubscriptionPlan = SubscriptionPlan.ANNUAL,
        val isLoading: Boolean = false
    )
    
    enum class SubscriptionPlan(
        val displayName: String,
        val price: String,
        val billingPeriod: String,
        val discount: String? = null,
        val isBestValue: Boolean = false
    ) {
        ANNUAL(
            displayName = "Annual",
            price = "$39.99",
            billingPeriod = "/yr",
            discount = "Save 50%",
            isBestValue = true
        ),
        MONTHLY(
            displayName = "Monthly",
            price = "$7.99",
            billingPeriod = "/mo"
        )
    }
    
    sealed class Intent {
        data class SelectPlan(val plan: SubscriptionPlan) : Intent()
        object StartTrial : Intent()
        object RestorePurchases : Intent()
        object Close : Intent()
    }
    
    sealed class SideEffect {
        object NavigateBack : SideEffect()
        object ShowTrialStarted : SideEffect()
        object ShowRestoreSuccess : SideEffect()
        object ShowRestoreError : SideEffect()
    }
}
