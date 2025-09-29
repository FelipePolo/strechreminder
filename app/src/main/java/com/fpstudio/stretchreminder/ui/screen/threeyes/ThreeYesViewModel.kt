package com.fpstudio.stretchreminder.ui.screen.threeyes

import androidx.lifecycle.ViewModel
import com.fpstudio.stretchreminder.foundation.Mvi
import com.fpstudio.stretchreminder.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.UiState
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesContract.Intent

class ThreeYesViewModel : ViewModel(), Mvi<UiState, Intent, SideEffect> by MviDelegate(UiState()) {


}
