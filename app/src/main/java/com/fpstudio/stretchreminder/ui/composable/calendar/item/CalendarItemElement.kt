package com.fpstudio.stretchreminder.ui.composable.calendar.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.theme.Gray2
import com.fpstudio.stretchreminder.ui.theme.Gray3
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_1
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_2
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

@Composable
fun CalendarItemElement(
    modifier: Modifier = Modifier,
    model: CalendarItemUiModel
) {
    if (model.dayNumber.isNotEmpty()) {
        Box(modifier = modifier) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Day of week header (only shown in first week)
                if (model.dayOfTheMonth.isNotEmpty()) {
                    val gradient = Brush.horizontalGradient(
                        startX = 1F,
                        colors = listOf(Green_gradient_1, Green_gradient_2),
                        tileMode = TileMode.Decal
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                2.dp,
                                gradient,
                                RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp),
                            )
                            .padding(2.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .background(gradient)
                                .fillMaxWidth(),
                            text = model.dayOfTheMonth,
                            textAlign = TextAlign.Center,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }
                
                // Day number with circle indicators
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (isWeekend(model.dayNumber.toInt(), model.currentMonth)) {
                                Modifier.background(
                                    color = Gray2,
                                    shape = if (model.dayOfTheMonth.isEmpty()) 
                                        RoundedCornerShape(8.dp) 
                                    else 
                                        RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
                                )
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Background circle for completed days (filled green circle)
                    if (model.checked) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    Green_gradient_1,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                        )
                    }
                    
                    // Border circle for current day (empty circle with border)
                    if (model.isCurrentDay) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .border(
                                    2.dp,
                                    Green_gradient_1,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                        )
                    }
                    
                    // Day number text
                    Text(
                        text = model.dayNumber,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (model.checked) Color.White else model.dayTextColor
                    )
                }
            }
        }
    }
}

private fun isWeekend(day: Int, month: Month): Boolean {
    try {
        val weekendDays = listOf(
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
        )
        return weekendDays.contains(
            LocalDate.of(LocalDate.now().year, month, day).dayOfWeek
        )
    } catch (e: Exception) {
        return false
    }
}

////////////////////////////////////////////////////////////////////////////////////
////////////////// PREVIEWS  ///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItem() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(5.dp)
    ) {
        CalendarItemElement(
            model = CalendarItemUiModel(
                dayNumber = "1",
                dayOfTheMonth = "MON",
                dayTextColor = Gray2,
                borderColor = Gray3,
                backgroundColor = Color.White,
                currentMonth = Month.DECEMBER,
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItemDisabled() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(5.dp)
    ) {
        CalendarItemElement(
            model = CalendarItemUiModel(
                dayNumber = "20",
                borderColor = Gray3,
                backgroundColor = Gray2,
                currentMonth = Month.DECEMBER,
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItemSelected() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(5.dp)
    ) {
        CalendarItemElement(
            model = CalendarItemUiModel(
                dayNumber = "19",
                borderColor = Green_gradient_1,
                backgroundColor = Color.White,
                currentMonth = Month.DECEMBER,
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItemChecked() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(5.dp)
    ) {
        CalendarItemElement(
            model = CalendarItemUiModel(
                dayNumber = "19",
                borderColor = Green_gradient_1,
                backgroundColor = Color.White,
                checked = true,
                currentMonth = Month.DECEMBER,
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItemWeekChecked() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(5.dp)
    ) {
        CalendarItemElement(
            model = CalendarItemUiModel(
                dayNumber = "1",
                dayOfTheMonth = "MON",
                dayTextColor = Gray2,
                borderColor = Gray3,
                backgroundColor = Color.White,
                checked = true,
                currentMonth = Month.DECEMBER,
            )
        )
    }
}

