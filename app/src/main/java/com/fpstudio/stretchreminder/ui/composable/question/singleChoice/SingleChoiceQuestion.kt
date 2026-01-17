package com.fpstudio.stretchreminder.ui.composable.question.singleChoice

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.theme.Gray
import com.fpstudio.stretchreminder.ui.theme.Green_secondary

@Composable
fun SingleChoiceQuestion(model: QuestionUiModel.SingleChoice, onSelect: (QuestionSelectionUiModel.IntSelectionUiModel) -> Unit) {
    // Question title
    Spacer(modifier = Modifier.height(12.dp))
    QuestionTitle(model)
    Spacer(modifier = Modifier.height(18.dp))

    // Content
    // Content
    model.options.forEachIndexed { index, option ->
        val text = androidx.compose.ui.res.stringResource(option)
        val backgroundColor by animateColorAsState(
            targetValue = if (model.selected == option) Green_secondary else Color.White,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            label = "backgroundColor"
        )
        OutlinedButton(
            onClick = {
                onSelect(QuestionSelectionUiModel.IntSelectionUiModel(option))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(vertical = 4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = backgroundColor,
            ),
            border = BorderStroke(1.dp, Gray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text, color = Color.Black, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}