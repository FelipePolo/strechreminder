package com.fpstudio.stretchreminder.ui.screen.routine.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ContinueButton(
    enabled: Boolean,
    selectedCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = Color(0xFFE0E0E0),
                contentColor = Color.White,
                disabledContentColor = Color.Gray
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = if (enabled) 4.dp else 0.dp
            )
        ) {
            Text(
                text = if (selectedCount > 0) {
                    "Continue with $selectedCount exercise${if (selectedCount > 1) "s" else ""}"
                } else {
                    "Select at least 1 exercise"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
