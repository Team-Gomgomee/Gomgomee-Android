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
    private val userNo: Int = application
        .getSharedPreferences("app_prefs",Context.MODE_PRIVATE)
        .getInt("current_user_no",-1)

    private val dao = AppDatabase.getDatabase(application).favoriteDao()
    private val repository = FavoriteRepository(dao, userNo)

    private val _favorites = MutableStateFlow<List<Institution>>(emptyList())
    val favorites: StateFlow<List<Institution>> = _favorites

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites()
        }
    }

    suspend fun isFavorite(institutionId: Int): Boolean {
        val prefs = getApplication<Application>()
            .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val userNo = prefs.getInt("current_user_no", -1)

        return repository.isFavorite(userNo, institutionId)
    }

    suspend fun addFavorite(institution: Institution) {
        val prefs = getApplication<Application>()
            .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val userNo = prefs.getInt("current_user_no", -1)

        repository.add(institution, userNo)
        loadFavorites()
    }

    suspend fun removeFavorite(institutionId: Int) {
        val prefs = getApplication<Application>()
            .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val userNo = prefs.getInt("current_user_no", -1)

        repository.remove(institutionId, userNo)
        loadFavorites()
    }
}