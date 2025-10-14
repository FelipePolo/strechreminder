package com.fpstudio.stretchreminder.ui.screen.tutorial

import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.congratulation.CongratulationUiModel

interface TutorialScreenContract {
    data class UiState(
        val page: Int = 0,
        val tutorialScreens: List<TutorialUiModel> = getBaseTutorialList()
    )

    sealed interface Intent {
        object StartTutorial : Intent
    }

    sealed interface SideEffect {

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
            )
        ),
        TutorialUiModel.Tutorial(
            disclaimer = "If you experience pain or discomfort while exercising, please stop immediately and consult your doctor or qualified healthcare",
            video = 0
        ),
        TutorialUiModel.Complete(
            congrats = CongratulationUiModel()
        )
    )
}