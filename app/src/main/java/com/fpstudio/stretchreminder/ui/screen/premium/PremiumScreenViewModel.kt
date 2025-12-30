package com.fpstudio.stretchreminder.ui.screen.premium

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.fpstudio.stretchreminder.data.repository.BillingRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.BillingConnectionState
import com.fpstudio.stretchreminder.domain.repository.BillingRepository
import com.fpstudio.stretchreminder.domain.repository.BillingResult
import com.fpstudio.stretchreminder.domain.usecase.GetSubscriptionStatusUseCase
import com.fpstudio.stretchreminder.domain.usecase.PurchaseSubscriptionUseCase
import com.fpstudio.stretchreminder.domain.usecase.RestorePurchasesUseCase
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.PurchaseState
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.SubscriptionPlan
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PremiumScreenViewModel(
    private val billingRepository: BillingRepository,
    private val purchaseSubscriptionUseCase: PurchaseSubscriptionUseCase,
    private val restorePurchasesUseCase: RestorePurchasesUseCase,
    private val getSubscriptionStatusUseCase: GetSubscriptionStatusUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect> = _sideEffect.asSharedFlow()
    
    private var currentActivity: Activity? = null
    
    init {
        // Log bypass status for debugging
        com.fpstudio.stretchreminder.util.DebugBillingBypass.logBypassStatus()
        
        initializeBilling()
    }
    
    fun setActivity(activity: Activity) {
        currentActivity = activity
    }
    
    private fun initializeBilling() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Initialize billing
            billingRepository.initialize()
            
            // Observe billing connection state
            billingRepository.billingConnectionState.collect { state ->
                when (state) {
                    BillingConnectionState.CONNECTED -> {
                        _uiState.value = _uiState.value.copy(billingConnected = true)
                        queryProducts()
                    }
                    BillingConnectionState.ERROR -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            billingConnected = false,
                            errorMessage = "Failed to connect to billing service"
                        )
                    }
                    else -> {
                        _uiState.value = _uiState.value.copy(billingConnected = false)
                    }
                }
            }
        }
        
        // Observe products
        viewModelScope.launch {
            billingRepository.subscriptionProducts.collect { products ->
                val annualProduct = products.find { 
                    it.productId == BillingRepositoryImpl.PRODUCT_ID_ANNUAL 
                }
                val monthlyProduct = products.find { 
                    it.productId == BillingRepositoryImpl.PRODUCT_ID_MONTHLY 
                }
                
                _uiState.value = _uiState.value.copy(
                    annualProduct = annualProduct,
                    monthlyProduct = monthlyProduct,
                    isLoading = false
                )
            }
        }
    }
    
    private suspend fun queryProducts() {
        billingRepository.querySubscriptionProducts()
    }
    
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.SelectPlan -> {
                _uiState.value = _uiState.value.copy(selectedPlan = intent.plan)
            }
            
            Intent.StartTrial -> {
                launchPurchaseFlow()
            }
            
            Intent.RestorePurchases -> {
                restorePurchases()
            }
            
            Intent.Close -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateBack)
                }
            }
            
            Intent.DismissError -> {
                _uiState.value = _uiState.value.copy(
                    errorMessage = null,
                    purchaseState = PurchaseState.IDLE
                )
            }
        }
    }
    
    private fun launchPurchaseFlow() {
        // Debug bypass: simulate successful purchase
        if (com.fpstudio.stretchreminder.util.DebugBillingBypass.isEnabled()) {
            viewModelScope.launch {
                android.util.Log.d("PremiumViewModel", "Debug bypass: simulating successful purchase")
                _uiState.value = _uiState.value.copy(
                    purchaseState = PurchaseState.PURCHASING,
                    isLoading = true
                )
                kotlinx.coroutines.delay(1000) // Simulate processing
                _uiState.value = _uiState.value.copy(
                    purchaseState = PurchaseState.SUCCESS,
                    isLoading = false
                )
                _sideEffect.emit(SideEffect.ShowPurchaseSuccess)
            }
            return
        }
        
        val activity = currentActivity
        if (activity == null) {
            viewModelScope.launch {
                _sideEffect.emit(SideEffect.ShowPurchaseError("Activity not available"))
            }
            return
        }
        
        val productDetails = when (_uiState.value.selectedPlan) {
            SubscriptionPlan.ANNUAL -> _uiState.value.annualProduct
            SubscriptionPlan.MONTHLY -> _uiState.value.monthlyProduct
        }
        
        if (productDetails == null) {
            viewModelScope.launch {
                _sideEffect.emit(SideEffect.ShowPurchaseError("Product not available"))
            }
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                purchaseState = PurchaseState.PURCHASING,
                isLoading = true
            )
            
            when (val result = purchaseSubscriptionUseCase(activity, productDetails)) {
                is BillingResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        purchaseState = PurchaseState.SUCCESS,
                        isLoading = false
                    )
                    _sideEffect.emit(SideEffect.ShowPurchaseSuccess)
                }
                is BillingResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        purchaseState = PurchaseState.ERROR,
                        isLoading = false,
                        errorMessage = result.message
                    )
                    _sideEffect.emit(SideEffect.ShowPurchaseError(result.message))
                }
                is BillingResult.Cancelled -> {
                    _uiState.value = _uiState.value.copy(
                        purchaseState = PurchaseState.IDLE,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    private fun restorePurchases() {
        // Debug bypass: simulate successful restore
        if (com.fpstudio.stretchreminder.util.DebugBillingBypass.isEnabled()) {
            viewModelScope.launch {
                android.util.Log.d("PremiumViewModel", "Debug bypass: simulating successful restore")
                _uiState.value = _uiState.value.copy(isLoading = true)
                kotlinx.coroutines.delay(500) // Simulate processing
                _uiState.value = _uiState.value.copy(isLoading = false)
                _sideEffect.emit(SideEffect.ShowRestoreSuccess)
            }
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            when (val result = restorePurchasesUseCase()) {
                is BillingResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _sideEffect.emit(SideEffect.ShowRestoreSuccess)
                }
                is BillingResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                    _sideEffect.emit(SideEffect.ShowRestoreError(result.message))
                }
                is BillingResult.Cancelled -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        billingRepository.endConnection()
        currentActivity = null
    }
}
