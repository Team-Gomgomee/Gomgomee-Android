package com.konkuk.gomgomee.presentation.mypage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.ChecklistResultEntity
import com.konkuk.gomgomee.data.local.entity.TestSessionEntity
import com.konkuk.gomgomee.data.repository.ChecklistResultRepository
import com.konkuk.gomgomee.data.repository.TestSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestHistoryViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "TestHistoryViewModel"
    }

    private val checklistResultRepository: ChecklistResultRepository
    private val testSessionRepository: TestSessionRepository

    // 체크리스트 결과 Flow
    private val _checklistResults = MutableStateFlow<List<ChecklistResultEntity>>(emptyList())
    val checklistResults: StateFlow<List<ChecklistResultEntity>> = _checklistResults.asStateFlow()

    // 영역별 테스트 세션 Flow
    private val _areaTestSessions = MutableStateFlow<List<TestSessionEntity>>(emptyList())
    val areaTestSessions: StateFlow<List<TestSessionEntity>> = _areaTestSessions.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        checklistResultRepository = ChecklistResultRepository(database.checklistResultDao())
        testSessionRepository = TestSessionRepository(database.testSessionDao())
        loadTestHistory()
    }

    private fun loadTestHistory() {
        viewModelScope.launch {
            // 체크리스트 결과 로드
            launch {
                try {
                    checklistResultRepository.allResults
                        .collect { results ->
                            _checklistResults.value = results.sortedByDescending { it.createdAt }
                        }
                } catch (e: Exception) {
                    if (e !is kotlinx.coroutines.CancellationException) {
                        Log.e(TAG, "Error loading checklist results", e)
                    }
                }
            }

            // 영역별 테스트 세션 로드
            launch {
                try {
                    testSessionRepository.allSessions
                        .collect { sessions ->
                            _areaTestSessions.value = sessions.sortedByDescending { it.startedAt }
                        }
                } catch (e: Exception) {
                    if (e !is kotlinx.coroutines.CancellationException) {
                        Log.e(TAG, "Error loading test sessions", e)
                    }
                }
            }
        }
    }
} 