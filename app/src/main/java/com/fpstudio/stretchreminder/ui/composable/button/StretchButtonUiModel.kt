package com.fpstudio.stretchreminder.ui.composable.button

import androidx.compose.ui.graphics.Color

data class StretchButtonUiModel (
    val text: String,
    val isVisible: Boolean = false,
    val buttonType: ButtonType = ButtonType.DEFAULT,
    val backgroundColor: Color = Color.Unspecified,
    val borderColor: Color = Color.Unspecified
)

enum class ButtonType {
    DEFAULT,
    ANIMATED,
    FANCY,
    OUTLINE,

    LOTTIE_BACK
}
