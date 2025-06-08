package com.konkuk.gomgomee.data.local.dao

import androidx.room.*
import com.konkuk.gomgomee.data.local.entity.ChecklistItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistItemDao {
    @Query("SELECT * FROM checklist_item")
    fun getAllItems(): Flow<List<ChecklistItemEntity>>
    
    @Query("SELECT * FROM checklist_item WHERE itemId = :itemId")
    fun getItemById(itemId: Int): Flow<ChecklistItemEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ChecklistItemEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ChecklistItemEntity>)
    
    @Update
    suspend fun updateItem(item: ChecklistItemEntity)
    
    @Delete
    suspend fun deleteItem(item: ChecklistItemEntity)
    
    @Query("DELETE FROM checklist_item")
    suspend fun deleteAllItems()
} 