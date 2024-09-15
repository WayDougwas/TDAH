package com.example.tdah.repository

import androidx.lifecycle.LiveData
import com.example.tdah.data.User
import com.example.tdah.data.UserDao

// UserRepository.kt
class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User): Long {
        return userDao.insert(user) // Deve retornar o Long do ID gerado
    }
    // Atualiza um usuário existente
    suspend fun update(user: User) {
        userDao.update(user)
    }

    // Remove um usuário
    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    // Remove todos os usuários
    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

    // Recupera um usuário específico pelo ID
    fun getUserById(userId: Int): LiveData<User> {
        return userDao.getUserById(userId)
    }

    // Recupera todos os usuários
    fun getAllUsers(): LiveData<List<User>> {
        return userDao.getAllUsers()
    }
}
