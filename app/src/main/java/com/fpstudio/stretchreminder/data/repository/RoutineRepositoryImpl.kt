package com.fpstudio.stretchreminder.data.repository

import com.fpstudio.stretchreminder.data.datasource.RoutinesLocalDataSource
import com.fpstudio.stretchreminder.data.model.Routine
import com.fpstudio.stretchreminder.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow

class RoutineRepositoryImpl(
    private val localDataSource: RoutinesLocalDataSource
) : RoutineRepository {

    override fun getAllRoutines(): Flow<List<Routine>> {
        return localDataSource.getAllRoutinesFlow()
    }

    override suspend fun saveRoutine(routine: Routine): Result<Long> {
        return try {
            val id = localDataSource.saveRoutine(routine)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteRoutine(routineId: Long): Result<Unit> {
        return try {
            localDataSource.deleteRoutine(routineId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun hasRoutines(): Boolean {
        return localDataSource.hasRoutines()
    }

    override suspend fun routineNameExists(name: String): Boolean {
        return localDataSource.routineNameExists(name)
    }
}
