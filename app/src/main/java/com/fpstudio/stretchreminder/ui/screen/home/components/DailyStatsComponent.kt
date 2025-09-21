package com.fpstudio.stretchreminder.ui.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.statsitem.StatsItem
import com.fpstudio.stretchreminder.ui.composable.statsitem.StatsItemUiModel
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyStatsUiState
import com.fpstudio.stretchreminder.ui.theme.Gray3
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme

@Composable
fun DailyStatsComponent(
    uiState: DailyStatsUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(60.dp),
                    progress = { 0.75F }
                )

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "75%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Take a breath, take it slow -- strech with ease",
                    fontSize = 10.sp,
                    color = Gray3
                )
                Text(
                    text = "Your Daily Stretch Plan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatsItem(
                statsItemUiModel = StatsItemUiModel(
                    title = "Today's\nstretching time",
                    value = "0",
                    valueName = "Min",
                    iconId = R.drawable.ic_clock,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyStatsComponentPreview() {
    StretchReminderTheme {
        DailyStatsComponent(
            uiState = DailyStatsUiState(
                stretchingTime = 0,
                stretchDays = 0
            ),
        )
    }
}
