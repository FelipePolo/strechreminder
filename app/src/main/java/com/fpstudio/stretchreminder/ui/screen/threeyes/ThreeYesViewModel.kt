package com.fpstudio.stretchreminder.ui.screen.threeyes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.domain.repository.UserRepository
import com.fpstudio.stretchreminder.util.foundation.Mvi
import com.fpstudio.stretchreminder.util.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.UiState
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.Intent
import kotlinx.coroutines.launch

class ThreeYesViewModel(
    private val userRepository: UserRepository,
    private val application: Application
) : ViewModel(), Mvi<UiState, Intent, SideEffect> by MviDelegate(UiState()) {

    init {
        loadUserGender()
    }

    private fun loadUserGender() {
        viewModelScope.launch {
            val user = userRepository.getUser()
            val userGender = user?.gender ?: 0
            
            // Get the actual string value from the resource ID
            val isFemale = if (userGender != 0) {
                try {
                    val genderString = application.getString(userGender)
                    val femaleString = application.getString(R.string.gender_female)
                    genderString.equals(femaleString, ignoreCase = true)
                } catch (e: Exception) {
                    // Fallback to resource ID comparison
                    userGender == R.string.gender_female
                }
            } else {
                false
            }
            
            updateUiState {
                copy(agreementScreens = getBaseThreeYesList(isFemale))
            }
        }
    }

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.OnContinue -> onContinue()
        }
    }

    private fun onContinue() {
        val nextPage = uiState.value.page + 1
        if (nextPage in 0..uiState.value.agreementScreens.lastIndex) {
            updateUiState {
                copy(
                    page = nextPage
                )
            }
        } else {
            viewModelScope.launch {
                emitSideEffect(SideEffect.NavigateNext)
            }
        }
    }

}
