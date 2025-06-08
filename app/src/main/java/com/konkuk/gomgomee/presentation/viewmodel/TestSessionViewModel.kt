package com.konkuk.gomgomee.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.TestSessionEntity
import com.konkuk.gomgomee.data.repository.TestSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestSessionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TestSessionRepository
    val allSessions: Flow<List<TestSessionEntity>>

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        val testSessionDao = AppDatabase.getDatabase(application).testSessionDao()
        repository = TestSessionRepository(testSessionDao)
        allSessions = repository.allSessions
    }

    fun getSessionsByUser(userNo: Int): Flow<List<TestSessionEntity>> {
        return repository.getSessionsByUser(userNo)
    }

    fun getSessionsByUserAndDomain(userNo: Int, domain: String): Flow<List<TestSessionEntity>> {
        return repository.getSessionsByUserAndDomain(userNo, domain)
    }

    fun startNewSession(userNo: Int, domain: String, totalCount: Int): TestSessionEntity {
        return TestSessionEntity(
            userNo = userNo,
            domain = domain,
            startedAt = System.currentTimeMillis(),
            finishedAt = null,
            totalCount = totalCount
        )
    }

    fun finishSession(session: TestSessionEntity, correctCount: Int) = viewModelScope.launch {
        try {
            val finishedSession = session.copy(
                finishedAt = System.currentTimeMillis(),
                correctCount = correctCount
            )
            repository.insert(finishedSession)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    suspend fun getAverageScoreByDomain(userNo: Int, domain: String): Float? {
        return repository.getAverageScoreByDomain(userNo, domain)
    }

    suspend fun getLatestSession(userNo: Int): TestSessionEntity? {
        return repository.getLatestSession(userNo)
    }

    fun deleteSessionsByUser(userNo: Int) = viewModelScope.launch {
        try {
            repository.deleteSessionsByUser(userNo)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }
} 