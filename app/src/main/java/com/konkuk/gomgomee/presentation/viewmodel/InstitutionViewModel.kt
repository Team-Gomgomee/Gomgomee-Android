package com.konkuk.gomgomee.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.InstitutionEntity
import com.konkuk.gomgomee.data.repository.InstitutionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InstitutionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: InstitutionRepository
    val allInstitutions: Flow<List<InstitutionEntity>>

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        val institutionDao = AppDatabase.getDatabase(application).institutionDao()
        repository = InstitutionRepository(institutionDao)
        allInstitutions = repository.allInstitutions
    }

    fun getInstitutionsByCategory(category: String): Flow<List<InstitutionEntity>> {
        return repository.getInstitutionsByCategory(category)
    }

    fun insert(institution: InstitutionEntity) = viewModelScope.launch {
        try {
            repository.insert(institution)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun insertAll(institutions: List<InstitutionEntity>) = viewModelScope.launch {
        try {
            repository.insertAll(institutions)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun update(institution: InstitutionEntity) = viewModelScope.launch {
        try {
            repository.update(institution)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun delete(institution: InstitutionEntity) = viewModelScope.launch {
        try {
            repository.delete(institution)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    // 지도 관련 기능
    fun getInstitutionsInBounds(
        minLat: Double,
        maxLat: Double,
        minLng: Double,
        maxLng: Double
    ): Flow<List<InstitutionEntity>> {
        return repository.getInstitutionsInBounds(minLat, maxLat, minLng, maxLng)
    }

    fun getInstitutionsWithinRadius(
        lat: Double,
        lng: Double,
        radiusKm: Double
    ): Flow<List<InstitutionEntity>> {
        return repository.getInstitutionsWithinRadius(lat, lng, radiusKm)
    }

    // 검색 기능
    fun searchInstitutions(query: String): Flow<List<InstitutionEntity>> {
        return repository.searchInstitutionsByName(query)
    }

    fun searchInstitutionsByAddress(query: String): Flow<List<InstitutionEntity>> {
        return repository.searchInstitutionsByAddress(query)
    }
} 