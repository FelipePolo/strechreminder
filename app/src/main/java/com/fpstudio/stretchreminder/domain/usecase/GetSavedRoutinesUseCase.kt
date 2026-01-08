package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.data.model.Routine
import com.fpstudio.stretchreminder.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow

class GetSavedRoutinesUseCase(
    private val repository: RoutineRepository
) {
    operator fun invoke(): Flow<List<Routine>> {
        return repository.getAllRoutines()
    }
}
