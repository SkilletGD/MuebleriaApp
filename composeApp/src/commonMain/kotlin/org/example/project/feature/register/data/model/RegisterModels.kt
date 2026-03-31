package org.example.project.feature.register.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.feature.register.domain.models.User
import org.example.project.feature.register.domain.models.UserRole

@Serializable
data class RegisterRequest(
    @SerialName("nombre") val nombre: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("telefono") val telefono: String
)

@Serializable
data class RegisterResponse(
    // ✅ Debe coincidir exactamente con "message" del JSON
    @SerialName("message") val message: String,
    @SerialName("token") val token: String? = null,
    @SerialName("user") val user: UserDto? = null
)

@Serializable
data class UserDto(
    @SerialName("id") val id: Int,
    @SerialName("nombre") val nombre: String,
    @SerialName("email") val email: String,
    @SerialName("rol") val rol: String? = "cliente" // El JSON dice "cliente"
)

// Tu Mapper de confianza
fun UserDto.toDomain() = User(
    id = id,
    nombre = nombre,
    email = email,
    rol = when(rol?.lowercase()) {
        "admin" -> UserRole.ADMIN
        else -> UserRole.CLIENTE // Por defecto para Woodcraft
    }
)