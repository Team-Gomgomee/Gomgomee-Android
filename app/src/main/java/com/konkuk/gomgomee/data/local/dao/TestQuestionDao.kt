package com.konkuk.gomgomee.data.local.dao

import androidx.room.*
import com.konkuk.gomgomee.data.local.entity.TestQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestQuestionDao {
    @Query("SELECT * FROM test_question")
    fun getAllQuestions(): Flow<List<TestQuestionEntity>>
    
    @Query("SELECT * FROM test_question WHERE domain = :domain")
    fun getQuestionsByDomain(domain: String): Flow<List<TestQuestionEntity>>
    
    @Query("SELECT * FROM test_question WHERE questionId = :questionId")
    fun getQuestionById(questionId: Int): Flow<TestQuestionEntity?>
    
    @Query("SELECT * FROM test_question WHERE domain = :domain ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomQuestionsByDomain(domain: String, count: Int): List<TestQuestionEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: TestQuestionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<TestQuestionEntity>)
    
    @Update
    suspend fun updateQuestion(question: TestQuestionEntity)
    
    @Delete
    suspend fun deleteQuestion(question: TestQuestionEntity)
    
    @Query("DELETE FROM test_question WHERE domain = :domain")
    suspend fun deleteQuestionsByDomain(domain: String)
    
    @Query("DELETE FROM test_question")
    suspend fun deleteAllQuestions()
    
    @Query("SELECT COUNT(*) FROM test_question WHERE domain = :domain")
    suspend fun getQuestionCountByDomain(domain: String): Int
} 