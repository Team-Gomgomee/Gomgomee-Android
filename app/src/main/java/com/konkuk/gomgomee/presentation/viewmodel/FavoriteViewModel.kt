package com.konkuk.gomgomee.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.repository.FavoriteRepository
import com.konkuk.gomgomee.presentation.findcare.Institution
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).favoriteDao()
    private val repository = FavoriteRepository(dao)

    private val _favorites = MutableStateFlow<List<Institution>>(emptyList())
    val favorites: StateFlow<List<Institution>> = _favorites

    private fun getCurrentUserNo(): Int {
        return getApplication<Application>()
            .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getInt("current_user_no", -1)
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val userNo = getCurrentUserNo()
            _favorites.value = repository.getFavorites(userNo)
        }
    }

    suspend fun isFavorite(institutionId: Int): Boolean {
        val userNo = getCurrentUserNo()
        return repository.isFavorite(userNo, institutionId)
    }

    fun addFavorite(institution: Institution) {
        viewModelScope.launch {
            val userNo = getCurrentUserNo()
            repository.add(institution, userNo)
            loadFavorites()
        }
    }

    fun removeFavorite(institutionId: Int) {
        viewModelScope.launch {
            val userNo = getCurrentUserNo()
            repository.remove(institutionId, userNo)
            loadFavorites()
        }
    }
}
