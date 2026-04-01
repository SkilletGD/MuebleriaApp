package org.example.project.feature.register.di

import org.example.project.feature.register.data.remote.RegisterApi
import org.example.project.feature.register.data.repository.RegisterRepositoryImpl
import org.example.project.feature.register.domain.repository.IRegistroRepository
import org.example.project.feature.register.domain.usecases.RegisterUserUseCase
import org.example.project.feature.register.domain.usecases.ValidateEmailUseCase
import org.example.project.feature.register.domain.usecases.ValidatePasswordUseCase
import org.example.project.feature.register.presentation.RegisterViewModel
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel


val registerModule = module {
    // API
    single { RegisterApi(get()) }

    // Repository
    single<IRegistroRepository> { RegisterRepositoryImpl(get(), get()) }

    // UseCases
    factory { RegisterUserUseCase(get()) }
    factory { ValidateEmailUseCase() }
    factory { ValidatePasswordUseCase() }

    // ViewModel (Sintaxis clásica si viewModelOf falla)
    viewModel { RegisterViewModel(get(), get(), get()) }
}

