package com.fpstudio.stretchreminder.ui.screen.premium

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.revenuecat.purchases.ui.revenuecatui.PaywallDialog
import com.revenuecat.purchases.ui.revenuecatui.PaywallDialogOptions
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.SideEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun PremiumScreen(
    viewModel: PremiumScreenViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Handle side effects
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SideEffect.NavigateBack -> onNavigateBack()
                SideEffect.ShowPurchaseSuccess -> onNavigateToSuccess()
                is SideEffect.ShowPurchaseError -> {
                    // Error is handled by the Paywall UI
                }
                SideEffect.ShowRestoreSuccess -> {
                    // Success is handled by the Paywall UI
                    onNavigateToSuccess()
                }
                is SideEffect.ShowRestoreError -> {
                    // Error is handled by the Paywall UI
                }
            }
        }
    }
    
    // Show RevenueCat Paywall
    PaywallDialog(
        PaywallDialogOptions.Builder()
            .setDismissRequest {
                viewModel.handleClose()
            }
            .setListener(
                object : com.revenuecat.purchases.ui.revenuecatui.PaywallListener {
                    override fun onPurchaseStarted(rcPackage: com.revenuecat.purchases.Package) {
                        viewModel.handlePurchaseStarted()
                    }
                    
                    override fun onPurchaseCompleted(
                        customerInfo: com.revenuecat.purchases.CustomerInfo,
                        storeTransaction: com.revenuecat.purchases.models.StoreTransaction
                    ) {
                        viewModel.handlePurchaseSuccess()
                    }
                    
                    override fun onPurchaseError(error: com.revenuecat.purchases.PurchasesError) {
                        viewModel.handlePurchaseError(error.message)
                    }
                    
                    override fun onPurchaseCancelled() {
                        viewModel.handlePurchaseCancelled()
                    }
                    
                    override fun onRestoreStarted() {
                        viewModel.handleRestoreStarted()
                    }
                    
                    override fun onRestoreCompleted(customerInfo: com.revenuecat.purchases.CustomerInfo) {
                        viewModel.handleRestoreCompleted(customerInfo)
                    }
                    
                    override fun onRestoreError(error: com.revenuecat.purchases.PurchasesError) {
                        viewModel.handleRestoreError(error.message)
                    }
                }
            )
            .build()
    )
}
