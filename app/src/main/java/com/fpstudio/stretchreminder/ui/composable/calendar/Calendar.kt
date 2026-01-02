package com.fpstudio.stretchreminder.ui.composable.calendar

import java.time.LocalDate

data class Calendar(
    val today: LocalDate,
    val markedDays: List<LocalDate> = emptyList() // Changed from List<Int> to List<LocalDate>
)
