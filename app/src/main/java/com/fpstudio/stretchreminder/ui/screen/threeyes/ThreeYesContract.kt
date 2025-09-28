package com.fpstudio.stretchreminder.ui.screen.threeyes

import androidx.compose.ui.text.AnnotatedString
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel

interface ThreeYesContract {

    data class UiState(
        val title: AnnotatedString,
        val backgroundImg: Int,
        val noButton: StretchButtonUiModel,
        val yesButton: StretchButtonUiModel,
    )

    sealed interface Intent

    sealed interface SideEffect
}
