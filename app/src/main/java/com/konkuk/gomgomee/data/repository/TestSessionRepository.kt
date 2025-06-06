package com.konkuk.gomgomee.data.repository

import com.konkuk.gomgomee.data.local.dao.TestSessionDao
import com.konkuk.gomgomee.data.local.entity.TestSessionEntity
import kotlinx.coroutines.flow.Flow

class TestSessionRepository(private val testSessionDao: TestSessionDao) {
    val allSessions: Flow<List<TestSessionEntity>> = testSessionDao.getAllSessions()
    
    fun getSessionsByUser(userNo: Int): Flow<List<TestSessionEntity>> {
        return testSessionDao.getSessionsByUser(userNo)
    }
    
    fun getSessionsByUserAndDomain(userNo: Int, domain: String): Flow<List<TestSessionEntity>> {
        return testSessionDao.getSessionsByUserAndDomain(userNo, domain)
    }
    
    fun getSessionById(sessionId: Int): Flow<TestSessionEntity?> {
        return testSessionDao.getSessionById(sessionId)
    }
    
    suspend fun insert(session: TestSessionEntity) {
        testSessionDao.insertSession(session)
    }
    
    suspend fun update(session: TestSessionEntity) {
        testSessionDao.updateSession(session)
    }
    
    suspend fun delete(session: TestSessionEntity) {
        testSessionDao.deleteSession(session)
    }
    
    suspend fun deleteSessionsByUser(userNo: Int) {
        testSessionDao.deleteSessionsByUser(userNo)
    }
    
    suspend fun getAverageScoreByDomain(userNo: Int, domain: String): Float? {
        return testSessionDao.getAverageScoreByDomain(userNo, domain)
    }
    
    suspend fun getLatestSession(userNo: Int): TestSessionEntity? {
        return testSessionDao.getLatestSession(userNo)
    }
} 