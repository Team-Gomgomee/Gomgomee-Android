package com.konkuk.gomgomee.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.UserEntity
import com.konkuk.gomgomee.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val allUsers: Flow<List<UserEntity>>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers
    }

    private val _loginState = MutableStateFlow<UserEntity?>(null)
    val loginState = _loginState.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun insert(user: UserEntity) = viewModelScope.launch {
        try {
            repository.insert(user)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun login(id: String, password: String) = viewModelScope.launch {
        try {
            val user = repository.login(id, password)
            _loginState.value = user
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun update(user: UserEntity) = viewModelScope.launch {
        repository.update(user)
    }

    fun delete(user: UserEntity) = viewModelScope.launch {
        repository.delete(user)
    }
} 