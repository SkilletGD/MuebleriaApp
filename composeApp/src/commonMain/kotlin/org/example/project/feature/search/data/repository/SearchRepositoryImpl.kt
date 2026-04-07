package org.example.project.feature.search.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import org.example.project.feature.search.data.model.ProductDto
import org.example.project.feature.search.data.remote.SearchApi
import org.example.project.feature.search.doamin.repository.ISearchRepository
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl (
    private val api: SearchApi,
    private val dataStore: DataStore<Preferences>
) : ISearchRepository {
    private val RECENT_SEARCH_KEY = stringPreferencesKey("recent_searches")

    override fun getRecentSearches(): Flow<List<String>> = dataStore.data.map { prefs ->
        val searches = prefs[RECENT_SEARCH_KEY] ?: ""
        if (searches.isEmpty()) emptyList() else searches.split("|")
    }

    override suspend fun saveRecentSearch(query: String) {
        if (query.isBlank()) return
        dataStore.edit { prefs ->
            val current = prefs[RECENT_SEARCH_KEY]?.split("|")?.toMutableList() ?: mutableListOf()
            current.remove(query) // Evita duplicados
            current.add(0, query) // Lo pone al principio
            prefs[RECENT_SEARCH_KEY] = current.take(5).joinToString("|") // Guarda las últimas 5
        }
    }

    override suspend fun deleteRecentSearch(query: String) {
        dataStore.edit { prefs ->
            val current = prefs[RECENT_SEARCH_KEY]?.split("|")?.toMutableList() ?: return@edit
            current.remove(query)
            prefs[RECENT_SEARCH_KEY] = current.joinToString("|")
        }
    }

    override suspend fun getProductsForSearch(): List<ProductDto> {
        return try {
            api.getAllProducts()
        } catch (e: Exception) {
            // Loguea el error para debug (opcional)
            println("Error de red en Repository: ${e.message}")
            // Devolvemos una lista vacía para que el resto de la app no truene
            emptyList()
        }
    }
}