package org.example.project.feature.productdetail.presentation

import org.example.project.feature.productdetail.data.model.ProductDetailDto

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: ProductDetailDto? = null,
    val error: String? = null
)