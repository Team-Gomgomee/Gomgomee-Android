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

    private val _registrationState = MutableStateFlow<Boolean?>(null)
    val registrationState = _registrationState.asStateFlow()

    suspend fun insert(user: UserEntity): Boolean {
        // 상태 초기화
        _errorMessage.value = null
        _registrationState.value = null

        return try {
            // ID 중복 체크
            if (repository.isIdExists(user.id)) {
                _errorMessage.value = "이미 사용 중인 아이디입니다"
                false
            } else {
                repository.insert(user)
                _registrationState.value = true
                true
            }
        } catch (e: Exception) {
            _errorMessage.value = e.message
            false
        }
    }

    fun login(id: String, password: String) = viewModelScope.launch {
        // 상태 초기화
        _errorMessage.value = null
        _loginState.value = null

        try {
            val user = repository.login(id, password)
            if (user == null) {
                _errorMessage.value = "아이디 또는 비밀번호가 일치하지 않습니다"
            } else {
                _loginState.value = user
            }
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

    suspend fun isIdExists(id: String): Boolean {
        return repository.isIdExists(id)
    }

    // 상태 초기화 함수
    fun resetStates() {
        _errorMessage.value = null
        _registrationState.value = null
        _loginState.value = null
    }
} 