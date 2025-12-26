package com.fpstudio.stretchreminder.ui.screen.exerciseroutine

import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.congratulation.CongratulationUiModel
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract

sealed class ExerciseRoutineUiModel {
    data class Welcome(
        val icon: Int,
        val title: String,
        val description: String,
        val button: StretchButtonUiModel
    ) : ExerciseRoutineUiModel()

    data class ExerciseRoutine(
        val exerciseScreenState: ExerciseScreenContract.UiState,
    ) : ExerciseRoutineUiModel()

    data class Complete(
        val congrats: CongratulationUiModel = CongratulationUiModel()
    ) : ExerciseRoutineUiModel()

}
