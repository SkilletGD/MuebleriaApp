package org.example.project.feature.products.di

import org.example.project.feature.products.data.remote.ProductsApi
import org.example.project.feature.products.data.repository.ProductsRepositoryImpl
import org.example.project.feature.products.domain.repository.IProductsRepository
import org.example.project.feature.products.domain.usecases.GetProductsUseCase
import org.example.project.feature.products.presentation.ProductsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val productsModule = module {
    single { ProductsApi(get()) }
    single<IProductsRepository> { ProductsRepositoryImpl(get()) }
    single { GetProductsUseCase(get()) }
    viewModel { ProductsViewModel(get()) }
}