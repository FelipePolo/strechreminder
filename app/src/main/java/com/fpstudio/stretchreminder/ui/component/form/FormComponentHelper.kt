package com.fpstudio.stretchreminder.ui.component.form

import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel

class FormComponentHelper {
    companion object {
        fun updateQuestionWithSelection(
            question: QuestionUiModel,
            selection: QuestionSelectionUiModel
        ): QuestionUiModel {
            return when (question) {

                is QuestionUiModel.InputText -> {
                    val selected = (selection as QuestionSelectionUiModel.StringSelectionUiModel)
                    question.copy(
                        answered = selected.selection.isNotEmpty(),
                        selected = selected.selection
                    )
                }

                is QuestionUiModel.SingleChoice -> {
                    val selected = (selection as QuestionSelectionUiModel.StringSelectionUiModel)
                    question.copy(
                        selected = selected.selection
                    )
                }

                is QuestionUiModel.MultiChoice -> {
                    val selected = (selection as QuestionSelectionUiModel.CustomUserAchivementUiModel)
                    val selectedList = question.selected.toMutableList()
                    if (selectedList.size > 1 && selectedList.contains(selected.selection)) {
                        selectedList.remove(selected.selection)
                    } else {
                        selectedList.add(selected.selection)
                    }
                    if (question.nothingOption) {
                        if (question.options.indexOf(selected.selection) == 0) {
                            selectedList.clear()
                            selectedList.add(selected.selection)
                        } else if (selectedList.contains(question.options[0])) {
                            selectedList.remove(question.options[0])
                        }
                    }
                    question.copy(selected = selectedList)
                }

                is QuestionUiModel.WorkDays -> {
                    val selected = (selection as QuestionSelectionUiModel.StringSelectionUiModel)
                    val selectedList = question.selected.toMutableList()
                    if (selectedList.size > 1 && selectedList.contains(selected.selection)) {
                        selectedList.remove(selected.selection)
                    } else {
                        selectedList.add(selected.selection)
                    }
                    question.copy(selected = selectedList)
                }

                is QuestionUiModel.TimeRange -> {
                    val selected = (selection as QuestionSelectionUiModel.TimeRangeSelectionUiModel)
                    question.copy(startTime = selected.startTime, endTime = selected.endTime)
                }

                is QuestionUiModel.ImageSingleChoice -> {
                    val selected = (selection as QuestionSelectionUiModel.IntSelectionUiModel)
                    question.copy(selected = selected.selection)
                }

                is QuestionUiModel.NotificationPermission -> question

                is QuestionUiModel.CustomBodyQuestion -> {
                    val selected = (selection as QuestionSelectionUiModel.CustomBodySelectionUiModel)
                    val selectedList = question.selected.toMutableList()
                    if (selectedList.size > 1 && selectedList.contains(selected.selection)) {
                        selectedList.remove(selected.selection)
                    } else {
                        selectedList.add(selected.selection)
                    }
                    val questionsOptions = question.options.map {it.second.id}
                    if (question.nothingOption) {
                        if (questionsOptions.indexOf(selected.selection) == 0) {
                            selectedList.clear()
                            selectedList.add(selected.selection)
                        } else if (selectedList.contains(questionsOptions[0])) {
                            selectedList.remove(questionsOptions[0])
                        }
                    }
                    question.copy(selected = selectedList)
                }

                is QuestionUiModel.CustomGenderSingleChoice -> {
                    val selected = (selection as QuestionSelectionUiModel.StringSelectionUiModel)
                    question.copy(
                        selected = selected.selection
                    )
                }
            }
        }

        fun updateFormWithSelection(
            form: FormUiModel,
            questionIndex: Int,
            selection: QuestionSelectionUiModel
        ): FormUiModel {
            val questions = form.questions.toMutableList()
            questions[questionIndex] =
                updateQuestionWithSelection(questions[questionIndex], selection)
            return form.copy(questions = questions)
        }

        fun allRequiredQuestionAreAnswered(form: FormUiModel): Boolean {
            form.questions.forEach { question ->
                if (question.required) {
                    return question.answered
                }
            }
            return true
        }
    }
}