package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.domain.repository.RoutineRepository

class HasSavedRoutinesUseCase(
    private val repository: RoutineRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.hasRoutines()
    }
}
