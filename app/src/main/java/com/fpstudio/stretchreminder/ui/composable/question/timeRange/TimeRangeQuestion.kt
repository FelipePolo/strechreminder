package com.fpstudio.stretchreminder.ui.composable.question.timeRange

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.theme.Green_primary
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.theme.Gray2
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel
import com.fpstudio.stretchreminder.ui.screen.settings.components.TimePickerDialog
import com.fpstudio.stretchreminder.util.Constants.GUION

@Composable
fun TimeRangeQuestion(
    model: QuestionUiModel.TimeRange,
    onSelect: (QuestionSelectionUiModel.TimeRangeSelectionUiModel) -> Unit
) {
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

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
                    text = stringResource(R.string.time_range_start_time),
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
                        onClick = { showStartTimePicker = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        elevation = null,
                        contentPadding = PaddingValues(vertical = 12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = model.startTime,
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
                    text = stringResource(R.string.time_range_end_time),
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
                        onClick = { showEndTimePicker = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        elevation = null,
                        contentPadding = PaddingValues(vertical = 12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = model.endTime,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }

    // Show custom TimePickerDialog for start time
    if (showStartTimePicker) {
        TimePickerDialog(
            initialTime = model.startTime,
            onDismiss = { showStartTimePicker = false },
            onTimeSelected = { selectedTime ->
                onSelect(
                    QuestionSelectionUiModel.TimeRangeSelectionUiModel(
                        startTime = selectedTime,
                        endTime = model.endTime
                    )
                )
                showStartTimePicker = false
            }
        )
    }

    // Show custom TimePickerDialog for end time
    if (showEndTimePicker) {
        TimePickerDialog(
            initialTime = model.endTime,
            onDismiss = { showEndTimePicker = false },
            onTimeSelected = { selectedTime ->
                onSelect(
                    QuestionSelectionUiModel.TimeRangeSelectionUiModel(
                        startTime = model.startTime,
                        endTime = selectedTime
                    )
                )
                showEndTimePicker = false
            }
        )
    }
}