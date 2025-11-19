package com.diego.proyecto.data.model

import java.util.Date

data class NotificationModel(
    var idNotification: Int,
    var message: String,
    var dateMessage: Date
){
    fun send() {}
}
