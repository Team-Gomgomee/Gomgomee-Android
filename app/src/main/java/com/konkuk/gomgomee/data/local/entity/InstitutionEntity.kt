package com.konkuk.gomgomee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "institution")
data class InstitutionEntity(
    @PrimaryKey(autoGenerate = true) 
    val institutionId: Int = 0,
    
    val name: String,
    val address: String,
    val phone: String?,
    val latitude: Double,
    val longitude: Double,
    val category: String,
    
    // 추가 메타데이터
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 