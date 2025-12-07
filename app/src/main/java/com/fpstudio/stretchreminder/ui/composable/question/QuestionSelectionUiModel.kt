package com.fpstudio.stretchreminder.ui.composable.question

import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.UserAchievement
import java.util.Calendar

sealed class QuestionSelectionUiModel {
    data class StringSelectionUiModel(val selection: String) : QuestionSelectionUiModel()
    data class TimeRangeSelectionUiModel(val startTime: Calendar, val endTime: Calendar) :
        QuestionSelectionUiModel()

    data class IntSelectionUiModel(val selection: Int) : QuestionSelectionUiModel()
    data class BooleanSelectionUiModel(val selection: Boolean) : QuestionSelectionUiModel()

    data class CustomBodySelectionUiModel(val selection: BodyPartID): QuestionSelectionUiModel()
    data class CustomUserAchivementUiModel(val selection: UserAchievement): QuestionSelectionUiModel()

}