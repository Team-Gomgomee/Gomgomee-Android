package com.konkuk.gomgomee.presentation.diagnosis

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.ChecklistItemEntity
import com.konkuk.gomgomee.data.repository.ChecklistItemRepository
import com.konkuk.gomgomee.util.context.toast
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ChecklistItem(
    val id: Int,
    val question: String,
    var answer: Boolean? = null
)

class ChecklistViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "ChecklistViewModel"
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                ChecklistViewModel(application)
            }
        }
    }

    private val repository: ChecklistItemRepository
    var checklistItems by mutableStateOf<List<ChecklistItem>>(emptyList())
        private set

    val isAllItemsAnswered: Boolean
        get() = checklistItems.all { it.answer != null }

    init {
        val checklistItemDao = AppDatabase.getDatabase(application).checklistItemDao()
        repository = ChecklistItemRepository(checklistItemDao)
        loadChecklistItems()
    }

    private fun loadChecklistItems() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Loading checklist items from database")
                val items = repository.allItems.first()
                Log.d(TAG, "Loaded ${items.size} checklist items")
                
                if (items.isEmpty()) {
                    Log.w(TAG, "No checklist items found in database")
                    getApplication<Application>().toast("체크리스트 항목을 불러올 수 없습니다.")
                    return@launch
                }

                checklistItems = items.map { entity ->
                    ChecklistItem(
                        id = entity.itemId,
                        question = entity.questionText
                    )
                }
                Log.d(TAG, "Successfully mapped ${checklistItems.size} items to UI model")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading checklist items", e)
                getApplication<Application>().toast("체크리스트 항목을 불러오는 중 오류가 발생했습니다.")
            }
        }
    }

    fun updateAnswer(id: Int, answer: Boolean) {
        checklistItems = checklistItems.map { item ->
            if (item.id == id) item.copy(answer = answer) else item
        }
    }

    fun resetAnswers() {
        checklistItems = checklistItems.map { it.copy(answer = null) }
    }
} 