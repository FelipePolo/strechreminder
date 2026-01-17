package com.fpstudio.stretchreminder.ui.screen.tutorial

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.Playlist
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.PreText

interface TutorialScreenContract {
    data class UiState(
        val page: Int = 0,
        val exerciseRoutineScreens: List<TutorialScreenUiModel> = getBaseExerciseRoutineList()
    )

    sealed interface Intent {
        object StartExerciseRoutine : Intent
        object FinishExerciseRoutine : Intent
    }

    sealed interface SideEffect {
        object NavigateNext : SideEffect
    }
}

private fun getBaseExerciseRoutineList(): List<TutorialScreenUiModel> {
    return listOf(
        TutorialScreenUiModel.Welcome(
            icon = R.raw.search,
            title = R.string.tutorial_welcome_title,
            description = R.string.tutorial_welcome_description,
            button = StretchButtonUiModel.Default(
                text = R.string.tutorial_button_start_exercise,
                shape = RoundedCornerShape(20.dp)
            )
        ),
        TutorialScreenUiModel.TutorialScreen(
            exerciseScreenState = UiState(
                playlist = Playlist(
                    videos = listOf("tutorial"),
                ),
                preText = PreText(
                    text = R.string.tutorial_get_ready,
                    secondsToShowPreText = 3000,
                    showPreTextForEachVideo = false,
                ),
                disclaimer = R.string.tutorial_disclaimer
            )
        )
    )
}