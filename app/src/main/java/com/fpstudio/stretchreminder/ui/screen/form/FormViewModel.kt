package com.fpstudio.stretchreminder.ui.screen.form

import androidx.lifecycle.ViewModel
import com.fpstudio.stretchreminder.ui.component.form.FormComponentHelper
import com.fpstudio.stretchreminder.ui.component.form.FormUiModel
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiSelection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormViewModel : ViewModel() {

    private val _state = MutableStateFlow(FormScreenState())
    val uiState: StateFlow<FormScreenState> = _state.asStateFlow()


    fun onEvent(event: FormEvent) {
        when (event) {
            is FormEvent.OnSelection -> {
                onSelection(
                    questionIndex = event.questionIndex,
                    selection = event.selection
                )
            }

            is FormEvent.OnContinueClick -> onContinueClick()
            is FormEvent.OnNotificationAllow -> onContinueClick()
            is FormEvent.OnNotificationDeny -> onContinueClick()
        }
    }

    fun onContinueClick() {
        val nextPage = getPageState()
        if (nextPage == uiState.value.form.size) {
            _state.update {
                it.copy(
                    page = nextPage,
                    shouldShowQuestionProgressBar = false,
                    congratulation = uiState.value.congratulation.copy(visible = true)
                )
            }
        } else if (nextPage > uiState.value.form.size) {
            // GO TO HOME SCREEN
        } else {
            _state.update {
                it.copy(
                    page = nextPage,
                    button = getStretchButtonState(nextPage)
                )
            }
        }
    }

    private fun onSelection(questionIndex: Int, selection: QuestionUiSelection) {
        val page = uiState.value.page
        val question = uiState.value.form[page].questions[questionIndex]
        if (shouldRequestNotificationPermission(
                questionModel = question,
                selection = selection
            )
        ) {
            _state.update {
                it.copy(
                    shouldRequestNotificationPermission = true
                )
            }
        } else {
            val nextPage = getPageState()
            if (uiState.value.button.isVisible) {
                _state.update {
                    it.copy(
                        form = getFormListState(questionIndex, selection),
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        page = nextPage,
                        form = getFormListState(questionIndex, selection),
                        button = getStretchButtonState(nextPage)
                    )
                }
            }
        }
    }

    private fun shouldRequestNotificationPermission(
        questionModel: QuestionUiModel,
        selection: QuestionUiSelection
    ): Boolean {
        if (questionModel is QuestionUiModel.NotificationPermission) {
            return (selection as QuestionUiSelection.BooleanSelection).selection
        }
        return false
    }

    private fun getPageState(): Int {
        val page = uiState.value.page
        return page + 1
    }

    private fun getStretchButtonState(page: Int): StretchButtonUiModel {
        return if (page == 0 || page == 1 || page == 3 || page == 4 || page == 5) {
            uiState.value.button.copy(
                isVisible = false
            )
        } else {
            uiState.value.button.copy(
                isVisible = true
            )
        }
    }

    private fun getFormListState(
        questionIndex: Int,
        selection: QuestionUiSelection
    ): List<FormUiModel> {
        val page = uiState.value.page
        val form = uiState.value.form.toMutableList()
        val updatedForm =
            FormComponentHelper.updateFormWithSelection(form[page], questionIndex, selection)
        form[uiState.value.page] = updatedForm
        return form
    }

}
