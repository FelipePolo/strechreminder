package com.fpstudio.stretchreminder.ui.composable.question.customBody

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.theme.Gray
import com.fpstudio.stretchreminder.ui.theme.Green_secondary
import com.fpstudio.stretchreminder.ui.theme.Green_tertiary

@Composable
fun CustomBodyQuestion(
    model: QuestionUiModel.CustomBodyQuestion,
    onSelect: (QuestionSelectionUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Question title
        Spacer(modifier = Modifier.height(12.dp))
        QuestionTitle(model = model)
        Spacer(modifier = Modifier.height(24.dp))

        // Body diagram with selectable parts
        Box(modifier = Modifier.fillMaxWidth()) {
            BodyDiagramBackground(model = model)
            BodyPartButtons(
                bodyPartButtons = model.options.map { it.second },
                selectedBodyParts = model.selected,
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onSelect = {
                    onSelect(QuestionSelectionUiModel.StringSelectionUiModel(it))
                }
            )
        }
    }
}

@Composable
fun BodyPartButtons(
    modifier: Modifier,
    bodyPartButtons: List<String>,
    selectedBodyParts: List<String>,
    onSelect: (String) -> Unit = {}
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        bodyPartButtons.forEach { bodyPart ->
            val backgroundColor by animateColorAsState(
                targetValue = if (selectedBodyParts.contains(bodyPart)) Green_tertiary else Color.White,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                label = "backgroundColor"
            )
            val borderColor by animateColorAsState(
                targetValue = if (selectedBodyParts.contains(bodyPart)) Green_secondary else Gray,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                label = "borderColor"
            )
            OutlinedButton(
                onClick = {
                    onSelect(bodyPart)
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(60.dp)
                    .padding(vertical = 4.dp),
                border = BorderStroke(2.dp, borderColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = backgroundColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    bodyPart,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun BodyDiagramBackground(
    baseBodyImage: Int = R.drawable.unselected,
    model: QuestionUiModel.CustomBodyQuestion,
) {
    Box(modifier = Modifier) {
        Image(
            painter = painterResource(id = baseBodyImage),
            contentDescription = null,
            modifier = Modifier
                .align(alignment = Alignment.TopStart),
            contentScale = ContentScale.FillWidth
        )
        model.options.forEach { option ->
            AnimatedVisibility(
                visible = model.selected.contains(option.second),
                enter = fadeIn(animationSpec = tween(durationMillis = 600)) +
                        slideInVertically(
                            initialOffsetY = { -120 },
                            animationSpec = tween(durationMillis = 600)
                        ),
                exit = fadeOut(animationSpec = tween(durationMillis = 600)) +
                        slideOutVertically(
                            targetOffsetY = { -120 },
                            animationSpec = tween(durationMillis = 600)
                        )
            ) {
                Image(
                    painter = painterResource(id = option.first),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopStart),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomBodyQuestionPreview() {
    
    val bodyParts = listOf(
        Pair(R.drawable.selected_neck, "Cuello"),
        Pair(R.drawable.selected_shoulder, "Hombros"),
        Pair(R.drawable.selected_arms, "Brazos"),
        Pair(R.drawable.selected_trapezoids, "Trapecios"),
        Pair(R.drawable.selected_lower_back, "Espalda baja"),
        Pair(R.drawable.selected_hands, "manos"),
        Pair(R.drawable.selected_hips, "Caderas"),
        Pair(R.drawable.selected_legs, "Piernas"),
        Pair(R.drawable.selected_all, "Cuerpo")
    )

    val question = QuestionUiModel.CustomBodyQuestion(
        subtitle1 = "Selecciona las áreas",
        question = "¿Qué partes del cuerpo quieres ejercitar?",
        options = bodyParts,
        selected = listOf("Caderas", "Trapecios", "Cuello", "manos","Espalda baja")
    )

    CustomBodyQuestion(
        model = question,
        onSelect = {}
    )
}