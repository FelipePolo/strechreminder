package com.fpstudio.stretchreminder.ui.composable.question

import java.util.Calendar

sealed class QuestionSelectionUiModel {
    data class StringSelectionUiModel(val selection: String) : QuestionSelectionUiModel()
    data class TimeRangeSelectionUiModel(val startTime: Calendar, val endTime: Calendar) :
        QuestionSelectionUiModel()

    data class IntSelectionUiModel(val selection: Int) : QuestionSelectionUiModel()
    data class BooleanSelectionUiModel(val selection: Boolean) : QuestionSelectionUiModel()
}