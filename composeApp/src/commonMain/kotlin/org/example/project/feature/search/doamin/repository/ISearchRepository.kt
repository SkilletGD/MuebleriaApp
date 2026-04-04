package org.example.project.feature.search.doamin.repository

import org.example.project.feature.search.data.model.ProductDto

interface ISearchRepository{
    suspend fun getProductsForSearch(): List<ProductDto>
}

