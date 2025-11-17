package com.fpstudio.stretchreminder.ui.composable.calendar.item

import androidx.compose.ui.graphics.Color
import com.fpstudio.stretchreminder.util.Constants.EMPTY

data class CalendarItemUiModel(
    val dayNumber: String,
    val dayOfTheMonth: String = EMPTY,
    val borderColor: Color,
    val backgroundColor: Color,
    val checked: Boolean = false,
)
