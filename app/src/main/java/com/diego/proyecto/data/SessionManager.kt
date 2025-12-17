package com.diego.proyecto.data

object SessionManager {
    var authToken: String? = null
    var userId: Int? = null

    fun clear() {
        authToken = null
        userId = null
    }
}
