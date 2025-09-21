package com.fpstudio.stretchreminder.ui.composable.question

import com.fpstudio.stretchreminder.util.Constants.EMPTY
import java.util.Calendar

sealed class QuestionUiModel(
    open val subtitle1: String = EMPTY,
    open val subtitle2: String = EMPTY,
    open val question: String = EMPTY,
    open val required: Boolean = false,
    open val answered: Boolean = true,
) {

    data class InputText(
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        override val required: Boolean = false,
        override val answered: Boolean = false,
        val selected: String = EMPTY
    ) : QuestionUiModel()

    data class SingleChoice(
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val options: List<String>,
        val selected: String = EMPTY
    ) : QuestionUiModel()

    data class MultiChoice(
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val nothingOption: Boolean = false,
        val options: List<String>,
        val selected: List<String> = emptyList()
    ) : QuestionUiModel()

    data class WorkDays(
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val days: List<String>,
        val selected: List<String> = emptyList()
    ) : QuestionUiModel()

    data class TimeRange(
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
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
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val imagesResId: List<Int>,
        val selected: Int = -1
    ) : QuestionUiModel()

    data class CustomBodyQuestion(
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val nothingOption: Boolean = false,
        val options: List<Pair<Int, String>>,
        val selected: List<String> = emptyList()
    ) : QuestionUiModel()

    data class CustomGenderSingleChoice(
        override val subtitle1: String = EMPTY,
        override val subtitle2: String = EMPTY,
        override val question: String,
        val male: Pair<Int, String>,
        val female: Pair<Int, String>,
        val preferNotToSay: String,
        val selected: String = EMPTY
    ) : QuestionUiModel()

    data class NotificationPermission(
        override val subtitle2: String = EMPTY,
        override val question: String,
    ) : QuestionUiModel()
}


