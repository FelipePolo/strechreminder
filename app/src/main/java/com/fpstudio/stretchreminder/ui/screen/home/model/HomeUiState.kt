package com.fpstudio.stretchreminder.ui.screen.home.model

import com.fpstudio.stretchreminder.ui.composable.calendar.Calendar
import java.time.LocalDate
import java.time.Month

data class HomeUiState(
    val headerState: HeaderUiState = HeaderUiState(),
    val dailyStatsState: DailyStatsUiState = DailyStatsUiState(),
    val calendarState: Calendar = Calendar(
        today = LocalDate.now(),
        markedDays = listOf(10, 24)
    )
)

data class HeaderUiState(
    val dayOfWeek: String = "",
    val month: Month = Month.JANUARY,
    val day: Int = 1
)

data class DailyStatsUiState(
    val stretchingTime: Int = 0,
    val stretchDays: Int = 0
)
