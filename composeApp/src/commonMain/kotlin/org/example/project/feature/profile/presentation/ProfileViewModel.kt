package org.example.project.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.core.datastore.TokenManager
import org.example.project.feature.profile.domain.usecases.GetUserProfileUseCase

class ProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val tokenManager: TokenManager // Para el Logout
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            getUserProfileUseCase().onSuccess { profile ->
                _uiState.value = ProfileUiState.Success(profile)
            }.onFailure { e ->
                _uiState.value = ProfileUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                tokenManager.deleteToken()
                // Aquí podrías emitir un evento para navegar al Login
                // después de borrar el token exitosamente
            } catch (e: Exception) {
                // Manejar error si falla el DataStore
            }
        }
    }
}