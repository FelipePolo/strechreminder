package com.fpstudio.stretchreminder.ui.composable.question

interface QuestionSideEffect {
    data class QuestionError(val errorType: QuestionErrorType) : QuestionSideEffect
}

enum class QuestionErrorType {
    INPUT_TEXT_INCOMPLETE
}