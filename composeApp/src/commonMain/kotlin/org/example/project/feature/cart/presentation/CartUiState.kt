package org.example.project.feature.cart.presentation

import org.example.project.feature.cart.domain.models.Cart

data class CartUiState(
    val cart: Cart? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)