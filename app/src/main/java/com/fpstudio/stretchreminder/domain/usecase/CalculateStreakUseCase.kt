package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.repository.RoutineSessionRepository
import java.time.LocalDate

data class StreakStats(
    val days: Int,
    val totalDurationSeconds: Long
)

class CalculateStreakUseCase(
    private val repository: RoutineSessionRepository
) {
    suspend operator fun invoke(): StreakStats {
        val allSessions = repository.getAllSessions()
        
        // Group sessions by date
        val sessionsByDate = allSessions
            .groupBy { LocalDate.parse(it.date) }
        
        // Get unique sorted dates in descending order
        val dates = sessionsByDate.keys
            .sortedDescending()

        if (dates.isEmpty()) return StreakStats(0, 0)
        
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        
        // Streak is broken if the last workout wasn't today or yesterday
        if (!dates[0].isEqual(today) && !dates[0].isEqual(yesterday)) {
            return StreakStats(0, 0)
        }
        
        var streak = 0
        var streakDuration: Long = 0
        var currentCheckDate = if (dates[0].isEqual(today)) today else yesterday
        
        // Iterate through dates to check continuity
        for (date in dates) {
            if (date.isEqual(currentCheckDate)) {
                streak++
                // Add duration for all sessions on this specific streak date
                streakDuration += sessionsByDate[date]?.sumOf { it.durationSeconds } ?: 0
                currentCheckDate = currentCheckDate.minusDays(1)
            } else {
                // Gap found
                break
            }
        }
        
        return StreakStats(streak, streakDuration)
    }
}
