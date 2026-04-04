package org.example.project.feature.profile.di


import org.koin.dsl.module
import org.example.project.feature.profile.data.remote.ProfileApi
import org.example.project.feature.profile.data.repository.ProfileRepositoryImpl
import org.example.project.feature.profile.domain.repository.IProfileRepository
import org.example.project.feature.profile.domain.usecases.GetUserProfileUseCase
import org.example.project.feature.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModel

val profileModule = module {

    // --- CAPA DATA ---
    // Api: Inyectamos el HttpClient que ya tienes en el Core
    single { ProfileApi(get()) }

    // Repository: Implementación ligada a la Interface
    single<IProfileRepository> { ProfileRepositoryImpl(get()) }

    // --- CAPA DOMAIN ---
    // UseCase: Usamos factory para que se cree una instancia nueva cada vez
    factory { GetUserProfileUseCase(get()) }

    // --- CAPA PRESENTATION ---
    // ViewModel: Inyecta el UseCase y el TokenManager (que debe estar en el Core)
    viewModel{ ProfileViewModel(get(), get()) }
}