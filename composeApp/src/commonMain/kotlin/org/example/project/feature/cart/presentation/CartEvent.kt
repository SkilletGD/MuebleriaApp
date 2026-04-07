package org.example.project.feature.cart.presentation

import org.example.project.feature.cart.domain.models.CartItem

sealed class CartEvent {
    object LoadCart : CartEvent()
    object Refresh : CartEvent()
    data class OnAddToCart(val variantId: Int, val quantity: Int) : CartEvent()
    data class OnIncreaseQuantity(val item: CartItem) : CartEvent()
    data class OnDecreaseQuantity(val item: CartItem) : CartEvent()
    data class OnRemoveItem(val cartItemId: Int) : CartEvent()
    object OnClearCart : CartEvent()
    object OnCheckoutClick : CartEvent()
}