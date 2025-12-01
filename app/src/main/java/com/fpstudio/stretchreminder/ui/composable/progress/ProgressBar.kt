package com.fpstudio.stretchreminder.ui.composable.progress

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.theme.Gray5
import kotlinx.coroutines.delay

@Composable
fun ProgressBar(
    total: Float,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val progressValue = if (total == 0f) 0f else progress / total

    // Animación suave del progreso
    val animatedProgress by animateFloatAsState(
        targetValue = progressValue,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing),
        label = "progressAnimation"
    )

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF4ABDAC))
        )
    }
}

@Preview
@Composable
fun PreviewQuestionProgressBar() {
    var progress by remember { mutableFloatStateOf(1F) }

    ProgressBar(
        total = 7F,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(18.dp)
            .clip(RoundedCornerShape(50))
            .background(Gray5),
    )

    LaunchedEffect(Unit) {
        while (true) {
            if (progress >= 7F) {
                progress = 0F
            } else {
                delay(100)
                progress += 0.1F
            }
        }
    }
}