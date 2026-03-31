package org.example.project.feature.register.data.repository

import org.example.project.core.datastore.TokenManager
import org.example.project.feature.register.data.model.RegisterRequest
import org.example.project.feature.register.data.model.toDomain
import org.example.project.feature.register.data.remote.RegistroApi
import org.example.project.feature.register.domain.models.User
import org.example.project.feature.register.domain.repository.IRegistroRepository

class RegistroRepositoryImpl(
    private val api: RegistroApi,
    private val tokenManager: TokenManager
) : IRegistroRepository {

    override suspend fun register(
        nombre: String,
        email: String,
        password: String,
        telefono: String
    ): Result<User> = runCatching {
        // 1. Preparamos la petición [cite: 77, 150]
        val request = RegisterRequest(nombre, email, password, telefono)

        // 2. Ejecutamos la llamada a la API en Render [cite: 160]
        val response = api.register(request)

        // 3. Si la respuesta trae token, lo persistimos [cite: 105]
        response.token?.let { token ->
            if (token.isNotEmpty()) {
                tokenManager.saveToken(token)
            }
        }

        // 4. Retornamos el usuario mapeado a dominio [cite: 123, 247]
        // Usamos !! o una validación porque en este punto esperamos éxito
        response.user?.toDomain() ?: throw Exception(response.message)
    }.onFailure {
        // Log opcional para consola en desarrollo
        println("RegistroRepository: Error detectado -> ${it.message}")
    }
}