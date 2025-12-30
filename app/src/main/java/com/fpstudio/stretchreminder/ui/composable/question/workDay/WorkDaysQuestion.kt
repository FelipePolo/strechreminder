package com.fpstudio.stretchreminder.ui.composable.question.workDay

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.theme.Green_primary
import com.fpstudio.stretchreminder.ui.theme.Gray
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.theme.Green_secondary

@Composable
fun WorkDaysQuestion(model: QuestionUiModel.WorkDays, onAnswer: (QuestionSelectionUiModel.StringSelectionUiModel) -> Unit) {
    // Question title
    Spacer(modifier = Modifier.height(12.dp))
    QuestionTitle(model)
    Spacer(modifier = Modifier.height(16.dp))

    // Content
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        model.days.forEach { day ->
            val selected = model.selected.contains(day)
            val backgroundColor by animateColorAsState(
                targetValue = if (selected) Green_secondary else Green_primary,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                label = "backgroundColor"
            )
            Column(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onAnswer(QuestionSelectionUiModel.StringSelectionUiModel(day))
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .background(
                            color = backgroundColor,
                            shape = CircleShape
                        )
                ) {
                    if (selected) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center),
                            painter = painterResource(id = R.drawable.ic_check),
                            contentDescription = "Check",
                            tint = Color.White
                        )
                    }
                }
                Text(
                    day,
                    color = if (selected) Green_primary else Gray
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}