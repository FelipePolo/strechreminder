package com.fpstudio.stretchreminder.data.mapper

import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.ui.composable.question.QuestionID
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.util.Constants.EMPTY

fun FormScreenContract.UiState.toUser(): User {
    val answers = form.flatMap { it.questions }
    var name = EMPTY
    var gender = EMPTY
    var age = EMPTY
    var mainPosture = 0
    var workDays: List<String> = emptyList()
    var achievement: List<String> = emptyList()
    var startWorkTime = 0L
    var endWorkTime = 0L
    var bodyParts: List<String> = emptyList()
    var frequency = 0
    var notificationPermission = false

    answers.forEach { question ->
        when (question) {
            is QuestionUiModel.InputText -> {
                if (question.id == QuestionID.NAME) name = question.selected
            }

            is QuestionUiModel.SingleChoice -> {
                if (question.id == QuestionID.FREQUENCY) frequency =
                    question.options.indexOf(question.selected)
                if (question.id == QuestionID.AGE) age = question.selected
            }

            is QuestionUiModel.MultiChoice -> {
                if (question.id == QuestionID.ACHIEVEMENT) achievement = question.selected
            }

            is QuestionUiModel.WorkDays -> {
                if (question.id == QuestionID.WORK_DAYS) workDays = question.selected
            }

            is QuestionUiModel.TimeRange -> {
                if (question.id == QuestionID.WORK_HOURS) {
                    startWorkTime = question.startTime.timeInMillis
                    endWorkTime = question.endTime.timeInMillis
                }
            }

            is QuestionUiModel.CustomBodyQuestion -> {
                bodyParts = question.selected
            }

            is QuestionUiModel.CustomGenderSingleChoice -> {
                if (question.id == QuestionID.GENDER) gender = question.selected
            }

            is QuestionUiModel.ImageSingleChoice -> {
                if (question.id == QuestionID.MAIN_POSTURE) mainPosture = question.selected
            }

            is QuestionUiModel.NotificationPermission -> {
                notificationPermission = question.answered
            }
        }
    }
    return User(
        name = name,
        lastFormPage = this.page,
        gender = gender,
        age = age,
        mainPosture = mainPosture,
        workDays = workDays,
        achievement = achievement,
        startTime = startWorkTime,
        endTime = endWorkTime,
        bodyParts = bodyParts,
        frequency = frequency,
        notificationPermission = notificationPermission
    )
}
