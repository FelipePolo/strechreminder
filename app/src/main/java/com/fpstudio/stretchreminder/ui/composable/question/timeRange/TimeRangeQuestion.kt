package com.fpstudio.stretchreminder.ui.composable.question.timeRange

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.theme.Green_primary
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.theme.Gray2
import android.app.TimePickerDialog
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.util.Constants.GUION
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TimeRangeQuestion(model: QuestionUiModel.TimeRange, onSelect: (QuestionSelectionUiModel.TimeRangeSelectionUiModel) -> Unit) {
    val context = LocalContext.current
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Question title
        Spacer(modifier = Modifier.height(12.dp))
        QuestionTitle(model)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.start),
                    color = Green_primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Gray2,
                    modifier = Modifier.width(120.dp)
                ) {
                    Button(
                        onClick = {
                            showTimePicker(context) { calendar ->
                                onSelect(QuestionSelectionUiModel.TimeRangeSelectionUiModel(calendar, model.endTime))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        elevation = null,
                        contentPadding = PaddingValues(vertical = 12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = timeFormat.format(model.startTime.time),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Text(
                text = GUION,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.end),
                    color = Green_primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Gray2,
                    modifier = Modifier.width(120.dp)
                ) {
                    Button(
                        onClick = {
                            showTimePicker(context) { calendar ->
                                onSelect(QuestionSelectionUiModel.TimeRangeSelectionUiModel(model.startTime, calendar))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        elevation = null,
                        contentPadding = PaddingValues(vertical = 12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = timeFormat.format(model.endTime.time),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

private fun showTimePicker(context: Context, onComplete: (Calendar) -> Unit) {
    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val updatedCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
            }

            onComplete(updatedCalendar)
        },
        0,
        0,
        false
    )
    timePicker.show()
}