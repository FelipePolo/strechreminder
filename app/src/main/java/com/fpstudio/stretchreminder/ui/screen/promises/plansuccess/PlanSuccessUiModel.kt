package com.fpstudio.stretchreminder.ui.screen.promises.plansuccess

import androidx.compose.ui.graphics.Color
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.ButtonType
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel

data class PlanSuccessUiModel(
    val isVisible: Boolean = false,
    val title: String = "Great! Your plan adapts to your level!",
    val description: String = "Plus, we tailor the program to your body's changes to ensure you gain flexibility in an easy, enjoyable way.",
    val imageResId: Int = R.drawable.male,
    val nextButton: StretchButtonUiModel = StretchButtonUiModel(
        text = "Continue",
        isVisible = true,
        buttonType = ButtonType.OUTLINE,
        backgroundColor = Color.White
    )
)
