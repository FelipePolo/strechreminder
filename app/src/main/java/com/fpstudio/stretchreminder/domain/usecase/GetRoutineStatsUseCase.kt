package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.repository.RoutineSessionRepository
import java.time.LocalDate

data class RoutineStats(
    val completedDates: List<LocalDate>, // Full dates with completions (changed from day numbers)
    val totalDurationToday: Long, // Total seconds stretched today
    val routineCountToday: Int // Number of routines completed today
)

class GetRoutineStatsUseCase(
    private val repository: RoutineSessionRepository
) {
    suspend operator fun invoke(year: Int, month: Int): RoutineStats {
        val sessions = repository.getSessionsByMonth(year, month)
        
        // Get unique dates with completions as LocalDate objects
        val completedDates = sessions
            .map { LocalDate.parse(it.date) }
            .distinct()
            .sorted()
        
        // Get today's stats
        val today = LocalDate.now().toString()
        val todaySessions = repository.getSessionsByDate(today)
        val totalDurationToday = todaySessions.sumOf { it.durationSeconds }
        val routineCountToday = todaySessions.size
        
        return RoutineStats(
            completedDates = completedDates,
            totalDurationToday = totalDurationToday,
            routineCountToday = routineCountToday
        )
    }
}
