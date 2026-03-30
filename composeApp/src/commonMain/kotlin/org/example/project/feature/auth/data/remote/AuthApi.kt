package org.example.project.feature.auth.data.remote

import org.example.project.core.network.ApiEndpoints

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.project.feature.auth.data.model.AuthResponse
import org.example.project.feature.auth.data.model.LoginRequest

class AuthApi(private val client: HttpClient) {
    suspend fun login(request: LoginRequest): AuthResponse {
        return client.post(ApiEndpoints.Auth.LOGIN) {
            setBody(request)
        }.body()
    }
}