package org.example.project.feature.register.domain.models

enum class UserRole {
    ADMIN, CLIENTE, REPARTIDOR
}

data class User(
    val id: Int,
    val nombre: String,
    val email: String,
    val rol: UserRole
)