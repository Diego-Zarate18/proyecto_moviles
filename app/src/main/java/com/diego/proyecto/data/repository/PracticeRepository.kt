package com.diego.proyecto.data.repository

import android.util.Log
import com.diego.proyecto.data.model.PracticeSession
import com.diego.proyecto.data.model.Routine
import com.diego.proyecto.data.model.SessionStats
import com.diego.proyecto.network.KtorClient
import com.diego.proyecto.network.TokenManager
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class PracticeRepository {
    private val client = KtorClient.client

    // Obtener todas las rutinas del usuario
    suspend fun getRoutines(): List<Routine> {
        return try {
            Log.d("PracticeRepository", "Fetching routines")
            val response: HttpResponse = client.get("rutinas") {
                TokenManager.token?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                Log.e("PracticeRepository", "Failed to fetch routines: ${response.status}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("PracticeRepository", "Error fetching routines", e)
            emptyList()
        }
    }

    // Obtener rutinas activas
    suspend fun getActiveRoutines(): List<Routine> {
        return try {
            val response: HttpResponse = client.get("rutinas") {
                parameter("active", true)
                TokenManager.token?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("PracticeRepository", "Error fetching active routines", e)
            emptyList()
        }
    }

    // Obtener todas las sesiones
    suspend fun getSessions(): List<PracticeSession> {
        return try {
            Log.d("PracticeRepository", "Fetching sessions")
            val response: HttpResponse = client.get("sesiones") {
                TokenManager.token?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                Log.e("PracticeRepository", "Failed to fetch sessions: ${response.status}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("PracticeRepository", "Error fetching sessions", e)
            emptyList()
        }
    }

    // Obtener estadísticas de sesiones
    suspend fun getSessionStats(): SessionStats? {
        return try {
            val response: HttpResponse = client.get("sesiones/stats") {
                TokenManager.token?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("PracticeRepository", "Error fetching stats", e)
            null
        }
    }

    suspend fun saveRoutine(routine: Routine): Boolean {
        return try {
            Log.d("PracticeRepository", "Attempting to save routine: ${routine.name}")
            val response: HttpResponse = client.post("rutinas") {
                contentType(ContentType.Application.Json)
                TokenManager.token?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
                setBody(routine)
            }
            Log.d("PracticeRepository", "Response status: ${response.status}")

            if (response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK) {
                Log.d("PracticeRepository", "Routine saved successfully")
                true
            } else {
                val errorBody = response.bodyAsText()
                Log.e("PracticeRepository", "Failed to save routine. Status: ${response.status}, Body: $errorBody")
                throw Exception("Error del servidor: ${response.status} - $errorBody")
            }
        } catch (e: Exception) {
            Log.e("PracticeRepository", "Error saving routine", e)
            throw e
        }
    }
}
