package com.diego.proyecto.data.model

data class MultimediaModel(
    var idMultimedia: Int,
    var type: String,
    var fileRoute: String,
    var fileName: String
){
    fun update() {}

    fun edit(newName: String) {
        this.fileName = newName
    }

    fun delete() {}

    fun play() {}
}
