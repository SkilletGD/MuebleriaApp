package org.example.project.feature.cart.domain.usecases

import org.example.project.feature.cart.domain.repository.ICartRepository

class RemoveCartItemUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(cartItemId: Int) =
        repository.removeItem(cartItemId)
}