package com.fpstudio.stretchreminder.ui.screen.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.domain.usecase.GetSubscriptionInfoUseCase
import com.fpstudio.stretchreminder.domain.usecase.SubmitFeedbackUseCase
import com.fpstudio.stretchreminder.ui.screen.feedback.contract.FeedbackScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.feedback.contract.FeedbackScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.feedback.contract.FeedbackScreenContract.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class FeedbackScreenViewModel(
    private val submitFeedbackUseCase: SubmitFeedbackUseCase,
    private val getSubscriptionInfoUseCase: GetSubscriptionInfoUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()
    
    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.SelectSubject -> {
                _uiState.update { it.copy(selectedSubject = intent.subject) }
            }
            is Intent.UpdateCustomSubject -> {
                _uiState.update { it.copy(customSubject = intent.text) }
            }
            is Intent.UpdateMessage -> {
                _uiState.update { it.copy(message = intent.text) }
            }
            Intent.SubmitFeedback -> submitFeedback()
            Intent.NavigateBack -> {
                viewModelScope.launch {
                    _sideEffect.send(SideEffect.NavigateBack)
                }
            }
            Intent.DismissError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }
    
    private fun submitFeedback() {
        val state = _uiState.value
        
        // Validation
        if (state.message.isBlank()) {
            _uiState.update { it.copy(error = "Please enter a message") }
            return
        }
        
        if (state.selectedSubject == null) {
            _uiState.update { it.copy(error = "Please select a subject") }
            return
        }
        
        // Get subject text
        val subject = if (state.selectedSubject == com.fpstudio.stretchreminder.ui.screen.feedback.contract.FeedbackScreenContract.FeedbackSubject.OTHER && state.customSubject.isNotBlank()) {
            state.customSubject
        } else {
            state.selectedSubject.displayName
        }
        
        _uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            // Get user type
            val subscriptionInfo = getSubscriptionInfoUseCase()
            val userType = if (subscriptionInfo?.isActive == true) "premium" else "free"
            
            // Get country code
            val country = Locale.getDefault().country
            
            val result = submitFeedbackUseCase(
                subject = subject,
                message = state.message,
                platform = "android",
                userType = userType,
                country = country
            )
            
            _uiState.update { it.copy(isLoading = false) }
            
            result.onSuccess { message ->
                _sideEffect.send(SideEffect.ShowSuccess(message))
            }.onFailure { error ->
                _uiState.update { it.copy(error = error.message ?: "Failed to submit feedback") }
            }
        }
    }
}
