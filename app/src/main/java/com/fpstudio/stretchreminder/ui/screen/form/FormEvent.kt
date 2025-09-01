package com.fpstudio.stretchreminder.ui.screen.form

import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiSelection

sealed class FormEvent {
    object OnContinueClick : FormEvent()
    object OnNotificationAllow : FormEvent()
    object OnNotificationDeny : FormEvent()
    data class OnSelection(val questionIndex: Int, val selection: QuestionUiSelection) : FormEvent()
}
