package org.example.project.feature.profile.data.models

import kotlinx.serialization.Serializable
import org.example.project.feature.profile.domain.models.UserProfile
import org.example.project.feature.profile.domain.models.UserRole

@Serializable
data class ProfileResponse(
    val id: Int,
    val nombre: String,
    val email: String,
    val telefono: String?,
    val rol: String,
    val fecha_registro: String
)

fun ProfileResponse.toDomain() = UserProfile(
    id = id,
    nombre = nombre,
    email = email,
    telefono = telefono ?: "No proporcionado",
    rol = when (rol.lowercase()) {
        "admin" -> UserRole.ADMIN
        "repartidor" -> UserRole.REPARTIDOR
        else -> UserRole.CLIENTE
    },
    fechaRegistro = fecha_registro
)