package com.fpstudio.stretchreminder.ui.screen.tutorial

import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.congratulation.CongratulationUiModel
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract

sealed class TutorialUiModel {
    data class Welcome(
        val icon: Int,
        val title: String,
        val description: String,
        val button: StretchButtonUiModel
    ) : TutorialUiModel()

    data class Tutorial(
        val exerciseScreenState: ExerciseScreenContract.UiState,
    ) : TutorialUiModel()

    data class Complete(
        val congrats: CongratulationUiModel = CongratulationUiModel()
    ) : TutorialUiModel()

}
