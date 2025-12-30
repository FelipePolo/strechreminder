package com.fpstudio.stretchreminder.ui.screen.tutorial

import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract

sealed class TutorialScreenUiModel {
    data class Welcome(
        val icon: Int,
        val title: String,
        val description: String,
        val button: StretchButtonUiModel
    ) : TutorialScreenUiModel()

    data class TutorialScreen(
        val exerciseScreenState: ExerciseScreenContract.UiState,
    ) : TutorialScreenUiModel()
}
