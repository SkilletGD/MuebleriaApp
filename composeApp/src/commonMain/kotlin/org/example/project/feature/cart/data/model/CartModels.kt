package org.example.project.feature.cart.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CartResponseDto(
    val items: List<CartItemDto>,
    val total: Double,
    val cantidad_items: Int
)

@Serializable
data class CartItemDto(
    val id: Int,
    val usuario_id: Int,
    val variante_id: Int,
    val cantidad: Int,
    val producto_nombre: String,
    val precio_unitario: Double,
    val color: String,
    val stock_disponible: Int
)

@Serializable
data class AddToCartRequest(
    val variante_id: Int,
    val cantidad: Int
)

@Serializable
data class UpdateQuantityRequest(
    val cantidad: Int
)

@Serializable
data class MessageResponse(val message: String)