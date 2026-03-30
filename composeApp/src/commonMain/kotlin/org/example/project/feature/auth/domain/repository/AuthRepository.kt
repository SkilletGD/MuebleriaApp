package org.example.project.feature.auth.domain.repository

import org.example.project.core.network.utils.NetworkResult
import org.example.project.feature.auth.data.model.AuthResponse


interface AuthRepository {
    suspend fun login(email: String, pass: String): NetworkResult<AuthResponse>
}