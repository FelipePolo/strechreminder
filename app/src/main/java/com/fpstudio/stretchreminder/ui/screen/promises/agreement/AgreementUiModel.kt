package com.fpstudio.stretchreminder.ui.screen.promises.agreement

import androidx.compose.ui.text.AnnotatedString
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel

data class AgreementUiModel(
    val title: AnnotatedString,
    val backgroundImg: Int,
    val noButton: StretchButtonUiModel,
    val yesButton: StretchButtonUiModel,
)
