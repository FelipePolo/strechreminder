package com.fpstudio.stretchreminder.ui.screen.premium

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.domain.repository.RevenueCatRepository
import com.fpstudio.stretchreminder.domain.usecase.CheckEntitlementUseCase
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
    private val revenueCatRepository: RevenueCatRepository,
    private val checkEntitlementUseCase: CheckEntitlementUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect> = _sideEffect.asSharedFlow()
    
    private var currentActivity: Activity? = null
    
    init {
        loadOfferings()
    }
    
    fun setActivity(activity: Activity) {
        currentActivity = activity
    }
    
    private fun loadOfferings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            revenueCatRepository.getOfferings().fold(
                onSuccess = { offerings ->
                    val currentOffering = offerings.current
                    
                    if (currentOffering != null) {
                        val annualPackage = currentOffering.availablePackages.find { 
                            it.packageType == com.revenuecat.purchases.PackageType.ANNUAL ||
                            it.identifier.contains("yearly", ignoreCase = true)
                        }
                        
                        val monthlyPackage = currentOffering.availablePackages.find { 
                            it.packageType == com.revenuecat.purchases.PackageType.MONTHLY ||
                            it.identifier.contains("monthly", ignoreCase = true)
                        }
                        
                        // Also try to find by product ID if package type fails
                        val annualPkg = annualPackage ?: currentOffering.availablePackages.find {
                            it.product.id.contains("annual", ignoreCase = true)
                        }
                        
                        val monthlyPkg = monthlyPackage ?: currentOffering.availablePackages.find {
                            it.product.id.contains("monthly", ignoreCase = true)
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            annualPackage = annualPkg,
                            monthlyPackage = monthlyPkg,
                            isLoading = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "No offerings available"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Failed to load offerings"
                    )
                }
            )
        }
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
        val activity = currentActivity
        if (activity == null) {
            viewModelScope.launch {
                _sideEffect.emit(SideEffect.ShowPurchaseError("Activity not available"))
            }
            return
        }
        
        val packageToPurchase = when (_uiState.value.selectedPlan) {
            SubscriptionPlan.ANNUAL -> _uiState.value.annualPackage
            SubscriptionPlan.MONTHLY -> _uiState.value.monthlyPackage
        }
        
        if (packageToPurchase == null) {
            viewModelScope.launch {
                _sideEffect.emit(SideEffect.ShowPurchaseError("Package not available"))
            }
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                purchaseState = PurchaseState.PURCHASING,
                isLoading = true
            )
            
            revenueCatRepository.purchasePackage(activity, packageToPurchase).fold(
                onSuccess = { customerInfo ->
                    // Verify entitlement
                    if (checkEntitlementUseCase()) {
                         _uiState.value = _uiState.value.copy(
                            purchaseState = PurchaseState.SUCCESS,
                            isLoading = false
                        )
                        _sideEffect.emit(SideEffect.ShowPurchaseSuccess)
                    } else {
                         _uiState.value = _uiState.value.copy(
                            purchaseState = PurchaseState.ERROR,
                            isLoading = false,
                            errorMessage = "Purchase successful but premium not active"
                        )
                        _sideEffect.emit(SideEffect.ShowPurchaseError("Entitlement not active after purchase"))
                    }
                },
                onFailure = { error ->
                    // Check if cancelled
                    if (error.message?.contains("cancelled", ignoreCase = true) == true) {
                        _uiState.value = _uiState.value.copy(
                            purchaseState = PurchaseState.IDLE,
                            isLoading = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            purchaseState = PurchaseState.ERROR,
                            isLoading = false,
                            errorMessage = error.message
                        )
                        _sideEffect.emit(SideEffect.ShowPurchaseError(error.message ?: "Purchase failed"))
                    }
                }
            )
        }
    }
    
    private fun restorePurchases() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            revenueCatRepository.restorePurchases().fold(
                onSuccess = { customerInfo ->
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    
                    if (checkEntitlementUseCase()) {
                        _sideEffect.emit(SideEffect.ShowRestoreSuccess)
                    } else {
                        _sideEffect.emit(SideEffect.ShowRestoreError("No active subscriptions found"))
                    }
                },
                onFailure = { error ->
                     _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                    _sideEffect.emit(SideEffect.ShowRestoreError(error.message ?: "Restore failed"))
                }
            )
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        currentActivity = null
    }
}
