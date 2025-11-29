package com.fpstudio.stretchreminder.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.composable.calendar.Calendar
import com.fpstudio.stretchreminder.ui.composable.calendar.CalendarElement
import com.fpstudio.stretchreminder.ui.screen.home.components.DailyStatsComponent
import com.fpstudio.stretchreminder.ui.screen.home.components.HeaderComponent
import com.fpstudio.stretchreminder.ui.screen.home.components.StretchButton
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyStatsUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HeaderUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HomeUiState
import com.fpstudio.stretchreminder.ui.theme.Gray
import com.fpstudio.stretchreminder.ui.theme.StretchReminderTheme
import java.time.Month
import org.koin.androidx.compose.koinViewModel
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
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                StretchButton(
                    onClick = onStretchButtonClick
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HeaderComponent(
                modifier = Modifier.padding(horizontal = 8.dp),
                uiState = uiState.headerState
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                color = Gray
            )

            DailyStatsComponent(
                modifier = Modifier.padding(horizontal = 8.dp),
                uiState = uiState.dailyStatsState
            )

            CalendarElement(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 8.dp),
                model = uiState.calendarState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    StretchReminderTheme {
        val previewUiState = HomeUiState(
            headerState = HeaderUiState(
                dayOfWeek = "sunday"
            ),
            dailyStatsState = DailyStatsUiState(
                stretchingTime = 0,
                stretchDays = 0
            ),
            calendarState = Calendar(
                today = LocalDate.now(),
                markedDays = listOf(10, 24)
            )
        )

        HomeContent(uiState = previewUiState)
    }
}