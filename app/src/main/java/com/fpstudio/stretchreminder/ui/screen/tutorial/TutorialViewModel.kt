package com.fpstudio.stretchreminder.ui.screen.tutorial

import androidx.lifecycle.ViewModel
import com.fpstudio.stretchreminder.foundation.Mvi
import com.fpstudio.stretchreminder.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenContract.SideEffect

class TutorialViewModel : ViewModel(), Mvi<UiState, Intent, SideEffect> by MviDelegate(UiState()) {

    override fun handleIntent(intent: Intent) {

    }
}
