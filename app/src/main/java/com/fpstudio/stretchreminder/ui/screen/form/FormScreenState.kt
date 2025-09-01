package com.fpstudio.stretchreminder.ui.screen.form

import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.component.form.FormUiModel
import com.fpstudio.stretchreminder.ui.composable.button.ButtonType
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.congratulation.CongratulationUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel

data class FormScreenState(
    val page: Int = 0,
    val form: List<FormUiModel> = getForms(),
    val button: StretchButtonUiModel = StretchButtonUiModel(text = "Continue", buttonType = ButtonType.ANIMATED),
    val shouldRequestNotificationPermission: Boolean = false,
    val shouldShowQuestionProgressBar: Boolean = true,
    val congratulation: CongratulationUiModel = CongratulationUiModel()
)

fun getForms(): List<FormUiModel> {
    return listOf(
        FormUiModel(
            questions = listOf(
                QuestionUiModel.SingleChoice(
                    question = "What is your age?",
                    options = listOf("18 - 35", "36 - 45", "46 - 55", "56 - 69", "Over 70")
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.SingleChoice(
                    question = "What is your gender?",
                    options = listOf("Male", "Female", "Other")
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.WorkDays(
                    question = "Select your work days",
                    selected = listOf("Mon", "Tue", "Wed", "Thu", "Fri"),
                    days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                ),
                QuestionUiModel.TimeRange(
                    question = "Working Hours"
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.ImageSingleChoice(
                    question = "Select the main posture during your workday",
                    imagesResId = listOf(
                        R.drawable.standing,
                        R.drawable.sitting,
                        R.drawable.laying_down
                    )
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.NotificationPermission
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.SingleChoice(
                    question = "How often do you stretch during your workday?",
                    options = listOf(
                        "Never",
                        "Once a day",
                        "Twice a day",
                        "Three times a day",
                        "Four or more times a day"
                    )
                )
            )
        ),
        FormUiModel(
            questions = listOf(
                QuestionUiModel.MultiChoice(
                    question = "Do you have any of the following health conditions?",
                    selected = listOf("Nothing"),
                    nothingOption = true,
                    options = listOf(
                        "Nothing",
                        "Joint pain",
                        "Pregnancy",
                        "Arthritis",
                        "Cardiovascular conditions",
                        "Diabetes",
                        "Neuropathy",
                        "Recent surgery",
                        "Proprioception issues"
                    )
                )
            )
        )
    )
}
