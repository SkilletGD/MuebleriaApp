package org.example.project.feature.productdetail.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.example.project.core.network.ApiEndpoints
import org.example.project.feature.productdetail.data.model.ProductDetailDto

class ProductDetailApi(private val client: HttpClient) {
    suspend fun fetchProductDetail(id: Int): ProductDetailDto {
        return client.get(ApiEndpoints.Productos.detalle(id)).body()
    }
}