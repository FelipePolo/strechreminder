package com.fpstudio.stretchreminder.data.repository

import com.fpstudio.stretchreminder.data.datasource.RoutineSessionsLocalDataSource
import com.fpstudio.stretchreminder.data.model.RoutineSession
import com.fpstudio.stretchreminder.domain.repository.RoutineSessionRepository

class RoutineSessionRepositoryImpl(
    private val localDataSource: RoutineSessionsLocalDataSource
) : RoutineSessionRepository {
    
    override suspend fun saveRoutineSession(session: RoutineSession) {
        localDataSource.saveSession(session)
    }

    override suspend fun getAllSessions(): List<RoutineSession> {
        return localDataSource.getAllSessions()
    }

    override suspend fun getSessionsByMonth(year: Int, month: Int): List<RoutineSession> {
        return localDataSource.getSessionsByMonth(year, month)
    }

    override suspend fun getSessionsByDate(date: String): List<RoutineSession> {
        return localDataSource.getSessionsByDate(date)
    }
}
