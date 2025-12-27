package com.fpstudio.stretchreminder.ui.screen.exercise.contract

import com.fpstudio.stretchreminder.util.Constants

interface ExerciseScreenContract {

    data class UiState(
        val playlist: Playlist = Playlist(),
        val disclaimer: String = Constants.EMPTY,
        val preText: PreText = PreText(),
        val showCongratulations: Boolean = false
    )

    sealed class Intent {
        object UserPrepared: Intent()
        object SeeNextVideo: Intent()
        data class ReadyToExercise(val videoDuration: Long): Intent()
        data class UpdateRemainingTime(val remainingTime: Long): Intent()
    }

    sealed class SideEffect {
        object Play: SideEffect()
        object Pause: SideEffect()
        object NavigateNext: SideEffect()
    }
}

data class PreText(
    val isVisible: Boolean = true,
    val text: String = Constants.EMPTY,
    val secondsToShowPreText: Long = 0,
    val showPreTextForEachVideo: Boolean = false
)

data class Playlist(
    val videoDuration: Long = 0,
    val remainingTime: Long = 0,
    val playIndex: Int = 0,
    val videos: List<String> = emptyList(),
)