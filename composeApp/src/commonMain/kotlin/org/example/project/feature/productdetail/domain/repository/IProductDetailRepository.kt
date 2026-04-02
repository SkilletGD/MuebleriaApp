package org.example.project.feature.productdetail.domain.repository

import org.example.project.feature.productdetail.data.model.ProductDetailDto

interface IProductDetailRepository {
    suspend fun getProductById(id: Int): Result<ProductDetailDto>
}