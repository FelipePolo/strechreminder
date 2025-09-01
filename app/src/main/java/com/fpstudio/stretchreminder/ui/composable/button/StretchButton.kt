package com.fpstudio.stretchreminder.ui.composable.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.button.animate.AnimatedButton
import com.fpstudio.stretchreminder.ui.composable.button.fancy.FancyAnimatedButton
import com.fpstudio.stretchreminder.ui.theme.Green2

@Composable
fun StretchButton(modifier: Modifier = Modifier, state: StretchButtonUiModel, onClick: () -> Unit) {
    if (state.isVisible) {
        when (state.buttonType) {
            ButtonType.DEFAULT -> {
                Button(
                    modifier = modifier,
                    colors = ButtonDefaults.buttonColors(containerColor = Green2),
                    shape = RoundedCornerShape(8.dp),
                    onClick = onClick
                ) {
                    Text(
                        text = state.text,
                        fontSize = 20.sp
                    )
                }
            }

            ButtonType.ANIMATED -> {
                AnimatedButton(
                    modifier = modifier,
                    colors = ButtonDefaults.buttonColors(containerColor = Green2),
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
        }
    }
}

@Preview
@Composable
fun StretchButtonPreview() {
    val state = StretchButtonUiModel("Continue", isVisible = true)
    StretchButton(state = state) {}
}