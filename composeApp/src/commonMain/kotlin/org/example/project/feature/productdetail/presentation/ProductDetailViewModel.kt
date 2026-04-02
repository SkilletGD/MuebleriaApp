package org.example.project.feature.productdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.project.feature.productdetail.domain.usecases.GetProductDetailUseCase

class ProductDetailViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ProductDetailEvent) {
        when (event) {
            is ProductDetailEvent.LoadProduct -> {
                loadProduct(event.id)
            }
        }
    }

    private fun loadProduct(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getProductDetailUseCase(id)
                .onSuccess { data ->
                    _uiState.update { it.copy(isLoading = false, product = data) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }
}