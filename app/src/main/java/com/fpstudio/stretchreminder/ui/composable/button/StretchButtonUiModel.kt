package com.fpstudio.stretchreminder.ui.composable.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieConstants
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.theme.Green_secondary

sealed class StretchButtonUiModel(
    open var isVisible: Boolean,
) {
    data class Default(
        val text: String,
        override var isVisible: Boolean = true,
        val backgroundColor: Color = Green_secondary,
        val shape: RoundedCornerShape = RoundedCornerShape(8.dp),
        val textColor: Color = Color.White,
        val fontWeight: FontWeight = FontWeight.SemiBold,
        val fontSize: TextUnit = 20.sp
    ) : StretchButtonUiModel(isVisible)

    data class Animated(
        val text: String,
        override var isVisible: Boolean = true,
        val backgroundColor: Color = Green_secondary,
        val shape: RoundedCornerShape = RoundedCornerShape(8.dp),
        val fontSize: TextUnit = 20.sp,
        val fontWeight: FontWeight = FontWeight.Bold
    ) : StretchButtonUiModel(isVisible)

    data class Fancy(
        val text: String,
        override var isVisible: Boolean = true,
        val backgroundColor: Color = Green_secondary,
        val shape: RoundedCornerShape = RoundedCornerShape(8.dp),
        val fontSize: TextUnit = 20.sp
    ) : StretchButtonUiModel(isVisible)

    data class Outline(
        val text: String,
        override var isVisible: Boolean = true,
        val defaultElevation: Dp = 6.dp,
        val backgroundColor: Color = Color.Transparent,
        val border: BorderStroke = BorderStroke(1.dp, Color.White),
        val shape: RoundedCornerShape = RoundedCornerShape(18.dp),
        val fontSize: TextUnit = 16.sp,
        val fontWeight: FontWeight = FontWeight.Bold,
        val textPaddingValues: PaddingValues = PaddingValues(vertical = 8.dp),
        val textColor: Color = Color.White,
    ) : StretchButtonUiModel(isVisible)

    data class Lottie(
        override var isVisible: Boolean = true,
        val lottieFile: Int = R.raw.back,
        val iterations: Int = LottieConstants.IterateForever,
    ) : StretchButtonUiModel(isVisible)

}
