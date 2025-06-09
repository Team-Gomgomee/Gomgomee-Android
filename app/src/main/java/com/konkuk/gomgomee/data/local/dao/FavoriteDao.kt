package com.konkuk.gomgomee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.konkuk.gomgomee.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("SELECT * FROM Favorite WHERE userNo = :userNo")
    suspend fun getFavoritesByUser(userNo: Int): List<FavoriteEntity>

    @Query("DELETE FROM Favorite WHERE userNo = :userNo AND institutionId = :institutionId")
    suspend fun delete(userNo: Int, institutionId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM Favorite WHERE userNo = :userNo AND institutionId = :institutionId)")
    suspend fun isFavorite(userNo: Int, institutionId: Int): Boolean
}