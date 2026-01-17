package com.fpstudio.stretchreminder.ui.composable.question

import com.fpstudio.stretchreminder.data.model.BodyPart
import com.fpstudio.stretchreminder.data.model.BodyPartID
import com.fpstudio.stretchreminder.data.model.UserAchievement
import com.fpstudio.stretchreminder.util.Constants.EMPTY

sealed class QuestionUiModel(
    open val id: QuestionID = QuestionID.NONE,
    open val subtitle1: Int? = null,
    open val subtitle2: Int? = null,
    open val question: Int? = null,
    open val required: Boolean = false,
    open val answered: Boolean = true,
) {

    data class InputText(
        override val id: QuestionID,
        override val subtitle1: Int? = null,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        override val required: Boolean = false,
        override val answered: Boolean = false,
        val selected: String = EMPTY
    ) : QuestionUiModel()

    data class SingleChoice(
        override val id: QuestionID,
        override val subtitle1: Int? = null,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        val options: List<Int> = emptyList(),
        val selected: Int? = null
    ) : QuestionUiModel()

    data class MultiChoice(
        override val id: QuestionID,
        override val subtitle1: Int? = null,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        val nothingOption: Boolean = false,
        val options: List<UserAchievement>,
        val selected: List<UserAchievement> = emptyList()
    ) : QuestionUiModel()

    data class WorkDays(
        override val id: QuestionID,
        override val subtitle1: Int? = null,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        val days: List<Pair<Int, String>>,
        val selected: List<String> = emptyList()
    ) : QuestionUiModel()

    data class TimeRange(
        override val id: QuestionID,
        override val subtitle1: Int? = null,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        val startTime: String = "09:00 AM",
        val endTime: String = "05:00 PM"
    ) : QuestionUiModel()

    data class ImageSingleChoice(
        override val id: QuestionID,
        override val subtitle1: Int? = null,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        val imagesResId: List<Int>,
        val selected: Int = -1
    ) : QuestionUiModel()

    data class CustomBodyQuestion(
        override val id: QuestionID,
        override val subtitle1: Int? = null,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        val nothingOption: Boolean = false,
        val options: List<Pair<Int, BodyPart>>,
        val selected: List<BodyPartID> = emptyList()
    ) : QuestionUiModel()

    data class CustomGenderSingleChoice(
        override val id: QuestionID,
        override val subtitle1: Int? = null,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        val male: Pair<Int, Int>, // iconRes, textRes
        val female: Pair<Int, Int>, // iconRes, textRes
        val preferNotToSay: Int, // textRes
        val selected: Int? = null
    ) : QuestionUiModel()

    data class NotificationPermission(
        override val id: QuestionID,
        override val subtitle2: Int? = null,
        override val question: Int? = null,
        override val answered: Boolean = false
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