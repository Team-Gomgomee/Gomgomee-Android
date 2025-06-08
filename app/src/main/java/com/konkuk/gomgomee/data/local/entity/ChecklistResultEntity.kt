package com.konkuk.gomgomee.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "checklist_result",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["userNo"],
        childColumns = ["userNo"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userNo")] // 외래키에 대한 인덱스 추가
)
data class ChecklistResultEntity(
    @PrimaryKey(autoGenerate = true) 
    val resultId: Int = 0,
    val userNo: Int,
    val yesCount: Int,
    val createdAt: Long = System.currentTimeMillis()
) 