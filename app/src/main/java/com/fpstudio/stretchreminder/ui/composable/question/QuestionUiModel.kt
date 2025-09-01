package com.fpstudio.stretchreminder.ui.composable.question

import com.fpstudio.stretchreminder.util.Constants.EMPTY
import java.util.Calendar

sealed class QuestionUiModel {
    data class SingleChoice(
        val question: String,
        val options: List<String>,
        val selected: String = EMPTY
    ) : QuestionUiModel()

    data class MultiChoice(
        val question: String,
        val nothingOption: Boolean = false,
        val options: List<String>,
        val selected: List<String> = emptyList()
    ) : QuestionUiModel()

    data class WorkDays(
        val question: String,
        val days: List<String>,
        val selected: List<String> = emptyList()
    ) : QuestionUiModel()

    data class TimeRange(
        val question: String,
        val startTime: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
        },
        val endTime: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 17)
            set(Calendar.MINUTE, 0)
        }
    ) : QuestionUiModel()
    
    data class ImageSingleChoice(
        val question: String,
        val imagesResId: List<Int>,
        val selected: Int = -1
    ) : QuestionUiModel()

    data object NotificationPermission : QuestionUiModel()
}

sealed class QuestionUiSelection {
    data class StringSelection(val selection: String) : QuestionUiSelection()
    data class TimeRangeSelection(val startTime: Calendar, val endTime: Calendar) : QuestionUiSelection()
    data class IntSelection(val selection: Int) : QuestionUiSelection()
    data class BooleanSelection(val selection: Boolean) : QuestionUiSelection()
}
