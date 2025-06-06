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
                    Log.d(TAG, "Starting data initialization...")
                    
                    // 사용자 데이터 초기화
                    val users = jsonDataReader.readUsers()
                    Log.d(TAG, "Read ${users.size} users from JSON")
                    database.userDao().insertUsers(users)

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
                    Log.d(TAG, "Data initialization completed successfully")
                } catch (e: Exception) {
                    Log.e(TAG, "Error during data initialization", e)
                    // 초기화 실패 시 flag를 false로 설정하여 다음 시작 시 다시 시도하도록 함
                    sharedPreferences.edit()
                        .putBoolean("is_data_initialized", false)
                        .apply()
                    throw e
                }
            }
        }
    }

    // 데이터 재초기화가 필요한 경우 사용
    suspend fun reinitializeData() {
        Log.d(TAG, "Starting data reinitialization...")
        withContext(Dispatchers.IO) {
            try {
                // 기존 데이터 삭제
                database.userDao().deleteAllUsers()
                database.checklistItemDao().deleteAllItems()
                database.testQuestionDao().deleteAllQuestions()
                database.institutionDao().deleteAllInstitutions()
                Log.d(TAG, "Existing data deleted")

                // 새로운 데이터 삽입
                val users = jsonDataReader.readUsers()
                database.userDao().insertUsers(users)
                Log.d(TAG, "${users.size} users reinitialized")

                val checklistItems = jsonDataReader.readChecklistItems()
                database.checklistItemDao().insertItems(checklistItems)
                Log.d(TAG, "${checklistItems.size} checklist items reinitialized")

                val testQuestions = jsonDataReader.readTestQuestions()
                database.testQuestionDao().insertQuestions(testQuestions)
                Log.d(TAG, "${testQuestions.size} test questions reinitialized")

                val institutions = jsonDataReader.readInstitutions()
                database.institutionDao().insertInstitutions(institutions)
                Log.d(TAG, "${institutions.size} institutions reinitialized")

                // 초기화 상태 업데이트
                sharedPreferences.edit()
                    .putBoolean("is_data_initialized", true)
                    .apply()
                Log.d(TAG, "Data reinitialization completed successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error during data reinitialization", e)
                sharedPreferences.edit()
                    .putBoolean("is_data_initialized", false)
                    .apply()
                throw e
            }
        }
    }
} 