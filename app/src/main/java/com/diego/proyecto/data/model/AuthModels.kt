package com.diego.proyecto.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val pass: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val userId: Int,
    val name: String,
    val message: String? = null
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val pass: String
)

@Serializable
data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val userId: Int? = null
)

@Serializable
data class ErrorResponse(
    val message: String
)
