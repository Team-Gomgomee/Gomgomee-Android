package com.konkuk.gomgomee.data.local.dao

import androidx.room.*
import com.konkuk.gomgomee.data.local.entity.ChecklistResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistResultDao {
    @Query("SELECT * FROM checklist_result")
    fun getAllResults(): Flow<List<ChecklistResultEntity>>
    
    @Query("SELECT * FROM checklist_result WHERE userNo = :userNo ORDER BY createdAt DESC")
    fun getResultsByUser(userNo: Int): Flow<List<ChecklistResultEntity>>
    
    @Query("SELECT * FROM checklist_result WHERE resultId = :resultId")
    fun getResultById(resultId: Int): Flow<ChecklistResultEntity?>
    
    @Query("SELECT * FROM checklist_result WHERE userNo = :userNo ORDER BY createdAt DESC LIMIT 1")
    fun getLatestResultByUser(userNo: Int): Flow<ChecklistResultEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: ChecklistResultEntity)
    
    @Update
    suspend fun updateResult(result: ChecklistResultEntity)
    
    @Delete
    suspend fun deleteResult(result: ChecklistResultEntity)
    
    @Query("DELETE FROM checklist_result WHERE userNo = :userNo")
    suspend fun deleteResultsByUser(userNo: Int)
} 