package com.example.tdah.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// DAO para operações relacionadas aos usuários
@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<User>

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>
}

@Dao
interface ResponseDao {
    @Insert
    suspend fun insert(response: Response)

    @Update
    suspend fun update(response: Response)

    @Delete
    suspend fun delete(response: Response)

    @Query("DELETE FROM responses")
    suspend fun deleteAllResponses()

    @Query("SELECT * FROM responses")
    fun getAllResponses(): LiveData<List<Response>>

    @Query("SELECT * FROM responses WHERE userId = :userId")
    fun getResponsesByUserId(userId: Int): LiveData<List<Response>>
}
