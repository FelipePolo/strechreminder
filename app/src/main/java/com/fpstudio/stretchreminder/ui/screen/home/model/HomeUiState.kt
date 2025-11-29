package com.fpstudio.stretchreminder.ui.screen.home.model

import com.fpstudio.stretchreminder.ui.composable.calendar.Calendar
import java.time.LocalDate
import java.time.Month

data class HomeUiState(
    val headerState: HeaderUiState = HeaderUiState(),
    val dailyGoalState: DailyGoalUiState = DailyGoalUiState(),
    val dailyStatsState: DailyStatsUiState = DailyStatsUiState(),
    val calendarState: Calendar = Calendar(
        today = LocalDate.now(),
        markedDays = listOf(10, 24)
    )
)

data class HeaderUiState(
    val userName: String = "",
    val formattedDate: String = ""
)

data class DailyGoalUiState(
    val progress: Int = 0
)

data class DailyStatsUiState(
    val stretchingTime: Int = 0,
    val stretchDays: Int = 0
)
