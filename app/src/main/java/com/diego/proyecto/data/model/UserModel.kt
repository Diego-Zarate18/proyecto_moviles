package com.diego.proyecto.data.model

data class UserModel(
    var idUser: Int,
    var name: String,
    var email: String,
    var password: String
){
    var practicas: MutableList<PracticeSession> = mutableListOf()
    var notificaciones: MutableList<NotificationModel> = mutableListOf()

    fun register(){}

    fun login() {}

    fun editProfile(newName: String) {
        this.name = newName
    }

    fun closeSesion() {}
}