package com.konkuk.gomgomee.data.local.entity

import androidx.room.*

@Entity(
    tableName = "favorite_institution",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userNo"],
            childColumns = ["userNo"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = InstitutionEntity::class,
            parentColumns = ["institutionId"],
            childColumns = ["institutionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userNo", "institutionId"], unique = true),
        Index(value = ["institutionId"])
    ]
)
data class FavoriteInstitutionEntity(
    @PrimaryKey(autoGenerate = true) 
    val favId: Int = 0,
    
    val userNo: Int,
    val institutionId: Int,
    val savedAt: Long = System.currentTimeMillis()
) 