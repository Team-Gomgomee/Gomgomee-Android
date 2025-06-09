package com.konkuk.gomgomee.data.repository

import com.konkuk.gomgomee.data.local.dao.FavoriteDao
import com.konkuk.gomgomee.data.local.entity.FavoriteEntity
import com.konkuk.gomgomee.presentation.findcare.Institution

class FavoriteRepository(
    private val favoriteDao: FavoriteDao,
    private val userNo: Int
) {

    suspend fun getFavorites(): List<Institution> {
        return favoriteDao.getFavoritesByUser(userNo).map { it.toInstitution() }
    }

    suspend fun add(institution: Institution, userNo: Int) {
        favoriteDao.insert(institution.toEntity(userNo))
    }

    suspend fun remove(institutionId: Int, userNo: Int) {
        favoriteDao.delete(userNo, institutionId)
    }

    suspend fun isFavorite(userNo: Int, institutionId: Int): Boolean {
        return favoriteDao.isFavorite(userNo, institutionId)
    }

    private fun Institution.toEntity(userId: Int) = FavoriteEntity(
        userNo = userNo,
        institutionId = institutionId,
        name = name,
        address = address,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        category = category
    )

    private fun FavoriteEntity.toInstitution(): Institution = Institution(
        institutionId = institutionId,
        name = name,
        address = address,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        category = category
    )
}


