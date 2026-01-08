package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.repository.RoutineRepository

class DeleteRoutineUseCase(
    private val repository: RoutineRepository
) {
    suspend operator fun invoke(routineId: Long): Result<Unit> {
        return repository.deleteRoutine(routineId)
    }
}
