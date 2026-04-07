package org.example.project.feature.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.project.feature.cart.domain.usecases.*

class CartViewModel(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val updateQuantityUseCase: UpdateCartQuantityUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()

    init { loadCart() }

    fun onEvent(event: CartEvent) {
        when (event) {
            // 2. AGREGAR ESTA RAMA PARA MANEJAR EL ADD
            is CartEvent.OnAddToCart -> {
                addItemToCart(event.variantId, event.quantity)
            }
            // 1. Te faltaba manejar la carga inicial
            is CartEvent.LoadCart -> loadCart()

            // 2. Te faltaba manejar el click de checkout (puedes dejarlo vacío por ahora)
            is CartEvent.OnCheckoutClick -> {
                // Aquí podrías disparar un efecto lateral si fuera necesario,
                // pero normalmente la navegación se hace directamente en la UI
            }

            is CartEvent.OnIncreaseQuantity -> updateQty(event.item.cartItemId, event.item.quantity + 1)

            is CartEvent.OnDecreaseQuantity -> {
                if (event.item.quantity > 1) {
                    updateQty(event.item.cartItemId, event.item.quantity - 1)
                }
            }

            is CartEvent.OnRemoveItem -> removeItem(event.cartItemId)

            is CartEvent.OnClearCart -> clearAll()

            is CartEvent.Refresh -> loadCart()
        }
    }

    private fun loadCart() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getCartUseCase().onSuccess { data ->
                _uiState.update { it.copy(cart = data, isLoading = false) }
            }.onFailure { e ->
                _uiState.update { it.copy(isLoading = false, error = "Error al cargar carrito") }
            }
        }
    }

    private fun updateQty(id: Int, qty: Int) {
        viewModelScope.launch { updateQuantityUseCase(id, qty).onSuccess { loadCart() } }
    }

    private fun removeItem(id: Int) {
        viewModelScope.launch { removeCartItemUseCase(id).onSuccess { loadCart() } }
    }

    private fun clearAll() {
        viewModelScope.launch { clearCartUseCase().onSuccess { loadCart() } }
    }

    // 3. AGREGAR ESTA FUNCIÓN PRIVADA
    private fun addItemToCart(variantId: Int, quantity: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            addToCartUseCase(variantId, quantity).onSuccess {
                loadCart() // Refrescamos la lista automáticamente
            }.onFailure { e ->
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudo agregar el producto")
                }
            }
        }
    }
}