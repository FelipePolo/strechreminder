package com.fpstudio.stretchreminder.ui.composable.transitions.smooth

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun SmoothOverlay(
    modifier: Modifier = Modifier,
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val transition = updateTransition(targetState = visible, label = "SmoothOverlayTransition")

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600, easing = FastOutSlowInEasing) },
        label = "alphaAnim"
    ) { state -> if (state) 1f else 0f }

    val scale by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600, easing = FastOutSlowInEasing) },
        label = "scaleAnim"
    ) { state -> if (state) 1f else 0.9f } // entra un poco reducido

    if (alpha > 0f) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .graphicsLayer {
                    this.alpha = alpha
                    scaleX = scale
                    scaleY = scale
                }
        ) {
            content()
        }
    }
}
