package com.fpstudio.stretchreminder.ui.screen.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.foundation.Mvi
import com.fpstudio.stretchreminder.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.component.form.FormComponentHelper
import com.fpstudio.stretchreminder.ui.component.form.FormUiModel
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionErrorType
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.promises.madeforyou.MadeForYouUiModel
import com.fpstudio.stretchreminder.ui.screen.promises.plansuccess.PlanSuccessUiModel

class FormViewModel : ViewModel(), Mvi<UiState, Intent, SideEffect> by MviDelegate(UiState()) {

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.OnQuestionAnswered -> {
                onSelection(
                    questionIndex = intent.questionIndex,
                    selection = intent.selection
                )
            }

            is Intent.OnContinueClick -> onContinue()
            is Intent.OnNotificationAllow -> onContinue()
            is Intent.OnNotificationDeny -> onContinue()
            is Intent.OnBackClick -> onBack()
            Intent.OnMadeForYouCloseClick -> onMadeForYouCloseClick()
            Intent.OnPlanSuccessCloseClick -> onPlanSuccessCloseClick()
        }
    }

    private fun onMadeForYouCloseClick() {
        updateUiState {
            copy(
                madeForYou = uiState.value.madeForYou.copy(isVisible = false)
            )
        }
    }

    private fun onPlanSuccessCloseClick() {
        updateUiState {
            copy(
                planSuccess = uiState.value.planSuccess.copy(isVisible = false)
            )
        }
    }

    private fun onBack() {
        val previousPage = getPreviousPage()
        updateUiState {
            copy(
                page = previousPage,
                nextButton = getNextButtonState(previousPage),
                backButton = getBackButtonState(previousPage)
            )
        }
    }

    fun onContinue() {
        val nextPage = getNextPage()
        if (nextPage == uiState.value.form.size) {
            updateUiState {
                copy(
                    shouldShowQuestionProgressBar = false,
                    congratulation = uiState.value.congratulation.copy(visible = true)
                )
            }
        } else {
            val form = uiState.value.form[uiState.value.page]
            if (FormComponentHelper.allRequiredQuestionAreAnswered(form)) {
                val uiState = shouldShowPromiseScreen(nextPage)
                updateUiState {
                    uiState.copy(
                        page = nextPage,
                        nextButton = getNextButtonState(nextPage),
                        backButton = getBackButtonState(nextPage)
                    )
                }
            } else {
                viewModelScope.emitSideEffect(
                    SideEffect.ShowErrorOnQuestion(
                        errorType = QuestionErrorType.INPUT_TEXT_INCOMPLETE
                    )
                )
            }
        }
    }

    private fun getBackButtonState(nextPage: Int): StretchButtonUiModel {
        return uiState.value.backButton.copy(
            isVisible = nextPage > 0
        )
    }

    private fun shouldShowPromiseScreen(nextPage: Int): UiState {
        return when (nextPage) {
            3 ->
                uiState.value.copy(
                    madeForYou = uiState.value.madeForYou.copy(isVisible = true)
                )

            6 ->
                uiState.value.copy(
                    planSuccess = uiState.value.planSuccess.copy(isVisible = true)
                )

            else -> uiState.value
        }
    }

    private fun onSelection(questionIndex: Int, selection: QuestionSelectionUiModel) {
        val page = uiState.value.page
        val question = uiState.value.form[page].questions[questionIndex]
        if (!shouldRequestNotificationPermission(
                questionModel = question,
                selection = selection
            )
        ) {
            val nextPage = getNextPage()
            val form = uiState.value.form[page]
            if (form.addNextBtn) {
                updateUiState {
                    copy(
                        form = getFormListState(questionIndex, selection),
                    )
                }
            } else {
                val uiState = shouldShowPromiseScreen(nextPage)
                updateUiState {
                    uiState.copy(
                        page = nextPage,
                        form = getFormListState(questionIndex, selection),
                        nextButton = getNextButtonState(nextPage)
                    )
                }
            }
        }
    }

    private fun shouldRequestNotificationPermission(
        questionModel: QuestionUiModel,
        selection: QuestionSelectionUiModel
    ): Boolean {
        if (questionModel is QuestionUiModel.NotificationPermission) {
            val notificationAllowUserAnswer =
                (selection as QuestionSelectionUiModel.BooleanSelectionUiModel).selection
            if (notificationAllowUserAnswer) {
                viewModelScope.emitSideEffect(SideEffect.RequestNotificationPermission)
                return true
            } else return false
        }
        return false
    }

    private fun getNextPage(): Int {
        val page = uiState.value.page
        return (page + 1).coerceAtMost(uiState.value.form.size)
    }

    private fun getPreviousPage(): Int {
        val page = uiState.value.page
        return (page - 1).coerceAtLeast(0)
    }

    private fun getNextButtonState(page: Int): StretchButtonUiModel {
        val form = uiState.value.form[page]
        return if (form.addNextBtn) {
            uiState.value.nextButton.copy(
                isVisible = true
            )
        } else {
            uiState.value.nextButton.copy(
                isVisible = false
            )
        }
    }

    private fun getFormListState(
        questionIndex: Int,
        selection: QuestionSelectionUiModel
    ): List<FormUiModel> {
        val page = uiState.value.page
        val form = uiState.value.form.toMutableList()
        val updatedForm =
            FormComponentHelper.updateFormWithSelection(form[page], questionIndex, selection)
        form[uiState.value.page] = updatedForm
        return form
    }
}
