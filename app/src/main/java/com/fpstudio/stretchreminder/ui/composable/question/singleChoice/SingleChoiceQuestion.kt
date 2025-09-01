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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiSelection
import com.fpstudio.stretchreminder.ui.theme.Green2
import com.fpstudio.stretchreminder.ui.theme.Gray

@Composable
fun SingleChoiceQuestion(model: QuestionUiModel.SingleChoice, onSelect: (QuestionUiSelection.StringSelection) -> Unit) {
    Text(text = model.question, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Spacer(modifier = Modifier.height(18.dp))
    model.options.forEach { option ->
        val backgroundColor by animateColorAsState(
            targetValue = if (model.selected == option) Green2 else Color.White,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            label = "backgroundColor"
        )
        OutlinedButton(
            onClick = {
                onSelect(QuestionUiSelection.StringSelection(option))
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
            Text(option, color = Color.Black, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}