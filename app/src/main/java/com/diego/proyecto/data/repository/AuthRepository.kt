package com.diego.proyecto.data.repository

import android.util.Log
import com.diego.proyecto.data.model.ErrorResponse
import com.diego.proyecto.data.model.LoginRequest
import com.diego.proyecto.data.model.LoginResponse
import com.diego.proyecto.data.model.RegisterRequest
import com.diego.proyecto.data.model.RegisterResponse
import com.diego.proyecto.network.KtorClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AuthRepository {
    private val client = KtorClient.client

    suspend fun login(email: String, pass: String): LoginResponse? {
        return try {
            val response: HttpResponse = client.post("auth/login") {
                setBody(LoginRequest(email, pass))
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                val errorBody = response.body<ErrorResponse>()
                Log.e("AuthRepository", "Login failed: ${errorBody.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login error", e)
            null
        }
    }

    suspend fun register(name: String, email: String, pass: String): RegisterResponse? {
        return try {
            val response: HttpResponse = client.post("auth/register") {
                setBody(RegisterRequest(name, email, pass))
            }
            if (response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                val errorBody = response.body<ErrorResponse>()
                Log.e("AuthRepository", "Register failed: ${errorBody.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Register error", e)
            null
        }
    }
}
