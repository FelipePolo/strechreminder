package com.fpstudio.stretchreminder.ui.composable.question.multiChoise

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
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
fun MultiChoiceQuestion(model: QuestionUiModel.MultiChoice, onSelected: (QuestionUiSelection.StringSelection) -> Unit) {
    Text(text = model.question, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    Spacer(modifier = Modifier.height(18.dp))
    model.options.forEach { option ->
        val backgroundColor by animateColorAsState(
            targetValue = if (model.selected.contains(option)) Green2 else Color.White,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            label = "backgroundColor"
        )
        OutlinedButton(
            onClick = {
                onSelected(QuestionUiSelection.StringSelection(option))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(vertical = 4.dp),
            border = BorderStroke(1.dp, Gray),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = backgroundColor
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(option, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}