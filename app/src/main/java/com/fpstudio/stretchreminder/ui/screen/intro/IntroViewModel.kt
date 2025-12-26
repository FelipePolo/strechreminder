package com.fpstudio.stretchreminder.ui.screen.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IntroViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(IntroUiState())
    val uiState: StateFlow<IntroUiState> = _uiState.asStateFlow()
    
    init {
        checkUserData()
    }
    
    private fun checkUserData() {
        viewModelScope.launch {
            val user = getUserUseCase()
            val isOnboardingComplete = user != null && 
                                       user.name.isNotEmpty() && 
                                       user.bodyParts.isNotEmpty()
            _uiState.value = IntroUiState(
                isOnboardingComplete = isOnboardingComplete,
                isLoading = false
            )
        }
    }
}

data class IntroUiState(
    val isOnboardingComplete: Boolean = false,
    val isLoading: Boolean = true
)
