package org.example.project.feature.auth.di


import org.example.project.feature.auth.data.remote.AuthApi
import org.example.project.feature.auth.data.repository.AuthRepositoryImpl
import org.example.project.feature.auth.domain.repository.AuthRepository
import org.example.project.feature.auth.ui.LoginViewModel
import org.koin.dsl.module

val authModule = module {

    single { AuthApi(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    factory { LoginViewModel(get()) }
}