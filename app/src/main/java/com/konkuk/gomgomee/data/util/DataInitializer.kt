package com.konkuk.gomgomee.data.util

import android.content.Context
import com.konkuk.gomgomee.data.local.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataInitializer(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val database = AppDatabase.getDatabase(context)
    private val jsonDataReader = JsonDataReader(context)

    suspend fun initializeDataIfNeeded() {
        val isInitialized = sharedPreferences.getBoolean("is_data_initialized", false)
        
        if (!isInitialized) {
            withContext(Dispatchers.IO) {
                try {
                    // 사용자 데이터 초기화
                    val users = jsonDataReader.readUsers()
                    database.userDao().insertUsers(users)

                    // 체크리스트 문항 초기화
                    val checklistItems = jsonDataReader.readChecklistItems()
                    database.checklistItemDao().insertItems(checklistItems)

                    // 테스트 문제 초기화
                    val testQuestions = jsonDataReader.readTestQuestions()
                    database.testQuestionDao().insertQuestions(testQuestions)

                    // 기관 정보 초기화
                    val institutions = jsonDataReader.readInstitutions()
                    database.institutionDao().insertInstitutions(institutions)

                    // 초기화 완료 표시
                    sharedPreferences.edit()
                        .putBoolean("is_data_initialized", true)
                        .apply()
                } catch (e: Exception) {
                    // 초기화 실패 시 로그 기록 또는 에러 처리
                    e.printStackTrace()
                }
            }
        }
    }

    // 데이터 재초기화가 필요한 경우 사용
    suspend fun reinitializeData() {
        withContext(Dispatchers.IO) {
            try {
                // 기존 데이터 삭제
                database.userDao().deleteAllUsers()
                database.checklistItemDao().deleteAllItems()
                database.testQuestionDao().deleteAllQuestions()
                database.institutionDao().deleteAllInstitutions()

                // 새로운 데이터 삽입
                database.userDao().insertUsers(jsonDataReader.readUsers())
                database.checklistItemDao().insertItems(jsonDataReader.readChecklistItems())
                database.testQuestionDao().insertQuestions(jsonDataReader.readTestQuestions())
                database.institutionDao().insertInstitutions(jsonDataReader.readInstitutions())

                // 초기화 상태 업데이트
                sharedPreferences.edit()
                    .putBoolean("is_data_initialized", true)
                    .apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
} 