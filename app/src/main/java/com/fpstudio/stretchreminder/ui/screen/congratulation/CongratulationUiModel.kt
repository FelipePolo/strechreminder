package com.fpstudio.stretchreminder.ui.screen.congratulation

data class CongratulationUiModel(
    val visible: Boolean = false,
    val title: String = "Congratulations on Taking the First Step!",
    val subtitle: String = "Your future self will thank you — let's keep growing together.",
    val animation: String = "congratulations.json"
)
