package com.fpstudio.stretchreminder.ui.screen.promises.plansuccess

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel

data class PlanSuccessUiModel(
    val isVisible: Boolean = false,
    val title: String = "Great! Your plan adapts to your level!",
    val description: String = "Plus, we tailor the program to your body's changes to ensure you gain flexibility in an easy, enjoyable way.",
    val imageResId: Int = R.drawable.male,
    val nextButton: StretchButtonUiModel = StretchButtonUiModel.Default(
        text = "Continue",
        backgroundColor = Color.White,
        fontSize = 16.sp,
        textColor = Color.Black,
    ),
    val backButton: StretchButtonUiModel = StretchButtonUiModel.Lottie(
        iterations = 3,
    )
)
