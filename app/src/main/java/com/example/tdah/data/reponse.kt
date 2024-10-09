package com.example.tdah.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "responses")
data class Response(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID gerado automaticamente
    var userId: Long, // Verifique se isso corresponde ao tipo de ID do usuário
    var answer: String
)