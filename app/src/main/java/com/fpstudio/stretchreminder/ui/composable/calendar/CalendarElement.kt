package com.fpstudio.stretchreminder.ui.composable.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.composable.calendar.item.CalendarItemElement
import com.fpstudio.stretchreminder.ui.composable.calendar.item.CalendarItemUiModel
import com.fpstudio.stretchreminder.ui.theme.Gray3
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_1
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarElement(
    modifier: Modifier = Modifier,
    model: Calendar
) {

    val currentDate = model.today
    val yearMonth = YearMonth.of(currentDate.year, currentDate.month)
    val daysInMonth = yearMonth.lengthOfMonth()

    val firstDay = LocalDate.of(currentDate.year, currentDate.month, 1).dayOfWeek
    val leadingEmptyDays = firstDay.toMondayFirstIndex()

    val previousMonth = currentDate.minusMonths(1)
    val daysInPrevMonth = YearMonth.of(previousMonth.year, previousMonth.month).lengthOfMonth()

    val totalCells = leadingEmptyDays + daysInMonth
    val totalWeeks = (totalCells + 6) / 7

    var dayCounter = 1
    var nextMonthCounter = 1

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        for (week in 0 until totalWeeks) {

            CalendarWeekRow(
                modifier = modifier.weight(1f),
                week = week,
                leadingEmptyDays = leadingEmptyDays,
                daysInPrevMonth = daysInPrevMonth,
                currentDate = currentDate,
                daysInMonth = daysInMonth,
                dayCounter = dayCounter,
                nextMonthCounter = nextMonthCounter,
                markedDays = model.markedDays,
                onDayCounterChange = { dayCounter = it },
                onNextMonthCounterChange = { nextMonthCounter = it }
            )
        }
    }
}

@Composable
fun CalendarWeekRow(
    modifier: Modifier = Modifier,
    week: Int,
    leadingEmptyDays: Int,
    daysInPrevMonth: Int,
    currentDate: LocalDate,
    daysInMonth: Int,
    dayCounter: Int,
    nextMonthCounter: Int,
    markedDays: List<Int>,
    onDayCounterChange: (Int) -> Unit,
    onNextMonthCounterChange: (Int) -> Unit,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        var mutableDayCounter = dayCounter
        var mutableNextCounter = nextMonthCounter

        for (dayOfWeek in 0..6) {

            val cellIndex = week * 7 + dayOfWeek

            CalendarDayCell(
                modifier = Modifier.weight(1f),
                cellIndex = cellIndex,
                leadingEmptyDays = leadingEmptyDays,
                daysInPrevMonth = daysInPrevMonth,
                currentDate = currentDate,
                week = week,
                dayOfWeek = dayOfWeek,
                dayCounter = mutableDayCounter,
                nextMonthCounter = mutableNextCounter,
                daysInMonth = daysInMonth,
                markedDays = markedDays,
                onDayCounterConsumed = {
                    mutableDayCounter++
                },
                onNextMonthCounterConsumed = {
                    mutableNextCounter++
                }
            )
        }

        onDayCounterChange(mutableDayCounter)
        onNextMonthCounterChange(mutableNextCounter)
    }
}

@Composable
fun CalendarDayCell(
    modifier: Modifier = Modifier,
    cellIndex: Int,
    leadingEmptyDays: Int,
    daysInPrevMonth: Int,
    currentDate: LocalDate,
    week: Int,
    dayOfWeek: Int,
    dayCounter: Int,
    nextMonthCounter: Int,
    daysInMonth: Int,
    markedDays: List<Int>,
    onDayCounterConsumed: () -> Unit,
    onNextMonthCounterConsumed: () -> Unit,
) {

    when {
        // Día del mes anterior
        cellIndex < leadingEmptyDays -> {
            PreviousMonthDay(
                modifier = modifier,
                cellIndex = cellIndex,
                leadingEmptyDays = leadingEmptyDays,
                daysInPrevMonth = daysInPrevMonth,
                previousMonthDate = currentDate.minusMonths(1),
                markedDays = markedDays,
            )
        }

        // Día del mes actual
        dayCounter <= daysInMonth -> {
            CurrentMonthDay(
                modifier = modifier,
                day = dayCounter,
                currentDate = currentDate,
                week = week,
                markedDays = markedDays,
            )
            onDayCounterConsumed()
        }

        // Día del mes siguiente
        else -> {
            NextMonthDay(
                modifier = modifier,
                day = nextMonthCounter
            )
            onNextMonthCounterConsumed()
        }
    }
}

