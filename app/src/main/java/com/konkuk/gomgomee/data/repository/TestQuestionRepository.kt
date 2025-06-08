package com.konkuk.gomgomee.data.repository

import com.konkuk.gomgomee.data.local.dao.TestQuestionDao
import com.konkuk.gomgomee.data.local.entity.TestQuestionEntity
import kotlinx.coroutines.flow.Flow

class TestQuestionRepository(private val testQuestionDao: TestQuestionDao) {
    val allQuestions: Flow<List<TestQuestionEntity>> = testQuestionDao.getAllQuestions()
    
    fun getQuestionsByDomain(domain: String): Flow<List<TestQuestionEntity>> {
        return testQuestionDao.getQuestionsByDomain(domain)
    }
    
    suspend fun getRandomQuestionsByDomain(domain: String, count: Int): List<TestQuestionEntity> {
        return testQuestionDao.getRandomQuestionsByDomain(domain, count)
    }
    
    suspend fun insert(question: TestQuestionEntity) {
        testQuestionDao.insertQuestion(question)
    }
    
    suspend fun insertAll(questions: List<TestQuestionEntity>) {
        testQuestionDao.insertQuestions(questions)
    }
    
    suspend fun update(question: TestQuestionEntity) {
        testQuestionDao.updateQuestion(question)
    }
    
    suspend fun delete(question: TestQuestionEntity) {
        testQuestionDao.deleteQuestion(question)
    }
    
    suspend fun deleteByDomain(domain: String) {
        testQuestionDao.deleteQuestionsByDomain(domain)
    }
    
    suspend fun deleteAll() {
        testQuestionDao.deleteAllQuestions()
    }
    
    suspend fun getQuestionCountByDomain(domain: String): Int {
        return testQuestionDao.getQuestionCountByDomain(domain)
    }
    
    fun getQuestionById(questionId: Int): Flow<TestQuestionEntity?> {
        return testQuestionDao.getQuestionById(questionId)
    }
} 