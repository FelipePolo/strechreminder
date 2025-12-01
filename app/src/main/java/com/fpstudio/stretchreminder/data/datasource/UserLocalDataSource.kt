package com.fpstudio.stretchreminder.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fpstudio.stretchreminder.data.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserLocalDataSource(private val context: Context) {

    private val gson = Gson()

    suspend fun saveUser(user: User) {
        context.dataStore.edit {
            val userJson = gson.toJson(user)
            it[USER_KEY] = userJson
        }
    }

    suspend fun getUser(): User? {
        try {
            val user = context.dataStore.data.first()
            val userJson = user[USER_KEY]
            return gson.fromJson(userJson, User::class.java)
        }catch (e: Exception) {
            return null
        }
    }

    companion object {
        private val USER_KEY = stringPreferencesKey("user_data")
    }
}
