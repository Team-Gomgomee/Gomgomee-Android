package com.konkuk.gomgomee.data.repository

import com.konkuk.gomgomee.data.local.dao.ChecklistItemDao
import com.konkuk.gomgomee.data.local.entity.ChecklistItemEntity
import kotlinx.coroutines.flow.Flow

class ChecklistItemRepository(private val checklistItemDao: ChecklistItemDao) {
    val allItems: Flow<List<ChecklistItemEntity>> = checklistItemDao.getAllItems()
    
    suspend fun insert(item: ChecklistItemEntity) {
        checklistItemDao.insertItem(item)
    }
    
    suspend fun insertAll(items: List<ChecklistItemEntity>) {
        checklistItemDao.insertItems(items)
    }
    
    suspend fun update(item: ChecklistItemEntity) {
        checklistItemDao.updateItem(item)
    }
    
    suspend fun delete(item: ChecklistItemEntity) {
        checklistItemDao.deleteItem(item)
    }
    
    suspend fun deleteAll() {
        checklistItemDao.deleteAllItems()
    }
    
    fun getItemById(itemId: Int): Flow<ChecklistItemEntity?> {
        return checklistItemDao.getItemById(itemId)
    }
} 