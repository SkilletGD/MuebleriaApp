package org.example.project.feature.productdetail.presentation

sealed class ProductDetailEvent {
    data class LoadProduct(val id: Int) : ProductDetailEvent()
}