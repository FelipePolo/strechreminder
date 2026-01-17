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
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.customBody.CustomBodyQuestion
import com.fpstudio.stretchreminder.ui.composable.question.customGender.CustomGenderSingleChoiceQuestion
import com.fpstudio.stretchreminder.ui.composable.question.inputText.InputTextQuestion
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.SideEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun FormComponent(
    userName: String,
    questions: List<QuestionUiModel>,
    onSelect: (Int, QuestionSelectionUiModel) -> Unit,
    onDone: () -> Unit,
    onError: Flow<SideEffect>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        questions.forEachIndexed { questionIndex, question ->
            when (question) {
                is QuestionUiModel.InputText -> InputTextQuestion(
                    model = question,
                    sideEffect = onError,
                    onDone = onDone
                ) { selection ->
                    onSelect(questionIndex, selection)
                }

                is QuestionUiModel.SingleChoice -> SingleChoiceQuestion(model = question) { selection ->
                    onSelect(questionIndex, selection)
                }

                is QuestionUiModel.MultiChoice -> MultiChoiceQuestion(userName, question) { selection ->
                    onSelect(questionIndex, selection)
                }

                is QuestionUiModel.WorkDays -> WorkDaysQuestion(model = question) { selection ->
                    onSelect(questionIndex, selection)
                }

                is QuestionUiModel.TimeRange -> TimeRangeQuestion(model = question) { selection ->
                    onSelect(questionIndex, selection)
                }

                is QuestionUiModel.ImageSingleChoice -> ImageSingleChoiceQuestion(model = question) { selection ->
                    onSelect(questionIndex, selection)
                }

                is QuestionUiModel.CustomBodyQuestion -> CustomBodyQuestion(model = question) { selection ->
                    onSelect(questionIndex, selection)
                }

                is QuestionUiModel.NotificationPermission -> NotificationPermissionQuestion(model = question) { selection ->
                    onSelect(questionIndex, selection)
                }

                is QuestionUiModel.CustomGenderSingleChoice -> CustomGenderSingleChoiceQuestion(
                    model = question
                ) { selection ->
                    onSelect(questionIndex, selection)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
