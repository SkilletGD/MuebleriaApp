package org.example.project.core.network

import org.example.project.core.datastore.TokenManager
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

fun createHttpClient(tokenManager: TokenManager) = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            isLenient = true
        })
    }

    install(Logging) {
        level = LogLevel.INFO
        logger = Logger.DEFAULT
    }

    defaultRequest {
        url(ApiEndpoints.BASE_URL)
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

    // Interceptor para inyectar el Token automáticamente
    install("AuthInterceptor") {
        requestPipeline.intercept(HttpRequestPipeline.State) {
            val token = tokenManager.token.firstOrNull()
            if (!token.isNullOrBlank()) {
                context.header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}