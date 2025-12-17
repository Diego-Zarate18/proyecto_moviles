package com.diego.proyecto.data.repository

import android.util.Log
import com.diego.proyecto.data.model.*
import com.diego.proyecto.network.KtorClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class RoutineRepository {
    private val client = KtorClient.client

    // GET all routines
    suspend fun getRoutines(token: String): List<Routine>? {
        return try {
            val response: HttpResponse = client.get("rutinas") {
                header("Authorization", "Bearer $token")
            }
            if (response.status == HttpStatusCode.OK) {
                val result = response.body<RoutineListResponse>()
                result.routines
            } else {
                Log.e("RoutineRepository", "Get routines failed: ${response.status}")
                null
            }
        } catch (e: Exception) {
            Log.e("RoutineRepository", "Get routines error", e)
            null
        }
    }

    // GET single routine by ID
    suspend fun getRoutine(token: String, id: Int): Routine? {
        return try {
            val response: HttpResponse = client.get("rutinas/$id") {
                header("Authorization", "Bearer $token")
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                Log.e("RoutineRepository", "Get routine failed: ${response.status}")
                null
            }
        } catch (e: Exception) {
            Log.e("RoutineRepository", "Get routine error", e)
            null
        }
    }

    // POST create routine
    suspend fun createRoutine(token: String, routine: Routine): Routine? {
        return try {
            val response: HttpResponse = client.post("rutinas") {
                header("Authorization", "Bearer $token")
                setBody(routine)
            }
            if (response.status == HttpStatusCode.Created) {
                response.body()
            } else {
                Log.e("RoutineRepository", "Create routine failed: ${response.status}")
                null
            }
        } catch (e: Exception) {
            Log.e("RoutineRepository", "Create routine error", e)
            null
        }
    }

    // PUT update routine
    suspend fun updateRoutine(token: String, id: Int, routine: Routine): Routine? {
        return try {
            val response: HttpResponse = client.put("rutinas/$id") {
                header("Authorization", "Bearer $token")
                setBody(routine)
            }
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else null
        } catch (e: Exception) {
            Log.e("RoutineRepository", "Update routine error", e)
            null
        }
    }

    // DELETE routine
    suspend fun deleteRoutine(token: String, id: Int): Boolean {
        return try {
            val response: HttpResponse = client.delete("rutinas/$id") {
                header("Authorization", "Bearer $token")
            }
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            Log.e("RoutineRepository", "Delete routine error", e)
            false
        }
    }
}

