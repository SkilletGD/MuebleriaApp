package org.example.project.feature.search.data.repository

import org.example.project.feature.search.data.model.ProductDto
import org.example.project.feature.search.data.remote.SearchApi
import org.example.project.feature.search.doamin.repository.ISearchRepository

class SearchRepositoryImpl (
    private val api: SearchApi
) : ISearchRepository {
    override suspend fun getProductsForSearch(): List<ProductDto>{
        return api.getAllProducts()
    }
}