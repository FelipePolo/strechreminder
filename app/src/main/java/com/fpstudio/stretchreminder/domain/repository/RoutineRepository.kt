package com.fpstudio.stretchreminder.domain.repository

import com.fpstudio.stretchreminder.data.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getAllRoutines(): Flow<List<Routine>>
    suspend fun saveRoutine(routine: Routine): Result<Long>
    suspend fun deleteRoutine(routineId: Long): Result<Unit>
    suspend fun hasRoutines(): Boolean
    suspend fun routineNameExists(name: String, excludeId: Long? = null): Boolean
}
