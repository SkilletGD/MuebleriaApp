package org.example.project.feature.register.presentation

sealed class RegistroEvent {
    data class NombreChanged(val nombre: String) : RegistroEvent()
    data class EmailChanged(val email: String) : RegistroEvent()
    data class PasswordChanged(val password: String) : RegistroEvent()
    data class TelefonoChanged(val telefono: String) : RegistroEvent()
    data object OnRegisterClick : RegistroEvent()
}