package com.fpstudio.stretchreminder.ui.composable.question

import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.UserAchievement

sealed class QuestionSelectionUiModel {
    data class StringSelectionUiModel(val selection: String) : QuestionSelectionUiModel()
    data class TimeRangeSelectionUiModel(val startTime: String, val endTime: String) :
        QuestionSelectionUiModel()

    data class IntSelectionUiModel(val selection: Int) : QuestionSelectionUiModel()
    data class BooleanSelectionUiModel(val selection: Boolean) : QuestionSelectionUiModel()

    data class CustomBodySelectionUiModel(val selection: BodyPartID): QuestionSelectionUiModel()
    data class CustomUserAchivementUiModel(val selection: UserAchievement): QuestionSelectionUiModel()

}