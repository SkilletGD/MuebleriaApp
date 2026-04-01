package org.example.project.feature.products.presentation

sealed class ProductsEvent {
    object LoadProducts : ProductsEvent()
    data class OnProductClick(val productId: Int) : ProductsEvent()
}