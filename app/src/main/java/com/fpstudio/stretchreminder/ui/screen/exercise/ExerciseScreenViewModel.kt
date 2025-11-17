package com.fpstudio.stretchreminder.ui.screen.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.util.foundation.Mvi
import com.fpstudio.stretchreminder.util.foundation.MviDelegate
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.UiState
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.exercise.contract.ExerciseScreenContract.SideEffect
import kotlinx.coroutines.launch

class ExerciseScreenViewModel(var initialState: UiState) : ViewModel(),
    Mvi<UiState, Intent, SideEffect> by MviDelegate(initialState) {

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.UserPrepared -> {
                updateUiState {
                    uiState.value.copy(
                        preText = uiState.value.preText.copy(
                            isVisible = false
                        )
                    )
                }
                viewModelScope.launch {
                    emitSideEffect(SideEffect.Play)
                }
            }

            is Intent.ReadyToExercise -> {
                updateUiState {
                    uiState.value.copy(
                        playlist = uiState.value.playlist.copy(
                            videoDuration = intent.videoDuration
                        )
                    )
                }
            }

            is Intent.UpdateRemainingTime -> {
                updateUiState {
                    uiState.value.copy(
                        playlist = uiState.value.playlist.copy(
                            remainingTime = intent.remainingTime
                        )
                    )
                }
            }

            is Intent.SeeNextVideo -> {
                if (uiState.value.playlist.playIndex + 1 == uiState.value.playlist.videos.size) {
                    viewModelScope.launch {
                        emitSideEffect(SideEffect.NavigateNext)
                    }
                } else {
                    updateUiState {
                        uiState.value.copy(
                            playlist = uiState.value.playlist.copy(
                                playIndex = (uiState.value.playlist.playIndex + 1).coerceAtMost(
                                    uiState.value.playlist.videos.lastIndex
                                )
                            )
                        )
                    }
                }
            }
        }
    }

}