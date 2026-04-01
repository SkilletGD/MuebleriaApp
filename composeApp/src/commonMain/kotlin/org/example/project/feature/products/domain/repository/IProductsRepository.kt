package org.example.project.feature.products.domain.repository


import org.example.project.core.network.utils.NetworkResult
import org.example.project.feature.products.data.model.ProductDto

interface IProductsRepository {
    suspend fun getProducts(): NetworkResult<List<ProductDto>>
}