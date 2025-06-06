package com.konkuk.gomgomee.data.util

import android.content.Context
import android.util.Log
import com.konkuk.gomgomee.data.local.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataInitializer(private val context: Context) {
    private val TAG = "DataInitializer"
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val database = AppDatabase.getDatabase(context)
    private val jsonDataReader = JsonDataReader(context)

    suspend fun initializeDataIfNeeded() {
        val isInitialized = sharedPreferences.getBoolean("is_data_initialized", false)
        Log.d(TAG, "Checking if data is initialized: $isInitialized")
        
        if (!isInitialized) {
            withContext(Dispatchers.IO) {
                try {
                    Log.d(TAG, "Starting initial data initialization...")
                    
                    // 체크리스트 문항 초기화
                    val checklistItems = jsonDataReader.readChecklistItems()
                    Log.d(TAG, "Read ${checklistItems.size} checklist items from JSON")
                    database.checklistItemDao().insertItems(checklistItems)

                    // 테스트 문제 초기화
                    val testQuestions = jsonDataReader.readTestQuestions()
                    Log.d(TAG, "Read ${testQuestions.size} test questions from JSON")
                    database.testQuestionDao().insertQuestions(testQuestions)

                    // 기관 정보 초기화
                    val institutions = jsonDataReader.readInstitutions()
                    Log.d(TAG, "Read ${institutions.size} institutions from JSON")
                    database.institutionDao().insertInstitutions(institutions)

                    // 초기화 완료 표시
                    sharedPreferences.edit()
                        .putBoolean("is_data_initialized", true)
                        .apply()
                    Log.d(TAG, "Initial data initialization completed successfully")
                } catch (e: Exception) {
                    Log.e(TAG, "Error during initial data initialization", e)
                    // 초기화 실패 시 flag를 false로 설정하여 다음 시작 시 다시 시도하도록 함
                    sharedPreferences.edit()
                        .putBoolean("is_data_initialized", false)
                        .apply()
                    throw e
                }
            }
        }
    }

    // 초기 데이터만 재초기화 (사용자 데이터는 유지)
    suspend fun reinitializeInitialData() {
        Log.d(TAG, "Starting reinitialization of initial data...")
        withContext(Dispatchers.IO) {
            try {
                // 초기 데이터만 삭제
                database.checklistItemDao().deleteAllItems()
                database.testQuestionDao().deleteAllQuestions()
                database.institutionDao().deleteAllInstitutions()
                Log.d(TAG, "Existing initial data deleted")

                // 새로운 초기 데이터 삽입
                val checklistItems = jsonDataReader.readChecklistItems()
                database.checklistItemDao().insertItems(checklistItems)
                Log.d(TAG, "${checklistItems.size} checklist items reinitialized")

                val testQuestions = jsonDataReader.readTestQuestions()
                database.testQuestionDao().insertQuestions(testQuestions)
                Log.d(TAG, "${testQuestions.size} test questions reinitialized")

                val institutions = jsonDataReader.readInstitutions()
                database.institutionDao().insertInstitutions(institutions)
                Log.d(TAG, "${institutions.size} institutions reinitialized")

                Log.d(TAG, "Initial data reinitialization completed successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error during initial data reinitialization", e)
                throw e
            }
        }
    }
} 