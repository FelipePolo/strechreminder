package com.fpstudio.stretchreminder.ui.screen.home.model

import androidx.annotation.StringRes
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.calendar.Calendar
import java.time.LocalDate

data class HomeUiState(
    val headerState: HeaderUiState = HeaderUiState(),
    val dailyGoalState: DailyGoalUiState = DailyGoalUiState(),
    val dailyStatsState: DailyStatsUiState = DailyStatsUiState(),
    val calendarState: Calendar = Calendar(
        today = LocalDate.now()
    ),
    val showNoInternetDialog: Boolean = false
)

data class HeaderUiState(
    val userName: String = "",
    val formattedDate: String = ""
)

data class DailyGoalUiState(
    val progress: Int = 0,
    val sessionsCompleted: Int = 0,
    val totalSessions: Int = 2,
    @StringRes val motivationalMessage: Int = R.string.home_motivational_message
)

data class DailyStatsUiState(
    val stretchingTime: Int = 0,
    val stretchDays: Int = 0
)
