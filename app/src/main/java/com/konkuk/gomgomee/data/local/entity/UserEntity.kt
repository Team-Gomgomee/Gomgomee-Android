package com.konkuk.gomgomee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) 
    val userNo: Int = 0,
    val id: String,
    val password: String,
    val name: String,
    val age: Int,
    val address: String?,
    val note: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 