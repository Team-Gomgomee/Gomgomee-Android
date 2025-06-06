package com.konkuk.gomgomee.data.repository

import com.konkuk.gomgomee.data.local.dao.FavoriteInstitutionDao
import com.konkuk.gomgomee.data.local.entity.FavoriteInstitutionEntity
import com.konkuk.gomgomee.data.local.entity.InstitutionEntity
import kotlinx.coroutines.flow.Flow

class FavoriteInstitutionRepository(private val favoriteInstitutionDao: FavoriteInstitutionDao) {
    val allFavorites: Flow<List<FavoriteInstitutionEntity>> = favoriteInstitutionDao.getAllFavorites()
    
    fun getFavoriteInstitutionsByUser(userNo: Int): Flow<List<InstitutionEntity>> {
        return favoriteInstitutionDao.getFavoriteInstitutionsByUser(userNo)
    }
    
    fun getFavoriteById(favId: Int): Flow<FavoriteInstitutionEntity?> {
        return favoriteInstitutionDao.getFavoriteById(favId)
    }
    
    fun isFavorite(userNo: Int, institutionId: Int): Flow<Boolean> {
        return favoriteInstitutionDao.isFavorite(userNo, institutionId)
    }
    
    suspend fun addToFavorites(userNo: Int, institutionId: Int) {
        val favorite = FavoriteInstitutionEntity(
            userNo = userNo,
            institutionId = institutionId
        )
        favoriteInstitutionDao.insertFavorite(favorite)
    }
    
    suspend fun removeFromFavorites(userNo: Int, institutionId: Int) {
        favoriteInstitutionDao.deleteFavoriteByUserAndInstitution(userNo, institutionId)
    }
    
    suspend fun removeAllFavorites(userNo: Int) {
        favoriteInstitutionDao.deleteAllFavoritesByUser(userNo)
    }
    
    fun getFavoriteCount(userNo: Int): Flow<Int> {
        return favoriteInstitutionDao.getFavoriteCount(userNo)
    }
    
    fun getFavoriteInstitutionsByCategory(userNo: Int, category: String): Flow<List<InstitutionEntity>> {
        return favoriteInstitutionDao.getFavoriteInstitutionsByCategory(userNo, category)
    }
} 