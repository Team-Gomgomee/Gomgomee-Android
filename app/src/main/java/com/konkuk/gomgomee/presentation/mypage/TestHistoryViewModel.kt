package com.konkuk.gomgomee.presentation.mypage

import android.app.Application
import android.content.Context
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
    private val sharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val currentUserNo: Int
        get() = sharedPreferences.getInt("current_user_no", -1)

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

    fun loadTestHistory() {
        Log.d(TAG, "Loading test history for user: $currentUserNo")
        
        if (currentUserNo == -1) {
            Log.e(TAG, "No user is currently logged in")
            return
        }

        viewModelScope.launch {
            try {
                // 체크리스트 결과와 영역별 테스트 세션을 별도의 코루틴에서 로드
                launch {
                    Log.d(TAG, "Starting to load checklist results")
                    checklistResultRepository.getResultsByUser(currentUserNo).collect { results ->
                        Log.d(TAG, "Loaded ${results.size} checklist results")
                        _checklistResults.value = results.sortedByDescending { it.createdAt }
                    }
                }

                launch {
                    Log.d(TAG, "Starting to load area test sessions")
                    testSessionRepository.getSessionsByUser(currentUserNo).collect { sessions ->
                        Log.d(TAG, "Loaded ${sessions.size} area test sessions")
                        sessions.forEach { session ->
                            Log.d(TAG, "Session details: id=${session.sessionId}, domain=${session.domain}, " +
                                    "correctCount=${session.correctCount}, totalCount=${session.totalCount}, " +
                                    "startedAt=${session.startedAt}, finishedAt=${session.finishedAt}")
                        }
                        _areaTestSessions.value = sessions
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading test history", e)
            }
        }
    }
} 