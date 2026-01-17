package com.fpstudio.stretchreminder.ui.screen.promises.plansuccess

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel

data class PlanSuccessUiModel(
    val isVisible: Boolean = false,
    val title: Int = R.string.promises_plan_success_title,
    val description: Int = R.string.promises_plan_success_description,
    val imageResId: Int = R.drawable.male,
    val nextButton: StretchButtonUiModel = StretchButtonUiModel.Default(
        text = R.string.common_button_continue,
        backgroundColor = Color.White,
        fontSize = 16.sp,
        textColor = Color.Black,
    ),
    val backButton: StretchButtonUiModel = StretchButtonUiModel.Lottie(
        iterations = 3,
    )
)
