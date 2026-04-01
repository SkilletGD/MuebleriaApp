package org.example.project.feature.products.data.remote


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.example.project.core.network.ApiEndpoints
import org.example.project.feature.products.data.model.ProductDto

class ProductsApi(private val client: HttpClient) {
    suspend fun getProducts(): List<ProductDto> {
        return client.get(ApiEndpoints.Productos.LISTAR).body()
    }
}