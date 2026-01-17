package com.fpstudio.stretchreminder.ui.composable.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarCard(
    modifier: Modifier = Modifier,
    model: Calendar
) {
    // State to track the currently displayed month
    var displayedDate by remember { mutableStateOf(model.today) }
    
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with title, month/year and navigation
        CalendarHeader(
            currentDate = displayedDate,
            onPreviousMonth = {
                displayedDate = displayedDate.minusMonths(1)
            },
            onNextMonth = {
                displayedDate = displayedDate.plusMonths(1)
            }
        )
        
        // Days of week
        DaysOfWeekHeader()
        
        // Calendar grid
        CalendarGrid(
            currentDate = displayedDate,
            actualToday = model.today,
            markedDays = model.markedDays
        )
        
        // Legend
        CalendarLegend()
    }
}

@Composable
private fun CalendarHeader(
    currentDate: LocalDate,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Calendar",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "${currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentDate.year}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            
            IconButton(onClick = onPreviousMonth) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous month",
                    tint = TurquoiseAccent
                )
            }
            
            IconButton(onClick = onNextMonth) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next month",
                    tint = TurquoiseAccent
                )
            }
        }
    }
}

@Composable
private fun DaysOfWeekHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    currentDate: LocalDate,
    actualToday: LocalDate,
    markedDays: List<LocalDate>
) {
    val yearMonth = YearMonth.of(currentDate.year, currentDate.month)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(currentDate.year, currentDate.month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Sunday = 0
    
    val previousMonth = currentDate.minusMonths(1)
    val daysInPrevMonth = YearMonth.of(previousMonth.year, previousMonth.month).lengthOfMonth()
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var dayCounter = 1
        var nextMonthCounter = 1
        
        // Calculate total weeks needed
        val totalCells = firstDayOfWeek + daysInMonth
        val totalWeeks = (totalCells + 6) / 7
        
        for (week in 0 until totalWeeks) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (dayOfWeek in 0..6) {
                    val cellIndex = week * 7 + dayOfWeek
                    
                    when {
                        // Previous month days
                        cellIndex < firstDayOfWeek -> {
                            val day = daysInPrevMonth - firstDayOfWeek + cellIndex + 1
                            DayCell(
                                day = day,
                                isCurrentMonth = false,
                                isToday = false,
                                isMarked = false
                            )
                        }
                        // Current month days
                        dayCounter <= daysInMonth -> {
                            val dayDate = LocalDate.of(currentDate.year, currentDate.month, dayCounter)
                            // Check if this day is actually today
                            val isToday = dayDate == actualToday
                            // Check if this specific date is marked
                            val isMarked = markedDays.contains(dayDate)
                            DayCell(
                                day = dayCounter,
                                isCurrentMonth = true,
                                isToday = isToday,
                                isMarked = isMarked
                            )
                            dayCounter++
                        }
                        // Next month days
                        else -> {
                            DayCell(
                                day = nextMonthCounter,
                                isCurrentMonth = false,
                                isToday = false,
                                isMarked = false
                            )
                            nextMonthCounter++
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCell(
    day: Int,
    isCurrentMonth: Boolean,
    isToday: Boolean,
    isMarked: Boolean
) {
    Box(
        modifier = Modifier
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        // Filled circle for marked/completed days
        if (isMarked) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(TurquoiseAccent)
            )
        }
        
        // Bordered circle for today (only border, no fill)
        if (isToday) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .border(
                        width = 2.dp,
                        color = TurquoiseAccent,
                        shape = CircleShape
                    )
            )
        }
        
        // Day number
        Text(
            text = day.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = when {
                isMarked -> Color.White  // White text on green background
                !isCurrentMonth -> Color.LightGray
                else -> Color.Black
            },
            fontWeight = if (isToday || isMarked) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CalendarLegend() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Today indicator
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .border(
                        2.dp,
                        color = TurquoiseAccent,
                        shape = CircleShape
                    )
                    .size(12.dp)

            )
            Text(
                text = "Today",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Stretched indicator
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(TurquoiseAccent)
            )
            Text(
                text = "Stretched",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun CalendarCardPreview() {
    CalendarCard(
        model = Calendar(
            today = LocalDate.of(2026, 1, 2),
            markedDays = listOf(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 8),
                LocalDate.of(2026, 1, 15),
                LocalDate.of(2026, 1, 22)
            )
        )
    )
}
