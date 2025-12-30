package com.fpstudio.stretchreminder.ui.screen.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.util.foundation.Mvi
import com.fpstudio.stretchreminder.util.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.SideEffect
import kotlinx.coroutines.launch

class TutorialScreenViewModel : ViewModel(), Mvi<UiState, Intent, SideEffect> by MviDelegate(UiState()) {

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.StartExerciseRoutine -> startExerciseRoutine()
            is Intent.FinishExerciseRoutine -> exerciseRoutineComplete()
        }
    }

    private fun startExerciseRoutine() {
        updateUiState {
            uiState.value.copy(
                page = 1
            )
        }
    }



    private fun exerciseRoutineComplete() {
        viewModelScope.launch {
            emitSideEffect(SideEffect.NavigateNext)
        }
    }

}
