package com.fpstudio.stretchreminder.ui.screen.promises.madeforyou

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel

data class MadeForYouUiModel(
    val isVisible: Boolean = false,
    val title: Int = R.string.promises_made_for_you_title,
    val subtitle: Int = R.string.promises_made_for_you_subtitle,
    val showBackButton: Boolean = true,
    val card1: MadeForYouCard = MadeForYouCard(
        icon = R.string.promises_made_for_you_card1_icon,
        title = R.string.promises_made_for_you_card1_title,
        description = R.string.promises_made_for_you_card1_description
    ),
    val card2: MadeForYouCard = MadeForYouCard(
        icon = R.string.promises_made_for_you_card2_icon,
        title = R.string.promises_made_for_you_card2_title,
        description = R.string.promises_made_for_you_card2_description
    ),
    val card3: MadeForYouCard = MadeForYouCard(
        icon = R.string.promises_made_for_you_card3_icon,
        title = R.string.promises_made_for_you_card3_title,
        description = R.string.promises_made_for_you_card3_description
    ),
    val nextButton: StretchButtonUiModel = StretchButtonUiModel.Outline(
        text = R.string.common_button_continue,
        backgroundColor = Color.White,
        fontSize = 16.sp,
        textColor = Color.Black,
    ),
    val backButton: StretchButtonUiModel = StretchButtonUiModel.Lottie(
        lottieFile = R.raw.back_white,
        iterations = 3,
    )
)

data class MadeForYouCard(
    val icon: Int,
    val title: Int,
    val description: Int,
    val backgroundColor: Color = Color.White
)
