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
    suspend fun insert(user: User): Long // Retorna o ID gerado

    // Atualiza um usuário existente na tabela 'users'
    @Update
    suspend fun update(user: User)

    // Remove um usuário da tabela 'users'
    @Delete
    suspend fun delete(user: User)

    // Remove todos os usuários da tabela 'users'
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    // Recupera um usuário específico pelo ID
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): LiveData<User>

    // Recupera todos os usuários
    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>
}

// DAO para operações relacionadas às respostas
@Dao
interface ResponseDao {

    // Insere uma nova resposta na tabela 'responses'
    @Insert
    suspend fun insert(response: Response)

    // Atualiza uma resposta existente na tabela 'responses'
    @Update
    suspend fun update(response: Response)

    // Remove uma resposta da tabela 'responses'
    @Delete
    suspend fun delete(response: Response)

    // Remove todas as respostas da tabela 'responses'
    @Query("DELETE FROM responses")
    suspend fun deleteAllResponses()

    // Recupera todas as respostas
    @Query("SELECT * FROM responses")
    fun getAllResponses(): LiveData<List<Response>>

    // Recupera todas as respostas associadas a um usuário específico
    @Query("SELECT * FROM responses WHERE userId = :userId")
    fun getResponsesByUserId(userId: Int): LiveData<List<Response>>

    // Recupera as respostas de uma pergunta específica para um usuário
    @Query("SELECT * FROM responses WHERE userId = :userId AND question LIKE :question")
    fun getResponsesForQuestion(userId: Int, question: String): LiveData<List<Response>>
}
