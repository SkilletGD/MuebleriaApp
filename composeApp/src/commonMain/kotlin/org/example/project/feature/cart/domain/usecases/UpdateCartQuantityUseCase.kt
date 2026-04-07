package org.example.project.feature.cart.domain.usecases

import org.example.project.feature.cart.domain.repository.ICartRepository

class UpdateCartQuantityUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(cartItemId: Int, quantity: Int) =
        repository.updateQuantity(cartItemId, quantity)
}