@Composable
fun PreviousMonthDay(
    modifier: Modifier = Modifier,
    cellIndex: Int,
    leadingEmptyDays: Int,
    daysInPrevMonth: Int,
    previousMonthDate: LocalDate,
    markedDays: List<Int>
) {
    val day = (daysInPrevMonth - leadingEmptyDays + 1) + cellIndex
    val date = LocalDate.of(previousMonthDate.year, previousMonthDate.month, day)

    CalendarItemElement(
        modifier = modifier,
        model = CalendarItemUiModel(
            dayNumber = day.toString(),
            dayOfTheMonth = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            borderColor = Gray3,
            backgroundColor = Color.White,
            checked = markedDays.contains(day)
        )
    )
}

@Composable
fun CurrentMonthDay(
    modifier: Modifier = Modifier,
    day: Int,
    currentDate: LocalDate,
    week: Int,
    markedDays: List<Int>
) {
    val date = LocalDate.of(currentDate.year, currentDate.month, day)

    CalendarItemElement(
        modifier = modifier,
        model = CalendarItemUiModel(
            dayNumber = day.toString(),
            dayOfTheMonth = if (week == 0)
                date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            else "",
            borderColor = if (day == currentDate.dayOfMonth) Green_gradient_1 else Gray3,
            backgroundColor = Color.White,
            checked = markedDays.contains(day)
        )
    )
}

@Composable
fun NextMonthDay(
    modifier: Modifier = Modifier,
    day: Int
) {
    CalendarItemElement(
        modifier = modifier,
        model = CalendarItemUiModel(
            dayNumber = day.toString(),
            dayOfTheMonth = "",
            borderColor = Gray3,
            backgroundColor = Color.White
        )
    )
}

fun DayOfWeek.toMondayFirstIndex(): Int {
    return (this.value + 6) % 7
}


/// PREVIEWS
// genera distintas previews con diferentes fechas
@Preview
@Composable
fun CalendarElementPreviewJanuary() {
    CalendarElement(
        modifier = Modifier,
        model = Calendar(
            today = LocalDate.of(2023, 1, 1),
            markedDays = listOf(1, 2, 3)
        )
    )
}

@Preview
@Composable
fun CalendarElementPreviewFebruary() {
    CalendarElement(
        modifier = Modifier,
        model = Calendar(
            today = LocalDate.of(2023, 2, 1),
            markedDays = listOf(1, 2, 3)
        )
    )
}

@Preview
@Composable
fun CalendarElementPreviewSeptember() {
    CalendarElement(
        modifier = Modifier,
        model = Calendar(
            today = LocalDate.of(2025, 9, 1),
            markedDays = listOf(1, 10, 24, 30)
        )
    )
}

@Preview
@Composable
fun CalendarElementPreviewMarch() {
    CalendarElement(
        modifier = Modifier,
        model = Calendar(
            today = LocalDate.of(2023, 3, 1),
            markedDays = listOf(1, 2, 3)
        )
    )
}


@Preview
@Composable
fun CalendarElementPreviewCurrent() {
    CalendarElement(
        modifier = Modifier,
        model = Calendar(
            today = LocalDate.now(),
            markedDays = listOf(1, 2, 3)
        )
    )
}

