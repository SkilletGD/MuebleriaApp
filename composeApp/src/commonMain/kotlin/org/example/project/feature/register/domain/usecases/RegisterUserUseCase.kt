package org.example.project.feature.register.domain.usecases

import org.example.project.feature.register.domain.models.User
import org.example.project.feature.register.domain.repository.IRegistroRepository

class RegisterUserUseCase(private val repository: IRegistroRepository) {
    suspend operator fun invoke(
        nombre: String,
        email: String,
        password: String,
        telefono: String
    ): Result<User> {
        return repository.register(nombre, email, password, telefono)
    }
}