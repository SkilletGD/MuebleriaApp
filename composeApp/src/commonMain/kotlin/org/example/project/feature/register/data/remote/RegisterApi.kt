package org.example.project.feature.register.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.example.project.core.network.ApiEndpoints
import org.example.project.feature.register.data.model.RegisterRequest
import org.example.project.feature.register.data.model.RegisterResponse

class RegistroApi(private val client: HttpClient) {
    suspend fun register(request: RegisterRequest): RegisterResponse {
        return client.post(ApiEndpoints.Auth.REGISTER) {
            setBody(request)
        }.body()
    }
}