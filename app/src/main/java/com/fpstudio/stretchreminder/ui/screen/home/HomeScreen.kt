package com.fpstudio.stretchreminder.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.composable.calendar.Calendar
import com.fpstudio.stretchreminder.ui.composable.calendar.CalendarCard
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyStatsUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HeaderUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HomeUiState
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.stringResource
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.home.components.DailyGoalCard
import com.fpstudio.stretchreminder.ui.screen.home.components.Header
import com.fpstudio.stretchreminder.ui.screen.home.components.InfoCard
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyGoalUiState
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_1
import com.fpstudio.stretchreminder.ui.theme.Green_gradient_2
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import java.time.LocalDate

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onStretchButtonClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeContent(
        uiState = uiState,
        onStretchButtonClick = onStretchButtonClick,
        onMenuClick = onMenuClick
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onStretchButtonClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = {
            Column {
                StretchButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    state = StretchButtonUiModel.Default(
                        text = "Build Your Routine",
                        isVisible = true,
                        backgroundColor = TurquoiseAccent
                    ),
                    onClick = onStretchButtonClick
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            // Header without gradient background
            Header(
                uiState = uiState.headerState,
                onMenuClick = onMenuClick
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Daily Goal Card
            DailyGoalCard(
                uiState = uiState.dailyGoalState,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Info Cards Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Streak",
                    value = "${uiState.dailyStatsState.stretchDays} Day",
                    icon = R.drawable.fire
                )
                InfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Duration",
                    value = "${uiState.dailyStatsState.stretchingTime} min",
                    icon = R.drawable.clock
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calendar
            CalendarCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                model = uiState.calendarState
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun HomeScreenPreview() {
    StretchReminderTheme {
        val previewUiState = HomeUiState(
            headerState = HeaderUiState(
                userName = "Juan",
                formattedDate = "29 Nov, 2025"
            ),
            dailyGoalState = DailyGoalUiState(
                progress = 100,
                sessionsCompleted = 2,
                totalSessions = 2,
                motivationalMessage = "You're doing great, keep it up!"
            ),
            dailyStatsState = DailyStatsUiState(
                stretchingTime = 1,
                stretchDays = 1
            ),
            calendarState = Calendar(
                today = LocalDate.of(2025, 12, 27),
                markedDays = listOf(1, 2, 8, 9, 10, 15, 16, 22, 23, 27)
            )
        )

        HomeContent(uiState = previewUiState)
    }
}