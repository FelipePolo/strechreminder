package com.fpstudio.stretchreminder.ui.composable.transitions.splash

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun LiquidSplashOverlay(
    modifier: Modifier = Modifier,
    visible: Boolean,
    content: @Composable () -> Unit
) {
    // Animación del radio circular
    val transition = updateTransition(targetState = visible, label = "LiquidSplashTransition")

    val scale by transition.animateFloat(
        transitionSpec = {
            if (targetState) spring(dampingRatio = 0.5f, stiffness = 200f)
            else tween(durationMillis = 300, easing = LinearOutSlowInEasing)
        }, label = "scaleAnim"
    ) { state ->
        if (state) 1f else 0f
    }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) },
        label = "alphaAnim"
    ) { state ->
        if (state) 1f else 0f
    }

    if (alpha > 0f) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = 0.50f
                    translationY = 0.50f
                    this.alpha = alpha
                    shadowElevation = 20f
                    shape = RoundedCornerShape(40)
                    clip = false
                }
        ) {
            content()
        }
    }
}
