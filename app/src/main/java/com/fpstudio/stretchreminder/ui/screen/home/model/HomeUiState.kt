package com.fpstudio.stretchreminder.ui.screen.home.model

import com.fpstudio.stretchreminder.ui.composable.calendar.Calendar
import java.time.LocalDate

data class HomeUiState(
    val headerState: HeaderUiState = HeaderUiState(),
    val dailyGoalState: DailyGoalUiState = DailyGoalUiState(),
    val dailyStatsState: DailyStatsUiState = DailyStatsUiState(),
    val calendarState: Calendar = Calendar(
        today = LocalDate.now()
    )
)

data class HeaderUiState(
    val userName: String = "",
    val formattedDate: String = ""
)

data class DailyGoalUiState(
    val progress: Int = 0,
    val sessionsCompleted: Int = 0,
    val totalSessions: Int = 2,
    val motivationalMessage: String = "You're doing great, keep it up!"
)

data class DailyStatsUiState(
    val stretchingTime: Int = 0,
    val stretchDays: Int = 0
)
