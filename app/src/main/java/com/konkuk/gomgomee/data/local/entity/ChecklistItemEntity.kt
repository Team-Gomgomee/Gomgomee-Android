package com.konkuk.gomgomee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_item")
data class ChecklistItemEntity(
    @PrimaryKey(autoGenerate = true) 
    val itemId: Int = 0,
    val questionText: String,
    val createdAt: Long = System.currentTimeMillis()
) 