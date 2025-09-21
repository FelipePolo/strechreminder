package com.fpstudio.stretchreminder.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.screen.home.model.CalendarUiState
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarComponent(
    uiState: CalendarUiState
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = uiState.currentMonth.getDisplayName(TextStyle.FULL, Locale.getDefault()),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        val daysInMonth = YearMonth.of(uiState.currentYear, uiState.currentMonth).lengthOfMonth()
        val weeks = (daysInMonth + 6) / 7 // Ceiling division to get number of weeks

        for (week in 0 until weeks) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (dayOfWeek in 0..6) {
                    val day = week * 7 + dayOfWeek + 1
                    if (day <= daysInMonth) {
                        DayItem(
                            day = day,
                            isSelected = day == uiState.selectedDay,
                            isMarked = day in uiState.markedDays
                        )
                    } else {
                        // Empty space for days beyond the month
                        Box(modifier = Modifier.size(36.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun DayItem(
    day: Int,
    isSelected: Boolean,
    isMarked: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(36.dp)
            .then(
                if (isSelected) {
                    Modifier
                        .border(
                            width = 2.dp,
                            color = Color(0xFF4AEADD),
                            shape = RoundedCornerShape(8.dp)
                        )
                } else {
                    Modifier
                }
            )
    ) {
        Text(
            text = day.toString(),
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        if (isMarked) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 2.dp)
                    .background(Color(0xFF4AEADD), CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarComponentPreview() {
    StretchReminderTheme {
        CalendarComponent(
            uiState = CalendarUiState(
                currentMonth = Month.JULY,
                currentYear = 2023,
                selectedDay = 27,
                markedDays = listOf(5, 12, 19)
            )
        )
    }
}
