package com.fpstudio.stretchreminder.ui.screen.premium

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.domain.usecase.CheckEntitlementUseCase
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.UiState
import com.revenuecat.purchases.CustomerInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PremiumScreenViewModel(
    private val checkEntitlementUseCase: CheckEntitlementUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect> = _sideEffect.asSharedFlow()
    
    fun handleClose() {
        viewModelScope.launch {
            _sideEffect.emit(SideEffect.NavigateBack)
        }
    }
    
    fun handlePurchaseStarted() {
        _uiState.value = _uiState.value.copy(isLoading = true)
    }
    
    fun handlePurchaseSuccess() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = false)
            
            // Verify entitlement
            if (checkEntitlementUseCase()) {
                _sideEffect.emit(SideEffect.ShowPurchaseSuccess)
            } else {
                _sideEffect.emit(SideEffect.ShowPurchaseError("Entitlement not active after purchase"))
            }
        }
    }
    
    fun handlePurchaseError(message: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = false)
            _sideEffect.emit(SideEffect.ShowPurchaseError(message))
        }
    }
    
    fun handlePurchaseCancelled() {
        _uiState.value = _uiState.value.copy(isLoading = false)
    }
    
    fun handleRestoreStarted() {
        _uiState.value = _uiState.value.copy(isLoading = true)
    }
    
    fun handleRestoreCompleted(customerInfo: CustomerInfo) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = false)
            
            if (checkEntitlementUseCase()) {
                _sideEffect.emit(SideEffect.ShowRestoreSuccess)
            } else {
                _sideEffect.emit(SideEffect.ShowRestoreError("No active subscriptions found"))
            }
        }
    }
    
    fun handleRestoreError(message: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = false)
            _sideEffect.emit(SideEffect.ShowRestoreError(message))
        }
    }
}
