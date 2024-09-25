package com.example.tdah.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tdah.data.User
import com.example.tdah.data.AppDatabase
import com.example.tdah.repository.UserRepository
import com.example.tdah.util.DateUtils
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    val allUsers: LiveData<List<User>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
        allUsers = userRepository.getAllUsers()
    }

    // UserViewModel.kt
    fun insertUser(name: String, email: String, birthDateString: String,schoolyerar:String,phone:Int ,result:Double): LiveData<Long> {
        val userIdLiveData = MutableLiveData<Long>()
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val birthDate = LocalDate.parse(birthDateString, formatter)
            val age = DateUtils.calculateAge(birthDate)
            val user = User(name = name, email = email, age = age, birthday = birthDateString, schoolyerar = schoolyerar, phone = phone ,result = result)
            val userId = userRepository.insert(user) // Retorna Long
            userIdLiveData.postValue(userId) // Poste o Long ID do usuário
        }
        return userIdLiveData
    }


    fun updateUser(user: User) = viewModelScope.launch {
        userRepository.update(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        userRepository.delete(user)
    }

    fun deleteAllUsers() = viewModelScope.launch {
        userRepository.deleteAllUsers()
    }

    fun getUserById(userId: Int): LiveData<User> {
        return userRepository.getUserById(userId)
    }
}
