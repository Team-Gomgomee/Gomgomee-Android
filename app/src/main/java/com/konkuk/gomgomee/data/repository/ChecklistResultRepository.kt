package com.konkuk.gomgomee.data.repository

import com.konkuk.gomgomee.data.local.dao.ChecklistResultDao
import com.konkuk.gomgomee.data.local.entity.ChecklistResultEntity
import kotlinx.coroutines.flow.Flow

class ChecklistResultRepository(private val checklistResultDao: ChecklistResultDao) {
    val allResults: Flow<List<ChecklistResultEntity>> = checklistResultDao.getAllResults()
    
    fun getResultsByUser(userNo: Int): Flow<List<ChecklistResultEntity>> {
        return checklistResultDao.getResultsByUser(userNo)
    }
    
    fun getLatestResultByUser(userNo: Int): Flow<ChecklistResultEntity?> {
        return checklistResultDao.getLatestResultByUser(userNo)
    }
    
    suspend fun insert(result: ChecklistResultEntity) {
        checklistResultDao.insertResult(result)
    }
    
    suspend fun update(result: ChecklistResultEntity) {
        checklistResultDao.updateResult(result)
    }
    
    suspend fun delete(result: ChecklistResultEntity) {
        checklistResultDao.deleteResult(result)
    }
    
    suspend fun deleteResultsByUser(userNo: Int) {
        checklistResultDao.deleteResultsByUser(userNo)
    }
    
    fun getResultById(resultId: Int): Flow<ChecklistResultEntity?> {
        return checklistResultDao.getResultById(resultId)
    }
} 