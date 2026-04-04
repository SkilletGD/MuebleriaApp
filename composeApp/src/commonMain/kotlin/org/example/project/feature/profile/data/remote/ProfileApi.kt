package org.example.project.feature.profile.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.core.network.ApiEndpoints
import org.example.project.feature.profile.data.models.ProfileResponse

class ProfileApi(private val client: HttpClient) {
    suspend fun getProfile(): ProfileResponse {
        return client.get(ApiEndpoints.Auth.PROFILE).body()
    }
}