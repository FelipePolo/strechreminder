package com.fpstudio.stretchreminder.ui.component.form

import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel

data class FormUiModel(
    val addNextBtn: Boolean = false,
    val questions: List<QuestionUiModel> = emptyList()
)
