package com.fpstudio.stretchreminder.ui.screen.threeyes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.foundation.Mvi
import com.fpstudio.stretchreminder.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.UiState
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.Intent
import kotlinx.coroutines.launch

class ThreeYesViewModel : ViewModel(), Mvi<UiState, Intent, SideEffect> by MviDelegate(UiState()) {

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
