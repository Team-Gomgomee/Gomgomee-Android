package com.konkuk.gomgomee.data.local.dao

import androidx.room.*
import com.konkuk.gomgomee.data.local.entity.InstitutionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InstitutionDao {
    @Query("SELECT * FROM institution")
    fun getAllInstitutions(): Flow<List<InstitutionEntity>>
    
    @Query("SELECT * FROM institution WHERE category = :category")
    fun getInstitutionsByCategory(category: String): Flow<List<InstitutionEntity>>
    
    @Query("SELECT * FROM institution WHERE institutionId = :institutionId")
    fun getInstitutionById(institutionId: Int): Flow<InstitutionEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstitution(institution: InstitutionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstitutions(institutions: List<InstitutionEntity>)
    
    @Update
    suspend fun updateInstitution(institution: InstitutionEntity)
    
    @Delete
    suspend fun deleteInstitution(institution: InstitutionEntity)
    
    @Query("DELETE FROM institution")
    suspend fun deleteAllInstitutions()
    
    // 지도 관련 쿼리
    @Query("""
        SELECT * FROM institution 
        WHERE latitude BETWEEN :minLat AND :maxLat 
        AND longitude BETWEEN :minLng AND :maxLng
    """)
    fun getInstitutionsInBounds(
        minLat: Double,
        maxLat: Double,
        minLng: Double,
        maxLng: Double
    ): Flow<List<InstitutionEntity>>
    
    // 현재 위치 기준 반경 내 기관 검색 (Haversine 공식 사용)
    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT * FROM (
            SELECT *, (
                6371 * acos(
                    cos(radians(:lat)) * cos(radians(latitude)) *
                    cos(radians(longitude) - radians(:lng)) +
                    sin(radians(:lat)) * sin(radians(latitude))
                )
            ) as distance
            FROM institution
        ) WHERE distance <= :radiusKm
        ORDER BY distance
    """)
    fun getInstitutionsWithinRadius(
        lat: Double,
        lng: Double,
        radiusKm: Double
    ): Flow<List<InstitutionEntity>>
    
    // 기관 이름으로 검색
    @Query("SELECT * FROM institution WHERE name LIKE '%' || :query || '%'")
    fun searchInstitutionsByName(query: String): Flow<List<InstitutionEntity>>
    
    // 주소로 검색
    @Query("SELECT * FROM institution WHERE address LIKE '%' || :query || '%'")
    fun searchInstitutionsByAddress(query: String): Flow<List<InstitutionEntity>>
} 