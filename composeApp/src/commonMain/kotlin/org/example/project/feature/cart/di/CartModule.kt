package org.example.project.feature.cart.di

import org.koin.dsl.module
import org.example.project.feature.cart.data.remote.CartApi
import org.example.project.feature.cart.data.repository.CartRepositoryImpl
import org.example.project.feature.cart.domain.repository.ICartRepository
import org.example.project.feature.cart.domain.usecases.*
import org.example.project.feature.cart.presentation.CartViewModel
import org.koin.core.module.dsl.viewModel

val cartModule = module {
    single { CartApi(get()) }
    single<ICartRepository> { CartRepositoryImpl(get()) }

    factory { GetCartUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { UpdateCartQuantityUseCase(get()) }
    factory { RemoveCartItemUseCase(get()) }
    factory { ClearCartUseCase(get()) }

    viewModel { CartViewModel(get(), get(), get(), get(), get()) }
}