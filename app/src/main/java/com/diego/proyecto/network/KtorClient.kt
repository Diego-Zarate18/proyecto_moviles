package com.diego.proyecto.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    // IMPORTANTE:
    // Si usas el emulador de Android Studio: usa "10.0.2.2"
    // Si usas tu celular por USB: usa la IP de tu PC (ej. "192.168.1.XX")
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 30000
        }

        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
        }

        install(Logging) {
            level = LogLevel.ALL
        }
    }
}
