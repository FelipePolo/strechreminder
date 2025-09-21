package com.fpstudio.stretchreminder.ui.screen.home.model

import java.time.Month

data class HomeUiState(
    val headerState: HeaderUiState = HeaderUiState(),
    val dailyStatsState: DailyStatsUiState = DailyStatsUiState(),
    val calendarState: CalendarUiState = CalendarUiState()
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

data class CalendarUiState(
    val currentMonth: Month = Month.JANUARY,
    val currentYear: Int = 2023,
    val selectedDay: Int = 1,
    val markedDays: List<Int> = emptyList()
)
