package org.example.project.feature.products.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.core.network.utils.NetworkResult
import org.example.project.feature.products.domain.usecases.GetProductsUseCase

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(ProductsEvent.LoadProducts)
    }

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.LoadProducts -> fetchProducts()
            is ProductsEvent.OnProductClick -> { /* Lógica de detalle */ }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = getProductsUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(products = result.data, isLoading = false) }
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(error = result.message, isLoading = false) }
                }
                else -> {}
            }
        }
    }
}