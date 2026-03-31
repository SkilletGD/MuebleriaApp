package org.example.project.feature.register.domain.repository

import org.example.project.feature.register.domain.models.User


interface IRegistroRepository {
    suspend fun register(nombre: String, email: String, password: String, telefono: String): Result<User>
}