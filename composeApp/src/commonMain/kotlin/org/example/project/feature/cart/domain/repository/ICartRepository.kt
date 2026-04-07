package org.example.project.feature.cart.domain.repository

import org.example.project.feature.cart.domain.models.Cart

interface ICartRepository {
    suspend fun getCart(): Result<Cart>
    suspend fun addToCart(variantId: Int, quantity: Int): Result<String>
    suspend fun updateQuantity(cartItemId: Int, quantity: Int): Result<String>
    suspend fun removeItem(cartItemId: Int): Result<String>
    suspend fun clearCart(): Result<String>
}