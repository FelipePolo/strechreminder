package com.fpstudio.stretchreminder.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fpstudio.stretchreminder.data.model.RoutineSession
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.YearMonth

private val Context.routineSessionsDataStore: DataStore<Preferences> by preferencesDataStore(name = "routine_sessions")

class RoutineSessionsLocalDataSource(private val context: Context) {

    private val gson = Gson()

    suspend fun saveSession(session: RoutineSession) {
        context.routineSessionsDataStore.edit { preferences ->
            val existingSessions = getAllSessions()
            val updatedSessions = existingSessions + session
            val sessionsJson = gson.toJson(updatedSessions)
            preferences[SESSIONS_KEY] = sessionsJson
        }
    }

    suspend fun getAllSessions(): List<RoutineSession> {
        return try {
            val preferences = context.routineSessionsDataStore.data.first()
            val sessionsJson = preferences[SESSIONS_KEY]
            if (sessionsJson != null) {
                val type = object : TypeToken<List<RoutineSession>>() {}.type
                gson.fromJson(sessionsJson, type)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getSessionsByMonth(year: Int, month: Int): List<RoutineSession> {
        val allSessions = getAllSessions()
        val yearMonth = YearMonth.of(year, month)
        val startDate = yearMonth.atDay(1).toString()
        val endDate = yearMonth.atEndOfMonth().toString()
        
        return allSessions.filter { session ->
            session.date >= startDate && session.date <= endDate
        }
    }

    suspend fun getSessionsByDate(date: String): List<RoutineSession> {
        val allSessions = getAllSessions()
        return allSessions.filter { it.date == date }
    }

    companion object {
        private val SESSIONS_KEY = stringPreferencesKey("routine_sessions")
    }
}
