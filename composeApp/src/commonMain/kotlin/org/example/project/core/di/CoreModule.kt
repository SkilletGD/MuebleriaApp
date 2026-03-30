package org.example.project.core.di

import org.example.project.core.network.createHttpClient
import org.example.project.core.datastore.TokenManager
import org.koin.dsl.module

val coreModule = module {
    single { TokenManager(get()) }
    single { createHttpClient(get()) }
}