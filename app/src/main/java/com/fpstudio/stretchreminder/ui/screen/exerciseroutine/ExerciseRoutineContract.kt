package com.fpstudio.stretchreminder.ui.screen.exerciseroutine

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.congratulation.CongratulationUiModel
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.Playlist
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.PreText

interface ExerciseRoutineContract {
    data class UiState(
        val page: Int = 0,
        val exerciseRoutineScreens: List<ExerciseRoutineUiModel> = getBaseExerciseRoutineList()
    )

    sealed interface Intent {
        object StartExerciseRoutine : Intent
        object FinishExerciseRoutine : Intent
        object CongratulationsComplete : Intent
    }

    sealed interface SideEffect {
        object NavigateNext : SideEffect
    }
}

private fun getBaseExerciseRoutineList(): List<ExerciseRoutineUiModel> {
    return listOf(
        ExerciseRoutineUiModel.Welcome(
            icon = R.raw.search,
            title = "No Stretches logged yet.",
            description = "This short exercise will help you understand how stretch sessions work--simple, guided, and easy to follow.",
            button = StretchButtonUiModel.Default(
                text = "Start Exercise",
                shape = RoundedCornerShape(20.dp)
            )
        ),
        ExerciseRoutineUiModel.ExerciseRoutine(
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
        ExerciseRoutineUiModel.Complete(
            congrats = CongratulationUiModel()
        )
    )
}