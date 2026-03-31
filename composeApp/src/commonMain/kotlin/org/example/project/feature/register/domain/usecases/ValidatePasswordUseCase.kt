package org.example.project.feature.register.domain.usecases

class ValidatePasswordUseCase {
    operator fun invoke(password: String): String? {
        if (password.isBlank()) return "La contraseña es obligatoria"
        if (password.length < 6) return "La contraseña debe tener al menos 6 caracteres"
        return null
    }
}