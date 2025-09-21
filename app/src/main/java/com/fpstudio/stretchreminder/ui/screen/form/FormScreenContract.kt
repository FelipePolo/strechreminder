package com.fpstudio.stretchreminder.ui.screen.form

import com.fpstudio.stretchreminder.ui.component.form.FormUiModel
import com.fpstudio.stretchreminder.ui.composable.button.ButtonType
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.composable.congratulation.CongratulationUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionErrorType
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.screen.promises.madeforyou.MadeForYouUiModel
import com.fpstudio.stretchreminder.ui.screen.promises.plansuccess.PlanSuccessUiModel

interface FormScreenContract {
    data class UiState(
        val page: Int = 0,
        val form: List<FormUiModel> = getForms(),
        val backButton: StretchButtonUiModel = StretchButtonUiModel(
            isVisible = false,
            text = "Back",
            buttonType = ButtonType.LOTTIE_BACK
        ),
        val nextButton: StretchButtonUiModel = StretchButtonUiModel(
            isVisible = true,
            text = "Continue",
            buttonType = ButtonType.ANIMATED
        ),
        val shouldShowQuestionProgressBar: Boolean = true,
        val congratulation: CongratulationUiModel = CongratulationUiModel(),
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

        data class ShowErrorOnQuestion(val errorType: QuestionErrorType) : SideEffect
    }
}

