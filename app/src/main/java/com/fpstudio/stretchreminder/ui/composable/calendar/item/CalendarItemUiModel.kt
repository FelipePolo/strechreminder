package com.fpstudio.stretchreminder.ui.composable.calendar.item

import androidx.compose.ui.graphics.Color
import com.fpstudio.stretchreminder.util.Constants.EMPTY
import java.time.Month

data class CalendarItemUiModel(
    val dayNumber: String,
    val dayTextColor: Color = Color.Black,
    val dayOfTheMonth: String = EMPTY,
    val currentMonth: Month,
    val borderColor: Color,
    val backgroundColor: Color,
    val checked: Boolean = false,
)
