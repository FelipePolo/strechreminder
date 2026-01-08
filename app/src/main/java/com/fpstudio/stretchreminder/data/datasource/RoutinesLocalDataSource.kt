package com.fpstudio.stretchreminder.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fpstudio.stretchreminder.data.model.Routine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.routinesDataStore: DataStore<Preferences> by preferencesDataStore(name = "routines")

class RoutinesLocalDataSource(private val context: Context) {

    private val gson = Gson()

    suspend fun saveRoutine(routine: Routine): Long {
        context.routinesDataStore.edit { preferences ->
            val existingRoutines = getAllRoutines()
            val updatedRoutines = existingRoutines + routine
            val routinesJson = gson.toJson(updatedRoutines)
            preferences[ROUTINES_KEY] = routinesJson
        }
        return routine.id
    }

    suspend fun getAllRoutines(): List<Routine> {
        return try {
            val preferences = context.routinesDataStore.data.first()
            val routinesJson = preferences[ROUTINES_KEY]
            if (routinesJson != null) {
                val type = object : TypeToken<List<Routine>>() {}.type
                gson.fromJson(routinesJson, type)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getAllRoutinesFlow(): Flow<List<Routine>> {
        return context.routinesDataStore.data.map { preferences ->
            try {
                val routinesJson = preferences[ROUTINES_KEY]
                if (routinesJson != null) {
                    val type = object : TypeToken<List<Routine>>() {}.type
                    gson.fromJson(routinesJson, type)
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun deleteRoutine(routineId: Long) {
        context.routinesDataStore.edit { preferences ->
            val existingRoutines = getAllRoutines()
            val updatedRoutines = existingRoutines.filter { it.id != routineId }
            val routinesJson = gson.toJson(updatedRoutines)
            preferences[ROUTINES_KEY] = routinesJson
        }
    }

    suspend fun hasRoutines(): Boolean {
        return getAllRoutines().isNotEmpty()
    }

    suspend fun routineNameExists(name: String): Boolean {
        val routines = getAllRoutines()
        return routines.any { it.name.equals(name, ignoreCase = true) }
    }

    companion object {
        private val ROUTINES_KEY = stringPreferencesKey("routines")
    }
}
