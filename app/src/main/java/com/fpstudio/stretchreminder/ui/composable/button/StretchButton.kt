package com.fpstudio.stretchreminder.ui.composable.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.*
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.animate.AnimatedButton
import com.fpstudio.stretchreminder.ui.composable.button.fancy.FancyAnimatedButton

@Composable
fun StretchButton(modifier: Modifier = Modifier, state: StretchButtonUiModel, onClick: () -> Unit) {
    if (state.isVisible) {
        when (state) {
            is StretchButtonUiModel.Default -> {
                Button(
                    modifier = modifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = state.backgroundColor
                    ),
                    shape = state.shape,
                    onClick = onClick
                ) {
                    Text(
                        text = state.text,
                        color = state.textColor,
                        fontSize = state.fontSize,
                        fontWeight = state.fontWeight
                    )
                }
            }

            is StretchButtonUiModel.Animated -> {
                AnimatedButton(
                    modifier = modifier,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = state.backgroundColor
                    ),
                    shape = state.shape,
                    onClick = onClick
                ) {
                    Text(
                        text = state.text,
                        fontSize = state.fontSize
                    )
                }
            }

            is StretchButtonUiModel.Fancy -> {
                FancyAnimatedButton(
                    modifier = modifier,
                    shape = state.shape,
                    onClick = onClick
                ) {
                    Text(
                        text = state.text,
                        fontSize = state.fontSize
                    )
                }
            }

            is StretchButtonUiModel.Outline -> {
                OutlinedButton(
                    onClick = onClick,
                    modifier = modifier,
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = state.defaultElevation
                    ),
                    border = state.border,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = state.backgroundColor
                    ),
                    shape = state.shape
                ) {
                    Text(
                        state.text,
                        color = state.textColor,
                        fontSize = state.fontSize,
                        fontWeight = state.fontWeight,
                        modifier = Modifier.padding(
                            state.textPaddingValues
                        ),
                    )
                }
            }

            is StretchButtonUiModel.Lottie -> {
                val composition by rememberLottieComposition(
                    RawRes(state.lottieFile)
                )

                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = state.iterations
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
    val state = StretchButtonUiModel.Default("Continue", isVisible = true)
    StretchButton(state = state) {}
}