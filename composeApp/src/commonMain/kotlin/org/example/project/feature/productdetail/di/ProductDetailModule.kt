package org.example.project.feature.productdetail.di

import org.example.project.feature.productdetail.data.remote.ProductDetailApi
import org.example.project.feature.productdetail.data.repository.ProductDetailRepositoryImpl
import org.example.project.feature.productdetail.domain.repository.IProductDetailRepository
import org.example.project.feature.productdetail.domain.usecases.GetProductDetailUseCase
import org.example.project.feature.productdetail.presentation.ProductDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val productDetailModule = module {
    single { ProductDetailApi(get()) }
    single<IProductDetailRepository> { ProductDetailRepositoryImpl(get()) }
    factory { GetProductDetailUseCase(get()) }

    // IMPORTANTE: Usa viewModel { ... }
    viewModel { ProductDetailViewModel(get()) }
}