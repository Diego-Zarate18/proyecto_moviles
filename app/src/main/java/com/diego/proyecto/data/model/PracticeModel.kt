package com.diego.proyecto.data.model

import java.sql.Time
import java.util.Date

data class PracticeModel(
    var idPractice: Int,
    var creationDate: Date,
    var duration: Time,
    var description: String,
    var lastModification: Date
){
    var multimediaFile: MutableList<MultimediaModel> = mutableListOf()

    fun create() {
        this.creationDate = Date()
    }

    fun edit(newDescription: String) {
        this.description = newDescription
        this.lastModification = Date()
    }

    fun delete() {}

    fun getMultimedia(): List<MultimediaModel> {
        return multimediaFile
    }
}
