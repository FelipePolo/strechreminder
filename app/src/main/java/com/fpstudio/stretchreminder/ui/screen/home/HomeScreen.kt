package com.fpstudio.stretchreminder.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.composable.button.StretchButton
import com.fpstudio.stretchreminder.ui.composable.button.StretchButtonUiModel
import com.fpstudio.stretchreminder.ui.screen.home.components.DailyGoalCard
import com.fpstudio.stretchreminder.ui.screen.home.components.Header
import com.fpstudio.stretchreminder.ui.screen.home.components.InfoCard
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyGoalUiState
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import com.fpstudio.stretchreminder.ui.screen.home.components.StreakInfoDialog
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDate

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onStretchButtonClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Auto-hide dialog when screen becomes active if internet is back
    LaunchedEffect(Unit) {
        if (uiState.showNoInternetDialog) {
            viewModel.hideNoInternetDialog()
        }
    }

    HomeContent(
        uiState = uiState,
        onCheckInternet = { viewModel.checkInternetBeforeNavigation(onStretchButtonClick) },
        onHideNoInternetDialog = viewModel::hideNoInternetDialog,
        onMenuClick = onMenuClick
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onCheckInternet: () -> Unit = {},
    onHideNoInternetDialog: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    var showStreakInfo by remember { mutableStateOf(false) }

    if (showStreakInfo) {
        StreakInfoDialog(onDismiss = { showStreakInfo = false })
    }

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
                    onClick = onCheckInternet
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
                when (uiState.dailyStatsState.stretchDays) {
                    0, 1 -> {
                        InfoCard(
                            modifier = Modifier.weight(1f),
                            title = "Streak",
                            value = "${uiState.dailyStatsState.stretchDays} Day",
                            onInfoClick = { showStreakInfo = true }
                        ) {
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.fire1
                                )
                            )
                            LottieAnimation(
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier.size(36.dp).offset(y = (5).dp)
                            )
                        }
                    }

                    2 -> {
                        InfoCard(
                            modifier = Modifier.weight(1f),
                            title = "Streak",
                            value = "${uiState.dailyStatsState.stretchDays} Day",
                            onInfoClick = { showStreakInfo = true }
                        ) {
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.fire2
                                )
                            )
                            LottieAnimation(
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    3 -> {
                        InfoCard(
                            modifier = Modifier.weight(1f),
                            title = "Streak",
                            value = "${uiState.dailyStatsState.stretchDays} Day",
                            onInfoClick = { showStreakInfo = true }
                        ) {
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.fire3
                                )
                            )
                            LottieAnimation(
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    else -> {
                        InfoCard(
                            modifier = Modifier.weight(1f),
                            title = "Streak",
                            value = "${uiState.dailyStatsState.stretchDays} Day",
                            onInfoClick = { showStreakInfo = true }
                        ) {
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.fire4
                                )
                            )
                            LottieAnimation(
                                composition = composition,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }


                InfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Duration",
                    value = if (uiState.dailyStatsState.stretchingTime < 60) {
                        "${uiState.dailyStatsState.stretchingTime} sec"
                    } else {
                        "${uiState.dailyStatsState.stretchingTime / 60} min"
                    },
                    icon = R.drawable.ic_clock
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
            
            // No Internet Dialog
            if (uiState.showNoInternetDialog) {
                com.fpstudio.stretchreminder.ui.composable.NoInternetConnectionDialog(
                    onRetry = onCheckInternet,
                    onDismiss = onHideNoInternetDialog
                )
            }
        }
    }
}

@Preview(group = "Streak", name = "Streak 0 - 0 min")
@Composable
fun HomeScreenPreviewStreak0() {
    StretchReminderTheme {
        HomeContent(
            uiState = HomeUiState(
                headerState = HeaderUiState("Juan", "29 Nov, 2025"),
                dailyGoalState = DailyGoalUiState(0, 0, 2, "Let's start!"),
                dailyStatsState = DailyStatsUiState(0, 0),
                calendarState = Calendar(LocalDate.now(), emptyList())
            )
        )
    }
}

@Preview(group = "Streak", name = "Streak 1 - 15 min")
@Composable
fun HomeScreenPreviewStreak1() {
    StretchReminderTheme {
        HomeContent(
            uiState = HomeUiState(
                headerState = HeaderUiState("Juan", "29 Nov, 2025"),
                dailyGoalState = DailyGoalUiState(50, 1, 2, "Good start!"),
                dailyStatsState = DailyStatsUiState(900, 1),
                calendarState = Calendar(LocalDate.now(), listOf(LocalDate.now()))
            )
        )
    }
}

@Preview(group = "Streak", name = "Streak 2 - 30 min")
@Composable
fun HomeScreenPreviewStreak2() {
    StretchReminderTheme {
        HomeContent(
            uiState = HomeUiState(
                headerState = HeaderUiState("Juan", "29 Nov, 2025"),
                dailyGoalState = DailyGoalUiState(100, 2, 2, "On fire!"),
                dailyStatsState = DailyStatsUiState(1800, 2),
                calendarState = Calendar(
                    LocalDate.now(),
                    listOf(LocalDate.now(), LocalDate.now().minusDays(1))
                )
            )
        )
    }
}

@Preview(group = "Streak", name = "Streak 3 - 45 min")
@Composable
fun HomeScreenPreviewStreak3() {
    StretchReminderTheme {
        HomeContent(
            uiState = HomeUiState(
                headerState = HeaderUiState("Juan", "29 Nov, 2025"),
                dailyGoalState = DailyGoalUiState(100, 2, 2, "Unstoppable!"),
                dailyStatsState = DailyStatsUiState(2700, 3),
                calendarState = Calendar(
                    LocalDate.now(),
                    listOf(
                        LocalDate.now(),
                        LocalDate.now().minusDays(1),
                        LocalDate.now().minusDays(2)
                    )
                )
            )
        )
    }
}

@Preview(group = "Streak", name = "Streak 4+ - 60 min")
@Composable
fun HomeScreenPreviewStreak4() {
    StretchReminderTheme {
        HomeContent(
            uiState = HomeUiState(
                headerState = HeaderUiState("Juan", "29 Nov, 2025"),
                dailyGoalState = DailyGoalUiState(100, 2, 2, "Legendary!"),
                dailyStatsState = DailyStatsUiState(3600, 10),
                calendarState = Calendar(
                    LocalDate.now(),
                    listOf(
                        LocalDate.now(),
                        LocalDate.now().minusDays(1),
                        LocalDate.now().minusDays(2),
                        LocalDate.now().minusDays(3)
                    )
                )
            )
        )
    }
}