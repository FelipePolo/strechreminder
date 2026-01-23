package com.fpstudio.stretchreminder.ui.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyGoalUiState
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent

@Composable
fun DailyGoalCard(
    modifier: Modifier = Modifier,
    uiState: DailyGoalUiState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(TurquoiseAccent)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Circular Progress Indicator
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(80.dp)
        ) {
            androidx.compose.material3.CircularProgressIndicator(
                progress = { uiState.progress / 100f },
                modifier = Modifier.size(80.dp),
                color = Color.White,
                strokeWidth = 8.dp,
                trackColor = Color.White.copy(alpha = 0.3f)
            )
            Text(
                text = "${uiState.progress}%",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        // Right side content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.home_daily_goal_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.home_daily_goal_status, uiState.sessionsCompleted, uiState.totalSessions),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Text(
                text = stringResource(uiState.motivationalMessage),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Preview
@Composable
private fun DailyGoalCardPreview() {
    StretchReminderTheme {
        DailyGoalCard(
            uiState = DailyGoalUiState(
                progress = 100,
                sessionsCompleted = 2,
                totalSessions = 2,
                motivationalMessage = R.string.home_motivational_message
            )
        )
    }
}
