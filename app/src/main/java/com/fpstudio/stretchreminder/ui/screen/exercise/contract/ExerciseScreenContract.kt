package com.fpstudio.stretchreminder.ui.screen.exercise.contract

import com.fpstudio.stretchreminder.util.Constants

interface ExerciseScreenContract {

    data class UiState(
        val playlist: Playlist = Playlist(),
        val disclaimer: Int? = null,
        val preText: PreText = PreText(),
        val showCongratulations: Boolean = false,
        val showNoInternetDialog: Boolean = false
    )

    sealed class Intent {
        object UserPrepared: Intent()
        object SeeNextVideo: Intent()
        object CongratulationsComplete: Intent()
        data class ReadyToExercise(val videoDuration: Long): Intent()
        data class UpdateRemainingTime(val remainingTime: Long): Intent()
        object CheckInternetConnection: Intent()
        object HideNoInternetDialog: Intent()
    }

    sealed class SideEffect {
        object Play: SideEffect()
        object Pause: SideEffect()
        object NavigateNext: SideEffect()
    }
}

data class PreText(
    val isVisible: Boolean = true,
    val text: Int? = null,
    val secondsToShowPreText: Long = 0,
    val showPreTextForEachVideo: Boolean = false
)

data class Playlist(
    val videoDuration: Long = 0,
    val remainingTime: Long = 0,
    val playIndex: Int = 0,
    val videos: List<String> = emptyList(),
    val totalRoutineDuration: Long = 0, // Total duration of all videos in seconds
    val countedVideoIndices: Set<Int> = emptySet() // Track which videos have been counted
)