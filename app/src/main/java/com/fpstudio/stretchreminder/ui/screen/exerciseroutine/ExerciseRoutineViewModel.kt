package com.fpstudio.stretchreminder.ui.screen.exerciseroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.util.foundation.Mvi
import com.fpstudio.stretchreminder.util.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.screen.exerciseroutine.ExerciseRoutineContract.UiState
import com.fpstudio.stretchreminder.ui.screen.exerciseroutine.ExerciseRoutineContract.Intent
import com.fpstudio.stretchreminder.ui.screen.exerciseroutine.ExerciseRoutineContract.SideEffect
import kotlinx.coroutines.launch

class ExerciseRoutineViewModel : ViewModel(), Mvi<UiState, Intent, SideEffect> by MviDelegate(UiState()) {

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.StartExerciseRoutine -> startExerciseRoutine()
            is Intent.FinishExerciseRoutine -> showCongratulations()
            is Intent.CongratulationsComplete -> exerciseRoutineComplete()
        }
    }

    private fun startExerciseRoutine() {
        updateUiState {
            uiState.value.copy(
                page = 1
            )
        }
    }

    private fun showCongratulations() {
        updateUiState {
            uiState.value.copy(
                page = 2
            )
        }
    }

    private fun exerciseRoutineComplete() {
        viewModelScope.launch {
            emitSideEffect(SideEffect.NavigateNext)
        }
    }

}
