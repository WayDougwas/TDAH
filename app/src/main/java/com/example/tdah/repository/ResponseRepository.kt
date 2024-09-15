package com.example.tdah.repository

import androidx.lifecycle.LiveData
import com.example.tdah.data.Response
import com.example.tdah.data.ResponseDao

class ResponseRepository(private val responseDao: ResponseDao) {

    // Insere uma nova resposta
    suspend fun insert(response: Response) {
        responseDao.insert(response)
    }

    // Atualiza uma resposta existente
    suspend fun update(response: Response) {
        responseDao.update(response)
    }

    // Remove uma resposta
    suspend fun delete(response: Response) {
        responseDao.delete(response)
    }

    // Remove todas as respostas
    suspend fun deleteAllResponses() {
        responseDao.deleteAllResponses()
    }

    // Recupera todas as respostas
    fun getAllResponses(): LiveData<List<Response>> {
        return responseDao.getAllResponses()
    }

    // Recupera todas as respostas associadas a um usuário específico
    fun getResponsesByUserId(userId: Int): LiveData<List<Response>> {
        return responseDao.getResponsesByUserId(userId)
    }

    // Recupera as respostas de uma pergunta específica para um usuário
    fun getResponsesForQuestion(userId: Int, question: String): LiveData<List<Response>> {
        return responseDao.getResponsesForQuestion(userId, question)
    }
}
