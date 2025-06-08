package com.konkuk.gomgomee.data.local.dao

import androidx.room.*
import com.konkuk.gomgomee.data.local.entity.FavoriteInstitutionEntity
import com.konkuk.gomgomee.data.local.entity.InstitutionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteInstitutionDao {
    @Query("SELECT * FROM favorite_institution")
    fun getAllFavorites(): Flow<List<FavoriteInstitutionEntity>>
    
    @Query("""
        SELECT i.* FROM institution i
        INNER JOIN favorite_institution f ON i.institutionId = f.institutionId
        WHERE f.userNo = :userNo
        ORDER BY f.savedAt DESC
    """)
    fun getFavoriteInstitutionsByUser(userNo: Int): Flow<List<InstitutionEntity>>
    
    @Query("SELECT * FROM favorite_institution WHERE favId = :favId")
    fun getFavoriteById(favId: Int): Flow<FavoriteInstitutionEntity?>
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_institution WHERE userNo = :userNo AND institutionId = :institutionId)")
    fun isFavorite(userNo: Int, institutionId: Int): Flow<Boolean>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteInstitutionEntity)
    
    @Delete
    suspend fun deleteFavorite(favorite: FavoriteInstitutionEntity)
    
    @Query("DELETE FROM favorite_institution WHERE userNo = :userNo AND institutionId = :institutionId")
    suspend fun deleteFavoriteByUserAndInstitution(userNo: Int, institutionId: Int)
    
    @Query("DELETE FROM favorite_institution WHERE userNo = :userNo")
    suspend fun deleteAllFavoritesByUser(userNo: Int)
    
    @Query("SELECT COUNT(*) FROM favorite_institution WHERE userNo = :userNo")
    fun getFavoriteCount(userNo: Int): Flow<Int>
    
    @Transaction
    @Query("""
        SELECT i.* FROM institution i
        INNER JOIN favorite_institution f ON i.institutionId = f.institutionId
        WHERE f.userNo = :userNo AND i.category = :category
        ORDER BY f.savedAt DESC
    """)
    fun getFavoriteInstitutionsByCategory(userNo: Int, category: String): Flow<List<InstitutionEntity>>
} 