package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.data.model.RoutineSession
import com.fpstudio.stretchreminder.domain.repository.RoutineSessionRepository
import java.time.LocalDate

class SaveRoutineSessionUseCase(
    private val repository: RoutineSessionRepository
) {
    suspend operator fun invoke(durationSeconds: Long) {
        val currentDate = LocalDate.now().toString() // ISO format YYYY-MM-DD
        val currentTimestamp = System.currentTimeMillis()
        
        val session = RoutineSession(
            date = currentDate,
            durationSeconds = durationSeconds,
            completedAt = currentTimestamp
        )
        
        repository.saveRoutineSession(session)
    }
}
