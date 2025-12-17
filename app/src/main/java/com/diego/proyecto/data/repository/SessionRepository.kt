package com.diego.proyecto.data.repository

import android.util.Log
import com.diego.proyecto.data.model.*
import com.diego.proyecto.network.KtorClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class SessionRepository {
    private val client = KtorClient.client

    // GET all sessions
    suspend fun getSessions(token: String): List<PracticeSession>? {
        return try {
            val response: HttpResponse = client.get("sessions") {
                header("Authorization", "Bearer $token")
            }
            if (response.status == HttpStatusCode.OK) {
                val result = response.body<SessionListResponse>()
                result.sessions
            } else null
        } catch (e: Exception) {
            Log.e("SessionRepository", "Get sessions error", e)
            null
        }
    }

    // POST create session
    suspend fun createSession(token: String, session: CreateSessionRequest): PracticeSession? {
        return try {
            val response: HttpResponse = client.post("sessions") {
                header("Authorization", "Bearer $token")
                setBody(session)
            }
            if (response.status == HttpStatusCode.Created) {
                response.body()
            } else {
                Log.e("SessionRepository", "Create session failed: ${response.status}")
                null
            }
        } catch (e: Exception) {
            Log.e("SessionRepository", "Create session error", e)
            null
        }
    }

    // GET stats
    suspend fun getStats(token: String): SessionStats? {
        return try {
            val response: HttpResponse = client.get("sessions/stats") {
                header("Authorization", "Bearer $token")
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else null
        } catch (e: Exception) {
            Log.e("SessionRepository", "Get stats error", e)
            null
        }
    }

    // GET single session by ID
    suspend fun getSession(token: String, id: Int): PracticeSession? {
        return try {
            val response: HttpResponse = client.get("sessions/$id") {
                header("Authorization", "Bearer $token")
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else null
        } catch (e: Exception) {
            Log.e("SessionRepository", "Get session error", e)
            null
        }
    }

    // PUT update session
    suspend fun updateSession(token: String, id: Int, session: CreateSessionRequest): PracticeSession? {
        return try {
            val response: HttpResponse = client.put("sessions/$id") {
                header("Authorization", "Bearer $token")
                setBody(session)
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                Log.e("SessionRepository", "Update session failed: ${response.status}")
                null
            }
        } catch (e: Exception) {
            Log.e("SessionRepository", "Update session error", e)
            null
        }
    }

    // DELETE session
    suspend fun deleteSession(token: String, id: Int): Boolean {
        return try {
            val response: HttpResponse = client.delete("sessions/$id") {
                header("Authorization", "Bearer $token")
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            Log.e("SessionRepository", "Delete session error", e)
            false
        }
    }
}

