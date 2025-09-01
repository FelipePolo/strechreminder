package com.fpstudio.stretchreminder.ui.component.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.composable.question.multiChoise.MultiChoiceQuestion
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.singleChoice.SingleChoiceQuestion
import com.fpstudio.stretchreminder.ui.composable.question.timeRange.TimeRangeQuestion
import com.fpstudio.stretchreminder.ui.composable.question.workDay.WorkDaysQuestion
import com.fpstudio.stretchreminder.ui.composable.question.imageSingleChoice.ImageSingleChoiceQuestion
import com.fpstudio.stretchreminder.ui.composable.question.notificationQuestion.NotificationPermissionQuestion
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiSelection

@Composable
fun FormComponent(
    questions: List<QuestionUiModel>,
    onSelect: (Int, QuestionUiSelection) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        questions.forEachIndexed { questionIndex, question ->
            when (question) {
                is QuestionUiModel.SingleChoice -> SingleChoiceQuestion(question) { selection ->
                    onSelect(questionIndex, selection)
                }
                is QuestionUiModel.MultiChoice -> MultiChoiceQuestion(question) { selection ->
                    onSelect(questionIndex, selection)
                }
                is QuestionUiModel.WorkDays -> WorkDaysQuestion(question) { selection ->
                    onSelect(questionIndex, selection)
                }
                is QuestionUiModel.TimeRange -> TimeRangeQuestion(question) { selection ->
                    onSelect(questionIndex, selection)
                }
                is QuestionUiModel.ImageSingleChoice -> ImageSingleChoiceQuestion( question) { selection ->
                    onSelect(questionIndex, selection)
                }
                is QuestionUiModel.NotificationPermission -> NotificationPermissionQuestion { selection ->
                    onSelect(questionIndex, selection)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
