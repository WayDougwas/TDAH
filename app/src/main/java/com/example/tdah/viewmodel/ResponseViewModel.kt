package com.example.tdah.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tdah.data.AppDatabase
import com.example.tdah.data.Response
import com.example.tdah.repository.ResponseRepository
import kotlinx.coroutines.launch

class ResponseViewModel(application: Application) : AndroidViewModel(application) {

    private val responseRepository: ResponseRepository
    val allResponses: LiveData<List<Response>>

    init {
        val responseDao = AppDatabase.getDatabase(application).responseDao()
        responseRepository = ResponseRepository(responseDao)

        allResponses = responseRepository.getAllResponses()
    }

    fun insert(response: Response) = viewModelScope.launch {
        responseRepository.insert(response) // Certifique-se de que este método esteja funcionando
    }


    fun update(response: Response) = viewModelScope.launch {
        responseRepository.update(response)
    }

    fun delete(response: Response) = viewModelScope.launch {
        responseRepository.delete(response)
    }

    fun deleteAllResponses() = viewModelScope.launch {
        responseRepository.deleteAllResponses()
    }

    fun getResponsesByUserId(userId: Int): LiveData<List<Response>> {
        return responseRepository.getResponsesByUserId(userId)
    }
}
