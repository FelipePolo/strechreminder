package com.fpstudio.stretchreminder.ui.screen.promises.madeforyou

import androidx.compose.ui.graphics.Color
import com.fpstudio.stretchreminder.ui.composable.button.ButtonType
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel

data class MadeForYouUiModel(
    val isVisible: Boolean = false,
    val title: String = "Stretch Reminder\nwas made for you.",
    val subtitle: String = "We've helped others just like you feel more flexible, energized, and take back control of their health.",
    val showBackButton: Boolean = true,
    val card1: MadeForYouCard = MadeForYouCard(
        icon = "⏱️",
        title = "Build healthy work breaks",
        description = "Smart reminders and 2-5 min breaks that won't interrupt your flow."
    ),
    val card2: MadeForYouCard = MadeForYouCard(
        icon = "🤸",
        title = "Improve posture",
        description = "Short routines to correct slouching, tense neck and lower back strain."
    ),
    val card3: MadeForYouCard = MadeForYouCard(
        icon = "🧘",
        title = "Reduce muscle tension",
        description = "Relieve stiffness with guided micro-stretches you can do in minutes."
    ),
    val nextButton: StretchButtonUiModel = StretchButtonUiModel(
        text = "Continue",
        isVisible = true,
        buttonType = ButtonType.OUTLINE,
        backgroundColor = Color.White
    )
)

data class MadeForYouCard(
    val icon: String,
    val title: String,
    val description: String,
)
