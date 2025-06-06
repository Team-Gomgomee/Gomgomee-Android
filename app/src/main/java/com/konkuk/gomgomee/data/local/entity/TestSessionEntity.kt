package com.konkuk.gomgomee.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "test_session",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["userNo"],
        childColumns = ["userNo"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TestSessionEntity(
    @PrimaryKey(autoGenerate = true) 
    val sessionId: Int = 0,
    val userNo: Int,
    val domain: String, // 진단한 영역
    val startedAt: Long,
    val finishedAt: Long?,
    val correctCount: Int = 0,
    val totalCount: Int = 0
) 