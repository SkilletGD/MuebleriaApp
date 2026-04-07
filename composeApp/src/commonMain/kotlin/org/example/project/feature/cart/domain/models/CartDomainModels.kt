package org.example.project.feature.cart.domain.models

data class Cart(
    val items: List<CartItem>,
    val total: Double,
    val totalItems: Int
)

data class CartItem(
    val cartItemId: Int,
    val variantId: Int,
    val name: String,
    val quantity: Int,
    val price: Double,
    val color: String,
    val stock: Int
)