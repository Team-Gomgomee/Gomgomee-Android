package com.konkuk.gomgomee.data.repository

import com.konkuk.gomgomee.data.local.dao.InstitutionDao
import com.konkuk.gomgomee.data.local.entity.InstitutionEntity
import kotlinx.coroutines.flow.Flow

class InstitutionRepository(private val institutionDao: InstitutionDao) {
    val allInstitutions: Flow<List<InstitutionEntity>> = institutionDao.getAllInstitutions()
    
    fun getInstitutionsByCategory(category: String): Flow<List<InstitutionEntity>> {
        return institutionDao.getInstitutionsByCategory(category)
    }
    
    fun getInstitutionById(institutionId: Int): Flow<InstitutionEntity?> {
        return institutionDao.getInstitutionById(institutionId)
    }
    
    suspend fun insert(institution: InstitutionEntity) {
        institutionDao.insertInstitution(institution)
    }
    
    suspend fun insertAll(institutions: List<InstitutionEntity>) {
        institutionDao.insertInstitutions(institutions)
    }
    
    suspend fun update(institution: InstitutionEntity) {
        institutionDao.updateInstitution(institution)
    }
    
    suspend fun delete(institution: InstitutionEntity) {
        institutionDao.deleteInstitution(institution)
    }
    
    suspend fun deleteAll() {
        institutionDao.deleteAllInstitutions()
    }
    
    // 지도 관련 기능
    fun getInstitutionsInBounds(
        minLat: Double,
        maxLat: Double,
        minLng: Double,
        maxLng: Double
    ): Flow<List<InstitutionEntity>> {
        return institutionDao.getInstitutionsInBounds(minLat, maxLat, minLng, maxLng)
    }
    
    fun getInstitutionsWithinRadius(
        lat: Double,
        lng: Double,
        radiusKm: Double
    ): Flow<List<InstitutionEntity>> {
        return institutionDao.getInstitutionsWithinRadius(lat, lng, radiusKm)
    }
    
    // 검색 기능
    fun searchInstitutionsByName(query: String): Flow<List<InstitutionEntity>> {
        return institutionDao.searchInstitutionsByName(query)
    }
    
    fun searchInstitutionsByAddress(query: String): Flow<List<InstitutionEntity>> {
        return institutionDao.searchInstitutionsByAddress(query)
    }
} 