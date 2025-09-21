package com.fpstudio.stretchreminder.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fpstudio.stretchreminder.ui.screen.home.model.CalendarUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.DailyStatsUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HeaderUiState
import com.fpstudio.stretchreminder.ui.screen.home.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val today = LocalDate.now()
            val dayOfWeek = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()).lowercase()
            
            _uiState.update { currentState ->
                currentState.copy(
                    headerState = HeaderUiState(
                        dayOfWeek = dayOfWeek
                    ),
                    dailyStatsState = DailyStatsUiState(
                        stretchingTime = 0,
                        stretchDays = 0
                    ),
                    calendarState = CalendarUiState(
                        currentMonth = today.month,
                        currentYear = today.year,
                        selectedDay = today.dayOfMonth,
                        markedDays = emptyList()
                    )
                )
            }
        }
    }
}
