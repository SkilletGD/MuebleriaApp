package org.example.project.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(private val dataStore: DataStore<Preferences>) {
    private val TOKEN_KEY = stringPreferencesKey("jwt_token")

    val token: Flow<String?> = dataStore.data.map { it[TOKEN_KEY] }

    suspend fun saveToken(token: String) {
        dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun deleteToken() {
        dataStore.edit { it.remove(TOKEN_KEY) }
    }
}