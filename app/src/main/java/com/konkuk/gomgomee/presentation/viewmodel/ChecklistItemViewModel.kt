package com.konkuk.gomgomee.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.ChecklistItemEntity
import com.konkuk.gomgomee.data.repository.ChecklistItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChecklistItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ChecklistItemRepository
    val allItems: Flow<List<ChecklistItemEntity>>

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        val checklistItemDao = AppDatabase.getDatabase(application).checklistItemDao()
        repository = ChecklistItemRepository(checklistItemDao)
        allItems = repository.allItems
    }

    fun insert(item: ChecklistItemEntity) = viewModelScope.launch {
        try {
            repository.insert(item)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun insertAll(items: List<ChecklistItemEntity>) = viewModelScope.launch {
        try {
            repository.insertAll(items)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun update(item: ChecklistItemEntity) = viewModelScope.launch {
        try {
            repository.update(item)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun delete(item: ChecklistItemEntity) = viewModelScope.launch {
        try {
            repository.delete(item)
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }

    fun deleteAll() = viewModelScope.launch {
        try {
            repository.deleteAll()
        } catch (e: Exception) {
            _errorMessage.value = e.message
        }
    }
} 