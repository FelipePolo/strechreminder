package com.fpstudio.stretchreminder.data.mapper

import androidx.compose.material3.Text
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.data.model.UserAchievement
import com.fpstudio.stretchreminder.ui.composable.question.QuestionID
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.util.Constants.EMPTY
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Converts a time string in format "HH:MM AM/PM" to timestamp
 */
private fun parseTimeToTimestamp(timeString: String): Long {
    return try {
        android.util.Log.d("TimeParser", "Parsing time string: '$timeString'")
        // Use Locale.US to ensure consistent AM/PM parsing
        val format = SimpleDateFormat("hh:mm a", Locale.US)
        format.isLenient = false
        val parsedDate = format.parse(timeString)
        
        if (parsedDate != null) {
            android.util.Log.d("TimeParser", "Parsed date: $parsedDate")
            // Create a calendar with today's date and the parsed time
            val calendar = Calendar.getInstance().apply {
                time = parsedDate
                // Keep the hour and minute from parsed time
                val hour = get(Calendar.HOUR_OF_DAY)
                val minute = get(Calendar.MINUTE)
                
                android.util.Log.d("TimeParser", "Extracted hour: $hour, minute: $minute")
                
                // Set to today's date with the parsed time
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val result = calendar.timeInMillis
            android.util.Log.d("TimeParser", "Result timestamp: $result")
            result
        } else {
            android.util.Log.e("TimeParser", "Failed to parse time string: '$timeString'")
            0L
        }
    } catch (e: Exception) {
        android.util.Log.e("TimeParser", "Exception parsing time: ${e.message}", e)
        0L
    }
}

fun FormScreenContract.UiState.toUser(): User {
    val answers = form.flatMap { it.questions }
    var name = EMPTY
    var gender = 0
    var age = 0
    var mainPosture = 0
    var workDays: List<String> = emptyList()
    var achievement: List<UserAchievement> = emptyList()
    var startWorkTime = 0L
    var endWorkTime = 0L
    var bodyParts: List<BodyPartID> = emptyList()
    var frequency = 0
    var notificationPermission = false

    answers.forEach { question ->
        when (question) {
            is QuestionUiModel.InputText -> {
                if (question.id == QuestionID.NAME) name = question.selected
            }

            is QuestionUiModel.SingleChoice -> {
                if (question.id == QuestionID.FREQUENCY) {
                    question.selected?.let {
                        frequency = question.options.indexOf(it)
                    }
                }
                if (question.id == QuestionID.AGE) {
                    question.selected?.let { age = it }
                }
            }

            is QuestionUiModel.MultiChoice -> {
                if (question.id == QuestionID.ACHIEVEMENT) {
                    achievement = question.selected
                }
            }

            is QuestionUiModel.WorkDays -> {
                if (question.id == QuestionID.WORK_DAYS) workDays = question.selected
            }

            is QuestionUiModel.TimeRange -> {
                if (question.id == QuestionID.WORK_HOURS) {
                    startWorkTime = parseTimeToTimestamp(question.startTime)
                    endWorkTime = parseTimeToTimestamp(question.endTime)
                }
            }

            is QuestionUiModel.CustomBodyQuestion -> {
                bodyParts = question.selected
            }

            is QuestionUiModel.CustomGenderSingleChoice -> {
                if (question.id == QuestionID.GENDER) {
                    question.selected?.let { gender = it }
                }
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
        ageRange = age,
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
