package com.diego.proyecto.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Routine(
    val id: Int? = null,
    val userId: Int,
    val name: String,
    val description: String,
    val estimatedDurationMinutes: Int,
    val daysOfWeek: String, // "Lun,Mie,Vie"
    val preferredTime: String? = null, // "HH:MM:SS"
    val active: Boolean = true,
    val exercises: List<RoutineExercise> = emptyList()
)

@Serializable
data class RoutineExercise(
    val id: Int? = null,
    val routineId: Int? = null,
    val name: String,
    val description: String,
    val durationMinutes: Int,
    val order: Int,
    val type: String // Técnica, Teoría, Repertorio
)

@Serializable
data class PracticeSession(
    val id: Int? = null,
    val odlUsuarioId: Int? = null,
    val odlRutinaId: Int? = null,
    val userId: Int? = null,
    val routineId: Int? = null,
    val date: String? = null,
    val fecha: String? = null,
    val durationMinutes: Int? = null,
    val duracionMinutos: Int? = null,
    val selfRating: Float? = null,
    val autoevaluacion: Float? = null,
    val systemRating: Float? = null,
    val evaluacionSistema: Float? = null,
    val notes: String? = null,
    val notas: String? = null,
    val completed: Boolean? = null,
    val completada: Boolean? = null
)

@Serializable
data class SessionStats(
    val totalSessions: Long? = null,
    val totalMinutes: Long? = null,
    val totalHours: Double? = null,
    val averageRating: Double? = null,
    val completedSessions: Long? = null,
    val completionRate: Double? = null,
    val sessionsThisWeek: Int? = null,
    // Campos en español (compatibilidad con backend)
    val totalSesiones: Int? = null,
    val totalMinutos: Int? = null,
    val promedioCalificacion: Float? = null,
    val sesionesEstaSemana: Int? = null
)

@Serializable
data class RoutineListResponse(
    val routines: List<Routine>
)

@Serializable
data class SessionListResponse(
    val sessions: List<PracticeSession>
)

@Serializable
data class CreateSessionRequest(
    val routineId: Int? = null,
    val durationMinutes: Int,
    val selfRating: Float? = null,
    val notes: String? = null,
    val completed: Boolean = true
)

