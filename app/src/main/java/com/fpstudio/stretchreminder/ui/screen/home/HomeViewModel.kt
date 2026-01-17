package com.fpstudio.stretchreminder.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.ui.composable.calendar.Calendar
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyGoalUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyStatsUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HeaderUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.fpstudio.stretchreminder.domain.usecase.GetRoutineStatsUseCase
import com.fpstudio.stretchreminder.domain.usecase.GetUserUseCase
import com.fpstudio.stretchreminder.domain.usecase.CalculateStreakUseCase
import com.fpstudio.stretchreminder.domain.usecase.CheckNetworkConnectivityUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class HomeViewModel(
    private val getRoutineStatsUseCase: GetRoutineStatsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val calculateStreakUseCase: CalculateStreakUseCase,
    private val checkNetworkConnectivityUseCase: CheckNetworkConnectivityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }
    
    fun checkInternetBeforeNavigation(onSuccess: () -> Unit) {
        val hasInternet = checkNetworkConnectivityUseCase()
        if (hasInternet) {
            onSuccess()
        } else {
            _uiState.update { it.copy(showNoInternetDialog = true) }
        }
    }
    
    fun hideNoInternetDialog() {
        _uiState.update { it.copy(showNoInternetDialog = false) }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val today = LocalDate.now()
            
            // Load user data
            val user = getUserUseCase()
            val userName = user?.name ?: "User"
            
            // Format date
            val dateFormatter = DateTimeFormatter.ofPattern("d MMM, yyyy", Locale.getDefault())
            val formattedDate = today.format(dateFormatter)
            
            // Load routine stats for current month
            val stats = getRoutineStatsUseCase(today.year, today.monthValue)
            
            // Calculate streak
            val streakStats = calculateStreakUseCase()

            _uiState.update { currentState ->
                currentState.copy(
                    headerState = HeaderUiState(
                        userName = userName,
                        formattedDate = formattedDate
                    ),
                    dailyGoalState = DailyGoalUiState(
                        sessionsCompleted = stats.routineCountToday,
                        totalSessions = 2, // Default goal of 2 sessions per day
                        progress = ((stats.routineCountToday.toFloat() / 2f) * 100).toInt().coerceAtMost(100)
                    ),
                    dailyStatsState = DailyStatsUiState(
                        stretchingTime = streakStats.totalDurationSeconds.toInt(), // Keep in seconds
                        stretchDays = streakStats.days
                    ),
                    calendarState = Calendar(
                        today = today,
                        markedDays = stats.completedDates
                    )
                )
            }
        }
    }
}
