package com.fpstudio.stretchreminder.ui.component.form

import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiSelection

class FormComponentHelper {
    companion object {
        fun updateQuestionWithSelection(
            question: QuestionUiModel,
            selection: QuestionUiSelection
        ): QuestionUiModel {
            return when (question) {
                is QuestionUiModel.SingleChoice -> {
                    val selected = (selection as QuestionUiSelection.StringSelection)
                    question.copy(
                        selected = selected.selection
                    )
                }

                is QuestionUiModel.MultiChoice -> {
                    val selected = (selection as QuestionUiSelection.StringSelection)
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
                    val selected = (selection as QuestionUiSelection.StringSelection)
                    val selectedList = question.selected.toMutableList()
                    if (selectedList.size > 1 && selectedList.contains(selected.selection)) {
                        selectedList.remove(selected.selection)
                    } else {
                        selectedList.add(selected.selection)
                    }
                    question.copy(selected = selectedList)
                }

                is QuestionUiModel.TimeRange -> {
                    val selected = (selection as QuestionUiSelection.TimeRangeSelection)
                    question.copy(startTime = selected.startTime, endTime = selected.endTime)
                }

                is QuestionUiModel.ImageSingleChoice -> {
                    val selected = (selection as QuestionUiSelection.IntSelection)
                    question.copy(selected = selected.selection)
                }

                is QuestionUiModel.NotificationPermission -> question
            }
        }

        fun updateFormWithSelection(
            form: FormUiModel,
            questionIndex: Int,
            selection: QuestionUiSelection
        ): FormUiModel {
            val questions = form.questions.toMutableList()
            questions[questionIndex] =
                updateQuestionWithSelection(questions[questionIndex], selection)
            return form.copy(questions = questions)
        }
    }
}