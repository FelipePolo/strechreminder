package com.fpstudio.stretchreminder.ui.screen.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.screen.settings.contract.SettingsScreenContract.Workday
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent

@Composable
fun WorkdaySelector(
    selectedDays: Set<Workday>,
    onDayToggled: (Workday) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Workdays",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Workday.values().forEach { day ->
                DayCircle(
                    modifier = modifier
                        .weight(1f, fill = true)
                        .aspectRatio(1f),
                    letter = day.shortName,
                    isSelected = selectedDays.contains(day),
                    onClick = { onDayToggled(day) }
                )
            }
        }
    }
}

@Composable
private fun DayCircle(
    letter: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(if (isSelected) TurquoiseAccent else Color(0xFFF5F5F5))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            fontSize = 18.sp,
            color = if (isSelected) Color.White else Color.Gray,
            fontWeight = FontWeight.SemiBold
        )
    }
}
