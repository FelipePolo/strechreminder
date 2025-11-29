package com.fpstudio.stretchreminder.ui.composable.calendar.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.theme.Gray2
import com.fpstudio.stretchreminder.ui.theme.Gray3
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_1
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_2


@Composable
fun CalendarItemElement(
    modifier: Modifier = Modifier,
    model: CalendarItemUiModel
) {
    if (model.dayNumber.isNotEmpty()) {
        Box (modifier = modifier) {
            Column(
                modifier = Modifier
                    .border(1.dp, model.borderColor, RoundedCornerShape(8.dp))
                    .background(model.backgroundColor)
            ) {
                if (model.dayOfTheMonth.isNotEmpty()) {
                    val gradient = Brush.horizontalGradient(
                        startX = 1F,
                        colors = listOf(Green_gradient_1, Green_gradient_2),
                        tileMode = TileMode.Decal
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(gradient)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = model.dayOfTheMonth,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }
                Box (modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = model.dayNumber,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }

            if (model.checked) {
                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.TopEnd).offset(y = (-4).dp, x = (+4).dp),
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "Checked"
                )
            }
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////
////////////////// PREVIEWS  ///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItem() {
    CalendarItemElement(
        model = CalendarItemUiModel(
            dayNumber = "1",
            dayOfTheMonth = "MON",
            borderColor = Gray3,
            backgroundColor = Color.White
        )
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItemDisabled() {
    CalendarItemElement(
        model = CalendarItemUiModel(
            dayNumber = "20",
            borderColor = Gray3,
            backgroundColor = Gray2
        )
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItemSelected() {
    CalendarItemElement(
        model = CalendarItemUiModel(
            dayNumber = "19",
            borderColor = Green_gradient_1,
            backgroundColor = Color.White
        )
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItemChecked() {
    Box (modifier = Modifier.size(80.dp).padding(5.dp)) {
        CalendarItemElement(
            model = CalendarItemUiModel(
                dayNumber = "19",
                borderColor = Green_gradient_1,
                backgroundColor = Color.White,
                checked = true
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewCalendarItemWeekChecked() {
    Box (modifier = Modifier.size(80.dp).padding(5.dp)) {
        CalendarItemElement(
            model = CalendarItemUiModel(
                dayNumber = "1",
                dayOfTheMonth = "MON",
                borderColor = Gray3,
                backgroundColor = Color.White,
                checked = true
            )
        )
    }
}

