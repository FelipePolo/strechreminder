package com.fpstudio.stretchreminder.ui.screen.form

import com.fpstudio.stretchreminder.data.model.UserAchievement
import com.fpstudio.stretchreminder.ui.component.form.FormUiModel
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionErrorType
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.screen.promises.madeforyou.MadeForYouUiModel
import com.fpstudio.stretchreminder.ui.screen.promises.plansuccess.PlanSuccessUiModel
import com.fpstudio.stretchreminder.util.Constants.EMPTY

interface FormScreenContract {
    data class UiState(
        val page: Int = 0,
        val userName: String = EMPTY,
        val userGender: String = EMPTY,
        val achievements: List<UserAchievement> = emptyList(),
        val form: List<FormUiModel> = getForms(),
        val backButton: StretchButtonUiModel = StretchButtonUiModel.Lottie(
            isVisible = false,
            iterations = 3
        ),
        val nextButton: StretchButtonUiModel = StretchButtonUiModel.Animated(
            isVisible = true,
            text = "Continue"
        ),
        val shouldShowQuestionProgressBar: Boolean = true,
        val madeForYou: MadeForYouUiModel = MadeForYouUiModel(),
        val planSuccess: PlanSuccessUiModel = PlanSuccessUiModel()
    )

    sealed interface Intent {
        object OnContinueClick : Intent
        object OnNotificationAllow : Intent
        object OnNotificationDeny : Intent
        data class OnQuestionAnswered(val questionIndex: Int, val selection: QuestionSelectionUiModel) : Intent
        object OnBackClick : Intent

        object OnMadeForYouCloseClick : Intent
        object OnPlanSuccessCloseClick : Intent
    }

    sealed interface SideEffect {
        object RequestNotificationPermission : SideEffect

        object NavigateNext : SideEffect

        data class ShowErrorOnQuestion(val errorType: QuestionErrorType) : SideEffect
    }
}

