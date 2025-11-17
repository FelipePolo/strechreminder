package com.fpstudio.stretchreminder.ui.composable.calendar

import java.time.LocalDate
import java.time.Month

data class Calendar(
    val today: LocalDate,
    val markedDays: List<Int> = emptyList()
)
