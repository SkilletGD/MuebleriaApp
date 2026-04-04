package org.example.project.feature.profile.domain.models

data class UserProfile(
    val id: Int,
    val nombre: String,
    val email: String,
    val telefono: String,
    val rol: UserRole,
    val fechaRegistro: String
)

enum class UserRole { ADMIN, CLIENTE, REPARTIDOR }