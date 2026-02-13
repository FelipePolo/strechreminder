package com.fpstudio.stretchreminder.ui.screen.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.data.mapper.toUser
import com.fpstudio.stretchreminder.domain.usecase.GetUserUseCase
import com.fpstudio.stretchreminder.domain.usecase.SaveUserUseCase
import com.fpstudio.stretchreminder.util.foundation.Mvi
import com.fpstudio.stretchreminder.util.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.component.form.FormComponentHelper
import com.fpstudio.stretchreminder.ui.component.form.FormUiModel
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionErrorType
import com.fpstudio.stretchreminder.ui.composable.question.QuestionID
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.form.FormScreenContract.UiState
import com.fpstudio.stretchreminder.util.Constants.EMPTY
import kotlinx.coroutines.launch

class FormViewModel(
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel(), Mvi<UiState, Intent, SideEffect> by MviDelegate(UiState()) {

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.OnQuestionAnswered -> {
                onSelection(
                    questionIndex = intent.questionIndex,
                    selection = intent.selection
                )
            }

            is Intent.OnContinueClick -> onContinue()
            is Intent.OnNotificationAllow -> {
                // Mark notification permission question as answered
                updateNotificationPermissionState(granted = true)
                onContinue()
            }
            is Intent.OnNotificationDeny -> {
                // Mark notification permission question as answered (but denied)
                updateNotificationPermissionState(granted = false)
                onContinue()
            }
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
        var userName = EMPTY
        if (nextPage == 1) {
            val questionName = uiState.value.form.find { questionsList ->
                questionsList.questions.any { it.id == QuestionID.NAME }
            }?.questions?.find { it.id == QuestionID.NAME } as? QuestionUiModel.InputText
            userName = questionName?.selected ?: EMPTY
        }
        if (nextPage >= uiState.value.form.size) {
            viewModelScope.launch {
                saveUserUseCase.invoke(uiState.value.toUser())
                emitSideEffect(SideEffect.NavigateNext)
            }
        } else {
            val form = uiState.value.form[uiState.value.page]
            if (FormComponentHelper.allRequiredQuestionAreAnswered(form)) {
                val uiState = shouldShowPromiseScreen(nextPage)
                updateUiState {
                    uiState.copy(
                        userName = userName,
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
        val newButton = uiState.value.backButton as StretchButtonUiModel.Lottie
        return newButton.copy(
            isVisible = nextPage > 0
        )
    }

    private fun shouldShowPromiseScreen(nextPage: Int): UiState {
        return when (nextPage) {
            3 -> {
                // Generate dynamic cards from user achievements
                val (card1, card2, card3) = com.fpstudio.stretchreminder.ui.screen.promises.madeforyou.createMadeForYouCards(
                    uiState.value.achievements
                )
                uiState.value.copy(
                    madeForYou = uiState.value.madeForYou.copy(
                        isVisible = true,
                        card1 = card1,
                        card2 = card2,
                        card3 = card3
                    )
                )
            }

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
                val updatedForm = getFormListState(questionIndex, selection)
                when (question) {
                    is QuestionUiModel.CustomGenderSingleChoice -> {
                        val selectedValue = (selection as? QuestionSelectionUiModel.IntSelectionUiModel)?.selection ?: 0
                        updateUiState {
                            copy(
                                userGender = selectedValue,
                                form = updatedForm,
                            )
                        }
                    }
                    is QuestionUiModel.MultiChoice -> {
                        if (question.id == QuestionID.ACHIEVEMENT) {
                            updateUiState {
                                copy(
                                    achievements = question.selected,
                                    form = updatedForm,
                                )
                            }
                        }
                    }
                    else -> {
                        updateUiState {
                            copy(
                                form = updatedForm,
                            )
                        }
                    }
                }
            } else {
                if (nextPage >= uiState.value.form.size) {
                    viewModelScope.launch {
                        saveUserUseCase.invoke(uiState.value.toUser())
                        emitSideEffect(SideEffect.NavigateNext)
                    }
                }else {
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
        val newButton = uiState.value.nextButton as StretchButtonUiModel.Animated
        return if (form.addNextBtn) {
            newButton.copy(
                isVisible = true
            )
        } else {
            newButton.copy(
                isVisible = false
            )
        }
    }

    private fun updateNotificationPermissionState(granted: Boolean) {
        val page = uiState.value.page
        val form = uiState.value.form.toMutableList()
        val currentPageForm = form[page]
        
        // Find the notification permission question
        val questionIndex = currentPageForm.questions.indexOfFirst { 
            it is QuestionUiModel.NotificationPermission 
        }
        
        if (questionIndex != -1) {
            val updatedQuestions = currentPageForm.questions.toMutableList()
            val notificationQuestion = updatedQuestions[questionIndex] as QuestionUiModel.NotificationPermission
            
            // Update the question with the answered state
            updatedQuestions[questionIndex] = notificationQuestion.copy(answered = granted)
            
            form[page] = currentPageForm.copy(questions = updatedQuestions)
            
            updateUiState {
                copy(form = form)
            }
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
