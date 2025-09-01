package com.fpstudio.stretchreminder.ui.composable.button

data class StretchButtonUiModel (
    val text: String,
    val isVisible: Boolean = false,
    val buttonType: ButtonType = ButtonType.DEFAULT
)

enum class ButtonType {
    DEFAULT,
    ANIMATED,
    FANCY,
}
