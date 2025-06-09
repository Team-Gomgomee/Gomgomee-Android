package com.konkuk.gomgomee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userNo: Int,
    val institutionId: Int,
    val name: String,
    val address: String,
    val phone: String?,
    val latitude: Double,
    val longitude: Double,
    val category: String
)
