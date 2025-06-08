package com.konkuk.gomgomee.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.TestQuestionEntity
import com.konkuk.gomgomee.data.repository.TestQuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestQuestionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TestQuestionRepository
    val allQuestions: Flow<List<TestQuestionEntity>>

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        val testQuestionDao = AppDatabase.getDatabase(application).testQuestionDao()
        repository = TestQuestionRepository(testQuestionDao)
        allQuestions = repository.allQuestions
    }

    fun getQuestionsByDomain(domain: String): Flow<List<TestQuestionEntity>> {
        return repository.getQuestionsByDomain(domain)
    }

    suspend fun getRandomQuestionsByDomain(domain: String, count: Int): List<TestQuestionEntity> {
        return repository.getRandomQuestionsByDomain(domain, count)
    }

    fun insert(question: TestQuestionEntity) = viewModelScope.launch {
        try {
            repository.insert(question)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun insertAll(questions: List<TestQuestionEntity>) = viewModelScope.launch {
        try {
            repository.insertAll(questions)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun update(question: TestQuestionEntity) = viewModelScope.launch {
        try {
            repository.update(question)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun delete(question: TestQuestionEntity) = viewModelScope.launch {
        try {
            repository.delete(question)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun deleteByDomain(domain: String) = viewModelScope.launch {
        try {
            repository.deleteByDomain(domain)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun deleteAll() = viewModelScope.launch {
        try {
            repository.deleteAll()
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getQuestionCountByDomain(domain: String): Int {
        return repository.getQuestionCountByDomain(domain)
    }
} 