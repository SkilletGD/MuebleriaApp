package org.example.project.feature.search.doamin.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.feature.search.data.model.ProductDto

interface ISearchRepository{
    suspend fun getProductsForSearch(): List<ProductDto>

    fun getRecentSearches(): Flow<List<String>>
    suspend fun saveRecentSearch(query: String)
    suspend fun deleteRecentSearch(query: String)
}

