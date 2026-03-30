package org.example.project.feature.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    @SerialName("message") val mensaje: String? = null,
    @SerialName("token") val token: String? = null,
    @SerialName("user") val usuario: UserDto? = null
)

@Serializable
data class UserDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("nombre") val nombre: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("rol") val rol: String? = null
)