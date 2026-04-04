package org.example.project.feature.search.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.core.network.ApiEndpoints
import org.example.project.feature.search.data.model.ProductDto

class SearchApi(
    private val client: HttpClient
){
    suspend fun getAllProducts(): List<ProductDto> {
        return client.get(ApiEndpoints.Productos.LISTAR).body()
    }
}