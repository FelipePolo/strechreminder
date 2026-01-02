package com.fpstudio.stretchreminder.domain.repository

import com.fpstudio.stretchreminder.data.model.RoutineSession

interface RoutineSessionRepository {
    suspend fun saveRoutineSession(session: RoutineSession)
    suspend fun getAllSessions(): List<RoutineSession>
    suspend fun getSessionsByMonth(year: Int, month: Int): List<RoutineSession>
    suspend fun getSessionsByDate(date: String): List<RoutineSession>
}
