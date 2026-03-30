package org.example.project.feature.auth.data.repository


import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import org.example.project.core.datastore.TokenManager
import org.example.project.core.network.utils.NetworkResult
import org.example.project.feature.auth.data.model.AuthResponse // <--- IMPORTAR TAMBIÉN AQUÍ
import org.example.project.feature.auth.data.model.LoginRequest
import org.example.project.feature.auth.data.remote.AuthApi
import org.example.project.feature.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    // Dentro de AuthRepositoryImpl.kt
    override suspend fun login(email: String, pass: String): NetworkResult<AuthResponse> {
        return try {
            val response = api.login(LoginRequest(email, pass))
            response.token?.let { tokenManager.saveToken(it) }
            NetworkResult.Success(response)
        } catch (e: ClientRequestException) {
            // Esto atrapa errores 400, 401, 403...
            val errorBody = e.response.bodyAsText() // Aquí viene el JSON de error del servidor
            NetworkResult.Error("Credenciales incorrectas")
        } catch (e: Exception) {
            NetworkResult.Error("Error de conexión: ${e.message}")
        }
    }
}