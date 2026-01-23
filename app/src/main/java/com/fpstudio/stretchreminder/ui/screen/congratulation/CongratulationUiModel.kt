package com.fpstudio.stretchreminder.ui.screen.congratulation

data class CongratulationUiModel(
    val visible: Boolean = false,
    @androidx.annotation.StringRes val titleRes: Int = com.fpstudio.stretchreminder.R.string.congratulation_title,
    @androidx.annotation.StringRes val subtitleRes: Int = com.fpstudio.stretchreminder.R.string.congratulation_subtitle,
    val animation: String = "congratulations.json"
)
