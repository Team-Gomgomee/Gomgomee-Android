//package com.konkuk.gomgomee.presentation.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import com.konkuk.gomgomee.data.local.database.AppDatabase
//import com.konkuk.gomgomee.data.local.entity.ChecklistResultEntity
//import com.konkuk.gomgomee.data.repository.ChecklistResultRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class ChecklistResultViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: ChecklistResultRepository
//    val allResults: Flow<List<ChecklistResultEntity>>
//
//    private val _errorMessage = MutableStateFlow<String?>(null)
//    val errorMessage = _errorMessage.asStateFlow()
//
//    init {
//        val checklistResultDao = AppDatabase.getDatabase(application).checklistResultDao()
//        repository = ChecklistResultRepository(checklistResultDao)
//        allResults = repository.allResults
//    }
//
//    fun getResultsByUser(userNo: Int): Flow<List<ChecklistResultEntity>> {
//        return repository.getResultsByUser(userNo)
//    }
//
//    fun getLatestResultByUser(userNo: Int): Flow<ChecklistResultEntity?> {
//        return repository.getLatestResultByUser(userNo)
//    }
//
//    fun insert(result: ChecklistResultEntity) = viewModelScope.launch {
//        try {
//            repository.insert(result)
//        } catch (e: Exception) {
//            _errorMessage.value = e.message
//        }
//    }
//
//    fun update(result: ChecklistResultEntity) = viewModelScope.launch {
//        try {
//            repository.update(result)
//        } catch (e: Exception) {
//            _errorMessage.value = e.message
//        }
//    }
//
//    fun delete(result: ChecklistResultEntity) = viewModelScope.launch {
//        try {
//            repository.delete(result)
//        } catch (e: Exception) {
//            _errorMessage.value = e.message
//        }
//    }
//
//    fun deleteResultsByUser(userNo: Int) = viewModelScope.launch {
//        try {
//            repository.deleteResultsByUser(userNo)
//        } catch (e: Exception) {
//            _errorMessage.value = e.message
//        }
//    }
//}