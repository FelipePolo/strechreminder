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
                val hasMoreVideos = uiState.value.playlist.playIndex + 1 < uiState.value.playlist.videos.size
                
                if (!hasMoreVideos) {
                    // Last video finished - show congratulations
                    updateUiState {
                        uiState.value.copy(
                            showCongratulations = true
                        )
                    }
                    // Navigate after 3 seconds
                    viewModelScope.launch {
                        kotlinx.coroutines.delay(3000)
                        emitSideEffect(SideEffect.NavigateNext)
                    }
                } else {
                    // More videos to play
                    val shouldShowPreText = uiState.value.playlist.videos.size > 1 && 
                                           uiState.value.preText.showPreTextForEachVideo
                    
                    updateUiState {
                        uiState.value.copy(
                            playlist = uiState.value.playlist.copy(
                                playIndex = uiState.value.playlist.playIndex + 1
                            ),
                            preText = if (shouldShowPreText) {
                                uiState.value.preText.copy(isVisible = true)
                            } else {
                                uiState.value.preText
                            }
                        )
                    }
                    
                    // Pause video if showing pre-text
                    if (shouldShowPreText) {
                        viewModelScope.launch {
                            emitSideEffect(SideEffect.Pause)
                        }
                    }
                }
            }
        }
    }

}