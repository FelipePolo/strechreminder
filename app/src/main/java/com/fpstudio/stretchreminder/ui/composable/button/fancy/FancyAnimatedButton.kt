package com.fpstudio.stretchreminder.ui.composable.button.fancy

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.fpstudio.stretchreminder.ui.theme.Green_secondary
import kotlinx.coroutines.delay

@Composable
fun FancyAnimatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: RoundedCornerShape,
    content: @Composable () -> Unit,
) {
    var pressed by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (pressed) Color.White else Green_secondary,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "colorAnim"
    )

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scaleAnim"
    )

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(300)
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
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = shape
    ) {
        content()
    }
}