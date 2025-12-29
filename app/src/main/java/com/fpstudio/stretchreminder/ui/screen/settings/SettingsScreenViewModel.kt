package com.fpstudio.stretchreminder.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsScreenViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect: SharedFlow<SideEffect> = _sideEffect.asSharedFlow()
    
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.SelectWorkPosition -> {
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        workPosition = intent.position
                    )
                )
            }
            
            is Intent.ToggleFocusArea -> {
                val currentAreas = _uiState.value.routinePreferences.focusAreas.toMutableSet()
                if (currentAreas.contains(intent.area)) {
                    currentAreas.remove(intent.area)
                } else {
                    currentAreas.add(intent.area)
                }
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        focusAreas = currentAreas
                    )
                )
            }
            
            is Intent.ToggleWorkday -> {
                val currentDays = _uiState.value.routinePreferences.workdays.toMutableSet()
                if (currentDays.contains(intent.day)) {
                    currentDays.remove(intent.day)
                } else {
                    currentDays.add(intent.day)
                }
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        workdays = currentDays
                    )
                )
            }
            
            is Intent.UpdateStartTime -> {
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        startTime = intent.time
                    )
                )
            }
            
            is Intent.UpdateEndTime -> {
                _uiState.value = _uiState.value.copy(
                    routinePreferences = _uiState.value.routinePreferences.copy(
                        endTime = intent.time
                    )
                )
            }
            
            is Intent.ToggleNotifications -> {
                _uiState.value = _uiState.value.copy(
                    appSettings = _uiState.value.appSettings.copy(
                        notificationsEnabled = intent.enabled
                    )
                )
            }
            
            is Intent.UpdateDisplayName -> {
                _uiState.value = _uiState.value.copy(
                    profile = _uiState.value.profile.copy(
                        displayName = intent.name
                    )
                )
            }
            
            Intent.EditProfile -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateToEditProfile)
                }
            }
            
            Intent.UpgradeToPremium -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateToPremium)
                }
            }
            
            Intent.RateApp -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateToRateApp)
                }
            }
            
            Intent.SendFeedback -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateToFeedback)
                }
            }
            
            Intent.SaveChanges -> {
                viewModelScope.launch {
                    // TODO: Save settings to repository
                    _sideEffect.emit(SideEffect.ShowSaveSuccess)
                }
            }
            
            Intent.NavigateBack -> {
                viewModelScope.launch {
                    _sideEffect.emit(SideEffect.NavigateBack)
                }
            }
            
            Intent.ToggleDarkMode -> {
                // TODO: Implement dark mode toggle
            }
        }
    }
}
