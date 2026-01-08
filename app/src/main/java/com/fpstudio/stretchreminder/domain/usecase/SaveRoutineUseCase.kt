package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.data.model.Routine
import com.fpstudio.stretchreminder.domain.repository.RoutineRepository

class SaveRoutineUseCase(
    private val repository: RoutineRepository
) {
    suspend operator fun invoke(routine: Routine): Result<Long> {
        return repository.saveRoutine(routine)
    }
}
