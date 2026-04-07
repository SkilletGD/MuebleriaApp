package org.example.project.feature.cart.domain.usecases

import org.example.project.feature.cart.domain.repository.ICartRepository

class AddToCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(variantId: Int, quantity: Int) =
        repository.addToCart(variantId, quantity)
}