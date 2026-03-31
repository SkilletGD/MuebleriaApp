package org.example.project.feature.register.di

import org.example.project.feature.register.data.remote.RegistroApi
import org.example.project.feature.register.data.repository.RegistroRepositoryImpl
import org.example.project.feature.register.domain.repository.IRegistroRepository
import org.example.project.feature.register.domain.usecases.RegisterUserUseCase
import org.example.project.feature.register.domain.usecases.ValidateEmailUseCase
import org.example.project.feature.register.domain.usecases.ValidatePasswordUseCase
import org.example.project.feature.register.presentation.RegisterViewModel
import org.koin.dsl.module

val registerModule = module {
    // API
    single { RegistroApi(get()) }

    // Repository
    single<IRegistroRepository> { RegistroRepositoryImpl(get(), get()) }

    // UseCases
    factory { RegisterUserUseCase(get()) }
    factory { ValidateEmailUseCase() }
    factory { ValidatePasswordUseCase() }

    // ViewModel (Sintaxis clásica si viewModelOf falla)
    factory { RegisterViewModel(get(), get(), get()) }
}

