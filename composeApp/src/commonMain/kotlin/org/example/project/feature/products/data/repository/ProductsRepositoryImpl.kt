package org.example.project.feature.products.data.repository


import org.example.project.core.network.utils.NetworkResult
import org.example.project.feature.products.data.model.ProductDto
import org.example.project.feature.products.data.remote.ProductsApi
import org.example.project.feature.products.domain.repository.IProductsRepository

class ProductsRepositoryImpl(private val api: ProductsApi) : IProductsRepository {
    override suspend fun getProducts(): NetworkResult<List<ProductDto>> {
        return try {
            val response = api.getProducts()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            NetworkResult.Error("No se pudo cargar el catálogo: ${e.message}")
        }
    }
}