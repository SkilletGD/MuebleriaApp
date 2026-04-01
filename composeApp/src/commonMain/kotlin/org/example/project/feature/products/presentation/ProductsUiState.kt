package org.example.project.feature.products.presentation


import org.example.project.feature.products.data.model.ProductDto

data class ProductsUiState(
    val isLoading: Boolean = false,
    val products: List<ProductDto> = emptyList(),
    val error: String? = null
)