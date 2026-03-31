package org.example.project.feature.register.domain.usecases

class ValidateEmailUseCase {
    operator fun invoke(email: String): String? {
        if (email.isBlank()) return "El correo es obligatorio"
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        if (!email.matches(emailRegex.toRegex())) return "Formato de correo inválido"
        return null
    }
}