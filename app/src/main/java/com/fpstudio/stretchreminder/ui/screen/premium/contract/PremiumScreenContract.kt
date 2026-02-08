package com.fpstudio.stretchreminder.ui.screen.premium.contract

object PremiumScreenContract {
    
    data class UiState(
        val isLoading: Boolean = false
    )
    
    sealed class SideEffect {
        object NavigateBack : SideEffect()
        object ShowPurchaseSuccess : SideEffect()
        data class ShowPurchaseError(val message: String) : SideEffect()
        object ShowRestoreSuccess : SideEffect()
        data class ShowRestoreError(val message: String) : SideEffect()
    }
}
