package org.example.project.feature.cart.data.repository

import org.example.project.feature.cart.data.remote.CartApi
import org.example.project.feature.cart.data.model.AddToCartRequest
import org.example.project.feature.cart.data.model.CartItemDto
import org.example.project.feature.cart.domain.models.*
import org.example.project.feature.cart.domain.repository.ICartRepository

class CartRepositoryImpl(private val api: CartApi) : ICartRepository {

    override suspend fun getCart(): Result<Cart> = runCatching {
        val dto = api.getCart()
        Cart(
            items = dto.items.map { it.toDomain() },
            total = dto.total,
            totalItems = dto.cantidad_items
        )
    }

    override suspend fun addToCart(variantId: Int, quantity: Int): Result<String> = runCatching {
        api.addToCart(AddToCartRequest(variantId, quantity)).message
    }

    override suspend fun updateQuantity(cartItemId: Int, quantity: Int): Result<String> = runCatching {
        api.updateQuantity(cartItemId, quantity).message
    }

    override suspend fun removeItem(cartItemId: Int): Result<String> = runCatching {
        api.deleteItem(cartItemId).message
    }

    override suspend fun clearCart(): Result<String> = runCatching {
        api.clearCart().message
    }
}

// Mapper para limpiar los datos que van a la UI
fun CartItemDto.toDomain() = CartItem(
    cartItemId = id,
    variantId = variante_id,
    name = producto_nombre,
    quantity = cantidad,
    price = precio_unitario,
    color = color,
    stock = stock_disponible
)