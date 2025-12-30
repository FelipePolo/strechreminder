package com.fpstudio.stretchreminder.ui.composable.question

import com.fpstudio.stretchreminder.data.model.BodyPart
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.UserAchievement
import com.fpstudio.stretchreminder.util.Constants.EMPTY
import java.util.Calendar

sealed class QuestionUiModel(
    open val id: QuestionID = QuestionID.NONE,
    open val subtitle1: String = EMPTY,
    open val subtitle2: String = EMPTY,
    open val question: String = EMPTY,
    open val required: Boolean = false,
    open val answered: Boolean = true,
) {

    data class InputText(
        override val id: QuestionID,
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        override val required: Boolean = false,
        override val answered: Boolean = false,
        val selected: String = EMPTY
    ) : QuestionUiModel()

    data class SingleChoice(
        override val id: QuestionID,
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val options: List<String>,
        val selected: String = EMPTY
    ) : QuestionUiModel()

    data class MultiChoice(
        override val id: QuestionID,
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val nothingOption: Boolean = false,
        val options: List<UserAchievement>,
        val selected: List<UserAchievement> = emptyList()
    ) : QuestionUiModel()

    data class WorkDays(
        override val id: QuestionID,
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val days: List<String>,
        val selected: List<String> = emptyList()
    ) : QuestionUiModel()

    data class TimeRange(
        override val id: QuestionID,
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val startTime: String = "09:00 AM",
        val endTime: String = "05:00 PM"
    ) : QuestionUiModel()

    data class ImageSingleChoice(
        override val id: QuestionID,
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val imagesResId: List<Int>,
        val selected: Int = -1
    ) : QuestionUiModel()

    data class CustomBodyQuestion(
        override val id: QuestionID,
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val nothingOption: Boolean = false,
        val options: List<Pair<Int, BodyPart>>,
        val selected: List<BodyPartID> = emptyList()
    ) : QuestionUiModel()

    data class CustomGenderSingleChoice(
        override val id: QuestionID,
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val male: Pair<Int, String>,
        val female: Pair<Int, String>,
        val preferNotToSay: String,
        val selected: String = EMPTY
    ) : QuestionUiModel()

    data class NotificationPermission(
        override val id: QuestionID,
        override val subtitle2: String = EMPTY,
        override val question: String,
    ) : QuestionUiModel()
}

enum class QuestionID {
    NONE,
    NAME,
    ACHIEVEMENT,
    BODY_PARTS,
    FREQUENCY,
    GENDER,
    AGE,
    MAIN_POSTURE,
    WORK_DAYS,
    WORK_HOURS,
    NOTIFICATION_PERMISSION,
}