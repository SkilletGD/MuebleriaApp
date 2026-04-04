package org.example.project.feature.search.di


import org.example.project.feature.search.data.remote.SearchApi
import org.example.project.feature.search.data.repository.SearchRepositoryImpl
import org.example.project.feature.search.doamin.repository.ISearchRepository
import org.example.project.feature.search.doamin.usacases.SearchProductsUseCase
import org.example.project.feature.search.presentation.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {

    // 1. Data Layer: Definimos la API (inyecta el HttpClient que está en el core)
    single { SearchApi(get()) }

    // 2. Repository Layer: Vinculamos la interfaz con la implementación
    // get() aquí inyectará el SearchApi definido arriba
    single<ISearchRepository> { SearchRepositoryImpl(get()) }

    // 3. Domain Layer: El UseCase (factory crea una instancia nueva cada vez que se pide)
    // get() aquí inyectará el ISearchRepository
    factory { SearchProductsUseCase(get()) }

    // 4. Presentation Layer: El ViewModel
    // get() aquí inyectará el SearchProductsUseCase
    viewModel { SearchViewModel(get()) }
}