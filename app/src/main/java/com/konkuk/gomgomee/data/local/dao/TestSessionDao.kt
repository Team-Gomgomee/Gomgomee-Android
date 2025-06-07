package com.konkuk.gomgomee.data.local.dao

import androidx.room.*
import com.konkuk.gomgomee.data.local.entity.TestSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestSessionDao {
    @Query("SELECT * FROM test_session")
    fun getAllSessions(): Flow<List<TestSessionEntity>>
    
    @Query("SELECT * FROM test_session WHERE userNo = :userNo ORDER BY startedAt DESC")
    fun getSessionsByUser(userNo: Int): Flow<List<TestSessionEntity>>
    
    @Query("SELECT * FROM test_session WHERE userNo = :userNo AND domain = :domain ORDER BY startedAt DESC")
    fun getSessionsByUserAndDomain(userNo: Int, domain: String): Flow<List<TestSessionEntity>>
    
    @Query("SELECT * FROM test_session WHERE sessionId = :sessionId")
    fun getSessionById(sessionId: Int): Flow<TestSessionEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: TestSessionEntity): Long
    
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSession(session: TestSessionEntity)
    
    @Delete
    suspend fun deleteSession(session: TestSessionEntity)
    
    @Query("DELETE FROM test_session WHERE userNo = :userNo")
    suspend fun deleteSessionsByUser(userNo: Int)
    
    @Query("SELECT AVG(CAST(correctCount AS FLOAT) / CAST(totalCount AS FLOAT) * 100) FROM test_session WHERE userNo = :userNo AND domain = :domain")
    suspend fun getAverageScoreByDomain(userNo: Int, domain: String): Float?
    
    @Query("SELECT * FROM test_session WHERE userNo = :userNo ORDER BY startedAt DESC LIMIT 1")
    suspend fun getLatestSession(userNo: Int): TestSessionEntity?
} 