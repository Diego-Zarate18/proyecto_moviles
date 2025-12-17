package com.diego.proyecto.network

/**
 * Simple in-memory token manager.
 * Para producción, considera usar DataStore o EncryptedSharedPreferences.
 */
object TokenManager {
    private var _token: String? = null
    private var _userId: Int? = null

    val token: String?
        get() = _token

    val userId: Int?
        get() = _userId

    fun saveToken(token: String, userId: Int) {
        _token = token
        _userId = userId
    }

    fun clearToken() {
        _token = null
        _userId = null
    }

    fun isLoggedIn(): Boolean = _token != null
}

