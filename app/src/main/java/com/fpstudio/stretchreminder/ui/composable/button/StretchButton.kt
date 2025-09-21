package com.fpstudio.stretchreminder.ui.composable.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.animate.AnimatedButton
import com.fpstudio.stretchreminder.ui.composable.button.fancy.FancyAnimatedButton
import com.fpstudio.stretchreminder.ui.theme.Green_secondary

@Composable
fun StretchButton(modifier: Modifier = Modifier, state: StretchButtonUiModel, onClick: () -> Unit) {
    if (state.isVisible) {
        when (state.buttonType) {
            ButtonType.DEFAULT -> {
                Button(
                    modifier = modifier,
                    colors = ButtonDefaults.buttonColors(containerColor = Green_secondary),
                    shape = RoundedCornerShape(8.dp),
                    onClick = onClick
                ) {
                    Text(
                        text = state.text,
                        fontSize = 20.sp
                    )
                }
            }

            ButtonType.OUTLINE -> {
                OutlinedButton(
                    onClick = onClick,
                    modifier = modifier,
                    elevation = ButtonDefaults.elevatedButtonElevation( defaultElevation = 6.dp),
                    border = BorderStroke(1.dp, state.borderColor),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = state.backgroundColor
                    ),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        state.text,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            }

            ButtonType.ANIMATED -> {
                AnimatedButton(
                    modifier = modifier,
                    colors = ButtonDefaults.buttonColors(containerColor = Green_secondary),
                    shape = RoundedCornerShape(8.dp),
                    onClick = onClick
                ) {
                    Text(
                        text = state.text,
                        fontSize = 20.sp
                    )
                }
            }

            ButtonType.FANCY -> {
                FancyAnimatedButton(
                    modifier = modifier,
                    shape = RoundedCornerShape(8.dp),
                    onClick = onClick
                ) {
                    Text(
                        text = state.text,
                        fontSize = 20.sp
                    )
                }
            }

            ButtonType.LOTTIE_BACK -> {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.back)
                )

                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = 3
                )
                LottieAnimation(
                    composition = composition,
                    contentScale = ContentScale.FillBounds,
                    progress = { progress },
                    modifier = modifier
                        .clickable(onClick = onClick)
                )
            }
        }
    }
}

@Preview
@Composable
fun StretchButtonPreview() {
    val state = StretchButtonUiModel("Continue", isVisible = true)
    StretchButton(state = state) {}
}