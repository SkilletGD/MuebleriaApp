package org.example.project.feature.productdetail.data.repository

import org.example.project.feature.productdetail.data.model.ProductDetailDto
import org.example.project.feature.productdetail.data.remote.ProductDetailApi
import org.example.project.feature.productdetail.domain.repository.IProductDetailRepository

class ProductDetailRepositoryImpl(
    private val api: ProductDetailApi
) : IProductDetailRepository {
    override suspend fun getProductById(id: Int): Result<ProductDetailDto> {
        return try {
            Result.success(api.fetchProductDetail(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}