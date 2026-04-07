package org.example.project.feature.cart.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import org.example.project.core.network.ApiEndpoints
import org.example.project.feature.cart.data.model.*

class CartApi(private val client: HttpClient) {
    suspend fun getCart(): CartResponseDto =
        client.get(ApiEndpoints.Carrito.BASE).body()

    suspend fun addToCart(request: AddToCartRequest): MessageResponse =
        client.post(ApiEndpoints.Carrito.BASE) { setBody(request) }.body()

    suspend fun updateQuantity(id: Int, cantidad: Int): MessageResponse =
        client.put(ApiEndpoints.Carrito.item(id)) {
            setBody(UpdateQuantityRequest(cantidad))
        }.body()

    suspend fun deleteItem(id: Int): MessageResponse =
        client.delete(ApiEndpoints.Carrito.item(id)).body()

    suspend fun clearCart(): MessageResponse =
        client.delete(ApiEndpoints.Carrito.BASE).body()
}