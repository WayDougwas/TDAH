package com.example.tdah.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "responses")
data class Response(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Long = 0,
    val question: Int,
    val answer: String,
)