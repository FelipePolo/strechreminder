package com.fpstudio.stretchreminder.ui.composable.questionProgressBar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.theme.Gray5

@Composable
fun QuestionProgressBar(
    currentQuestion: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier,
    backButton: StretchButtonUiModel,
    visibility: Boolean,
    onBackClick: () -> Unit,
) {
    val progress = (currentQuestion.toFloat() - 1) / totalQuestions.toFloat()

    // Animación suave del progreso
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing),
        label = "progressAnimation"
    )

    // Animación suave del tamaño del botón
    val buttonSize by animateDpAsState(
        targetValue = if (backButton.isVisible) 42.dp else 0.dp,
        animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing),
        label = "buttonSizeAnimation"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {
        if (visibility) {
            Text(
                text = "Question $currentQuestion of $totalQuestions",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically) {
                StretchButton(
                    state = backButton,
                    modifier = Modifier
                        .size(buttonSize)
                        .rotate(90f),
                    onClick = onBackClick
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .height(18.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Gray5)
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuestionProgressBar() {
    var question by remember { mutableIntStateOf(1) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        QuestionProgressBar(
            currentQuestion = question,
            totalQuestions = 7,
            modifier = Modifier.fillMaxWidth(),
            visibility = true,
            backButton = StretchButtonUiModel.Lottie(
                iterations = 3
            )
        ) {}

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (question < 7) question++
        }) {
            Text("Next Question")
        }
    }
}