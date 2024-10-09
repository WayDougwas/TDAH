package com.example.tdah.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val phone: String,
    val schoolyear: String,
    val birthday: String,
    val age: Int,
    val result: Double
)

