package org.example.project.feature.products.domain.usecases


import org.example.project.feature.products.domain.repository.IProductsRepository

class GetProductsUseCase(private val repository: IProductsRepository) {
    suspend operator fun invoke() = repository.getProducts()
}