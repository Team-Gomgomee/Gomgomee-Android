package com.konkuk.gomgomee.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.InstitutionEntity
import com.konkuk.gomgomee.data.repository.FavoriteInstitutionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteInstitutionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteInstitutionRepository

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        val favoriteInstitutionDao = AppDatabase.getDatabase(application).favoriteInstitutionDao()
        repository = FavoriteInstitutionRepository(favoriteInstitutionDao)
    }

    fun getFavoriteInstitutions(userNo: Int): Flow<List<InstitutionEntity>> {
        return repository.getFavoriteInstitutionsByUser(userNo)
    }

    fun isFavorite(userNo: Int, institutionId: Int): Flow<Boolean> {
        return repository.isFavorite(userNo, institutionId)
    }

    fun addToFavorites(userNo: Int, institutionId: Int) = viewModelScope.launch {
        try {
            repository.addToFavorites(userNo, institutionId)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun removeFromFavorites(userNo: Int, institutionId: Int) = viewModelScope.launch {
        try {
            repository.removeFromFavorites(userNo, institutionId)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun removeAllFavorites(userNo: Int) = viewModelScope.launch {
        try {
            repository.removeAllFavorites(userNo)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun getFavoriteCount(userNo: Int): Flow<Int> {
        return repository.getFavoriteCount(userNo)
    }

    fun getFavoriteInstitutionsByCategory(userNo: Int, category: String): Flow<List<InstitutionEntity>> {
        return repository.getFavoriteInstitutionsByCategory(userNo, category)
    }

    // 즐겨찾기 토글 기능
    fun toggleFavorite(userNo: Int, institutionId: Int) = viewModelScope.launch {
        try {
            val isFavorite = repository.isFavorite(userNo, institutionId)
            isFavorite.collect { isCurrentlyFavorite ->
                if (isCurrentlyFavorite) {
                    repository.removeFromFavorites(userNo, institutionId)
                } else {
                    repository.addToFavorites(userNo, institutionId)
                }
            }
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }
} 