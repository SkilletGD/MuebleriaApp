package org.example.project.feature.productdetail.domain.usecases

import org.example.project.feature.productdetail.domain.repository.IProductDetailRepository

class GetProductDetailUseCase(private val repository: IProductDetailRepository) {
    suspend operator fun invoke(id: Int) = repository.getProductById(id)
}