package com.fpstudio.stretchreminder.ui.composable.question.notificationQuestion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.question.common.QuestionTitle
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionSelectionUiModel

@Composable
fun NotificationPermissionQuestion(
    model: QuestionUiModel.NotificationPermission,
    onSelection: (QuestionSelectionUiModel.BooleanSelectionUiModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        QuestionTitle(model)
        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(8.dp)
                .align(alignment = Alignment.CenterHorizontally)
            ,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card Title
                Text(
                    text = "Stretch Reminder would like to send you notifications",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Card Description
                Text(
                    text = "Notifications may include alerts, sounds, and icon badges, which can be configured in Settings.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Don't Allow Button
                    OutlinedButton(
                        onClick = { onSelection(QuestionSelectionUiModel.BooleanSelectionUiModel(false)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Don't Allow",
                            color = Color.Black
                        )
                    }

                    // Allow Button
                    Button(
                        onClick = { onSelection(QuestionSelectionUiModel.BooleanSelectionUiModel(true)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Allow",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
