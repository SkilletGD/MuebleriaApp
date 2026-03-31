package org.example.project.feature.register.presentation

import org.example.project.feature.register.domain.models.User

sealed class RegistroUiState {
    data object Idle : RegistroUiState()
    data object Loading : RegistroUiState()
    data class Success(val user: User) : RegistroUiState()
    data class Error(val message: String) : RegistroUiState()
}

data class RegistroFormState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val telefono: String = "",
    val nombreError: String? = null,
    val telefonoError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isValid: Boolean = false
)