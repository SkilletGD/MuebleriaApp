package org.example.project.feature.cart.domain.usecases

import org.example.project.feature.cart.domain.repository.ICartRepository

class ClearCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke() = repository.clearCart()
}