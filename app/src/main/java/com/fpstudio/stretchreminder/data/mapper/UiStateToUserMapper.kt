package com.fpstudio.stretchreminder.data.mapper

import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import java.util.Calendar

fun FormScreenContract.UiState.toUser(): User {
    val answers = form.flatMap { it.questions }
    var name = ""
    var gender = ""
    var age = ""
    var mainPosture = ""
    var workDays: List<String> = emptyList()
    var startTime = ""
    var endTime = ""
    var notificationPermission = false

    answers.forEach { question ->
        when (question) {
            is QuestionUiModel.InputText -> {
                if (question.question.contains("nombre", true)) name = question.selected
                if (question.question.contains("edad", true)) age = question.selected
            }
            is QuestionUiModel.SingleChoice -> {
                if (question.question.contains("género", true) || question.question.contains("genero", true)) gender = question.selected
                if (question.question.contains("postura", true)) mainPosture = question.selected
            }
            is QuestionUiModel.MultiChoice -> {
                if (question.question.contains("días", true) || question.question.contains("dias", true)) workDays = question.selected
            }
            is QuestionUiModel.WorkDays -> {
                workDays = question.selected
            }
            is QuestionUiModel.TimeRange -> {
                startTime = "${question.startTime.get(Calendar.HOUR_OF_DAY)}:${question.startTime.get(Calendar.MINUTE)}"
                endTime = "${question.endTime.get(Calendar.HOUR_OF_DAY)}:${question.endTime.get(Calendar.MINUTE)}"
            }
            else -> {}
        }
    }
    // Si tienes una pregunta específica para notificaciones, aquí puedes mapearla
    // notificationPermission = ...
    return User(
        name = name,
        gender = gender,
        age = age,
        mainPosture = mainPosture,
        workDays = workDays,
        startTime = startTime,
        endTime = endTime,
        notificationPermission = notificationPermission
    )
}
