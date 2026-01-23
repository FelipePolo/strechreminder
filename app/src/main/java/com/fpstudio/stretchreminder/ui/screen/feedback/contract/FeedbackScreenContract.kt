package com.fpstudio.stretchreminder.ui.screen.feedback.contract

import androidx.annotation.DrawableRes
import com.fpstudio.stretchreminder.R

object FeedbackScreenContract {
    
    data class UiState(
        val selectedSubject: FeedbackSubject? = null,
        val customSubject: String = "",
        val message: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )
    

    enum class FeedbackSubject(@androidx.annotation.StringRes val displayNameRes: Int, @DrawableRes val iconRes: Int) {
        BUG_REPORT(R.string.feedback_subject_bug, R.drawable.subject_bug_icon),
        FEATURE_REQUEST(R.string.feedback_subject_feature, R.drawable.subject_light_icon),
        GENERAL_FEEDBACK(R.string.feedback_subject_general, R.drawable.subject_comment_icon),
        OTHER(R.string.feedback_subject_other, R.drawable.subject_pen_icon)
    }

    
    sealed class Intent {
        data class SelectSubject(val subject: FeedbackSubject) : Intent()
        data class UpdateCustomSubject(val text: String) : Intent()
        data class UpdateMessage(val text: String) : Intent()
        object SubmitFeedback : Intent()
        object NavigateBack : Intent()
        object DismissError : Intent()
    }
    
    sealed class SideEffect {
        data class ShowSuccess(val message: String) : SideEffect()
        object NavigateBack : SideEffect()
    }
}
