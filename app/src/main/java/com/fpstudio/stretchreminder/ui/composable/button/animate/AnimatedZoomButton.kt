package com.fpstudio.stretchreminder.ui.composable.button.animate

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

@Composable
fun AnimatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: ButtonColors,
    shape: RoundedCornerShape,
    content: @Composable () -> Unit,
) {
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing),
        label = "scaleAnim"
    )

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(150)
            pressed = false
            onClick()
        }
    }
    Button(
        onClick = {
            pressed = true
        },
        modifier = modifier.graphicsLayer(
            scaleX = scale,
            scaleY = scale
        ),
        colors = colors,
        shape = shape
    ) {
        content()
    }
}