package com.fpstudio.stretchreminder.ui.composable.question.multiChoise

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.theme.Gray
import com.fpstudio.stretchreminder.ui.theme.Green_secondary
import com.fpstudio.stretchreminder.ui.theme.Green_tertiary
import com.fpstudio.stretchreminder.util.Constants.EMPTY

@Composable
fun MultiChoiceQuestion(
    userName: String = EMPTY,
    model: QuestionUiModel.MultiChoice,
    onSelected: (QuestionSelectionUiModel.CustomUserAchivementUiModel) -> Unit
) {
    // Question title
    Spacer(modifier = Modifier.height(12.dp))
    val subtitle1 = model.subtitle1?.let { 
        androidx.compose.ui.res.stringResource(it).replace("{USER_NAME}", userName) 
    }

    QuestionTitle(
        model = model,
        userName = userName
    )
    Spacer(modifier = Modifier.height(18.dp))

    // Content
    LazyColumn {
        items(items = model.options) { option ->
    val title = androidx.compose.ui.res.stringResource(option.title)
    
    val backgroundColor by animateColorAsState(
                targetValue = if (model.selected.contains(option)) Green_tertiary else Color.White,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                label = "backgroundColor"
            )
            val borderColor by animateColorAsState(
                targetValue = if (model.selected.contains(option)) Green_secondary else Gray,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                label = "borderColor"
            )
            OutlinedButton(
                onClick = {
                    onSelected(QuestionSelectionUiModel.CustomUserAchivementUiModel(option))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 4.dp),
                border = BorderStroke(2.dp, borderColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = backgroundColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "${option.iconStr}  ${title}",
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}