package com.fpstudio.stretchreminder.ui.screen.premium

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.Intent
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

class PremiumScreenViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect> = _sideEffect.asSharedFlow()
    
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.SelectPlan -> {
                _uiState.value = _uiState.value.copy(selectedPlan = intent.plan)
            }
            
            Intent.StartTrial -> {
                viewModelScope.launch {
                    // TODO: Implement actual billing logic
                    _uiState.value = _uiState.value.copy(isLoading = true)
                    // Simulate API call
                    kotlinx.coroutines.delay(500)
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _sideEffect.emit(SideEffect.ShowTrialStarted)
                }
            }
            
            Intent.RestorePurchases -> {
                viewModelScope.launch {
                    // TODO: Implement actual restore logic
                    _uiState.value = _uiState.value.copy(isLoading = true)
                    kotlinx.coroutines.delay(500)
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _sideEffect.emit(SideEffect.ShowRestoreSuccess)
                }
            }
            
            Intent.Close -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateBack)
                }
            }
        }
    }
}
