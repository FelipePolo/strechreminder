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
import com.fpstudio.stretchreminder.ui.composable.calendar.CalendarElement
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyStatsUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HeaderUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HomeUiState
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
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
import java.time.LocalDate

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onStretchButtonClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeContent(
        uiState = uiState,
        onStretchButtonClick = onStretchButtonClick
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onStretchButtonClick: () -> Unit = {}
) {
    val gradient = Brush.horizontalGradient(
        startX = 1F,
        colors = listOf(Green_gradient_1, Green_gradient_2),
        tileMode = TileMode.Decal
    )
    Scaffold(
        bottomBar = {
            Column {
                StretchButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 16.dp),
                    state = StretchButtonUiModel.Default(
                        text = stringResource(R.string.intro_button_text),
                        isVisible = true,
                        backgroundColor = Green_gradient_1
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
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF0F4F3), Color.White),
                        startY = 0f,
                        endY = 1f,
                    ),
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(gradient)
                    .padding(16.dp)
            ) {
                Header(uiState = uiState.headerState)
                Spacer(modifier = Modifier.height(16.dp))
                DailyGoalCard(uiState = uiState.dailyGoalState)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Todays streching",
                    value = "${uiState.dailyStatsState.stretchingTime} min",
                    icon = R.drawable.clock
                )
                InfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Stretching streak",
                    value = "${uiState.dailyStatsState.stretchDays} Days",
                    icon = R.drawable.fire
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            CalendarElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(horizontal = 8.dp),
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
                userName = "Pipe",
                formattedDate = "29 Nov, 2025"
            ),
            dailyGoalState = DailyGoalUiState(
                progress = 20
            ),
            dailyStatsState = DailyStatsUiState(
                stretchingTime = 2,
                stretchDays = 2
            ),
            calendarState = Calendar(
                today = LocalDate.of(2025, 11, 16),
                markedDays = listOf(1, 2, 10, 15, 16, 22, 23)
            )
        )

        HomeContent(uiState = previewUiState)
    }
}