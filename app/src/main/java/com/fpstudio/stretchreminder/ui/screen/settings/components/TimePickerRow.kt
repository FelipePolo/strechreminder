package com.fpstudio.stretchreminder.ui.screen.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.fpstudio.stretchreminder.R

@Composable
fun TimePickerRow(
    startTime: String,
    endTime: String,
    onStartTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_routine_schedule),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TimePickerItem(
                label = stringResource(R.string.settings_time_start),
                time = startTime,
                onClick = onStartTimeClick,
                modifier = Modifier.weight(1f)
            )
            
            Text(
                text = stringResource(R.string.settings_time_to),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            
            TimePickerItem(
                label = stringResource(R.string.settings_time_end),
                time = endTime,
                onClick = onEndTimeClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TimePickerItem(
    label: String,
    time: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "🕐",
            modifier = Modifier.size(20.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}
