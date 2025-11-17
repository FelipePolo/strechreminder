package com.fpstudio.stretchreminder.ui.screen.tutorial

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.congratulation.CongratulationUiModel
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.Playlist
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.PreText

interface TutorialScreenContract {
    data class UiState(
        val page: Int = 0,
        val tutorialScreens: List<TutorialUiModel> = getBaseTutorialList()
    )

    sealed interface Intent {
        object StartTutorial : Intent
        object FinishTutorial : Intent
        object CongratulationsComplete : Intent
    }

    sealed interface SideEffect {
        object NavigateNext : SideEffect
    }
}

private fun getBaseTutorialList(): List<TutorialUiModel> {
    return listOf(
        TutorialUiModel.Welcome(
            icon = R.raw.search,
            title = "No Stretches logged yet.",
            description = "This short tutorial will help you understand how stretch sessions work--simple, guided, and easy to follow.",
            button = StretchButtonUiModel.Default(
                text = "Start Tutorial",
                shape = RoundedCornerShape(20.dp)
            )
        ),
        TutorialUiModel.Tutorial(
            exerciseScreenState = UiState(
                playlist = Playlist(
                    videos = listOf("tutorial"),
                ),
                preText = PreText(
                    text = "Get Ready",
                    secondsToShowPreText = 3000,
                    showPreTextForEachVideo = false,
                ),
                disclaimer = "If your experience pain or discomfort while exercising, please stop immediately and consult your doctor or qualified healthcare professional before continuing."
            )
        ),
        TutorialUiModel.Complete(
            congrats = CongratulationUiModel()
        )
    )
}