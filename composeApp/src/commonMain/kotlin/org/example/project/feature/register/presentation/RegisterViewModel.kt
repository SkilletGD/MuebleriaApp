package org.example.project.feature.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.project.feature.register.domain.usecases.RegisterUserUseCase
import org.example.project.feature.register.domain.usecases.ValidateEmailUseCase
import org.example.project.feature.register.domain.usecases.ValidatePasswordUseCase

class RegisterViewModel(
    private val registerUseCase: RegisterUserUseCase,
    private val validateEmail: ValidateEmailUseCase,    // Inyectado
    private val validatePassword: ValidatePasswordUseCase // Inyectado
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegistroUiState>(RegistroUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(RegistroFormState())
    val formState = _formState.asStateFlow()

    fun onEvent(event: RegistroEvent) {
        when(event) {
            is RegistroEvent.NombreChanged -> {
                _formState.update { it.copy(nombre = event.nombre) }
                validateForm() // 👈 Llamar a validar
            }
            is RegistroEvent.EmailChanged -> {
                _formState.update { it.copy(email = event.email) }
                validateForm() // 👈 Llamar a validar
            }
            is RegistroEvent.PasswordChanged -> {
                _formState.update { it.copy(password = event.password) }
                validateForm() // 👈 Llamar a validar
            }
            is RegistroEvent.TelefonoChanged -> {
                _formState.update { it.copy(telefono = event.telefono) }
                validateForm() // 👈 Llamar a validar
            }
            is RegistroEvent.OnRegisterClick -> register()
            else -> {}
        }
    }

    private fun validateForm() {
        val s = _formState.value

        // Ejecutamos los casos de uso (devuelven un String si hay error, o null si es válido)
        val emailError = validateEmail(s.email)
        val passwordError = validatePassword(s.password)

        // Validación manual simple para campos que no tienen Use Case específico aún
        val nombreError = if (s.nombre.isBlank()) "El nombre es obligatorio" else null
        val telefonoError = if (s.telefono.length != 10) "Deben ser 10 dígitos" else null

        _formState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError,
                nombreError = nombreError,
                telefonoError = telefonoError,
                // El botón se habilita solo si todos los errores son null
                isValid = emailError == null && passwordError == null &&
                        nombreError == null && telefonoError == null
            )
        }
    }

    private fun register() {
        val state = _formState.value
        viewModelScope.launch {
            _uiState.value = RegistroUiState.Loading
            registerUseCase(state.nombre, state.email, state.password, state.telefono)
                .onSuccess { _uiState.value = RegistroUiState.Success(it) }
                .onFailure { _uiState.value = RegistroUiState.Error(it.message ?: "Error") }
        }
    }
}