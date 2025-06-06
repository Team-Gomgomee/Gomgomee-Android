package com.konkuk.gomgomee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklist_item")
data class ChecklistItemEntity(
    @PrimaryKey
    val itemId: Int,
    val questionText: String
) 