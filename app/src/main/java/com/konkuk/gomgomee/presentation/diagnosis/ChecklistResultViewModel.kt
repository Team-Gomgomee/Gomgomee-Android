package com.konkuk.gomgomee.presentation.diagnosis

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.konkuk.gomgomee.data.local.database.AppDatabase
import com.konkuk.gomgomee.data.local.entity.ChecklistResultEntity
import com.konkuk.gomgomee.data.repository.ChecklistResultRepository
import com.konkuk.gomgomee.util.context.toast
import kotlinx.coroutines.launch

data class ChecklistResultState(
    val totalQuestions: Int,
    val yesAnswers: Int,
    val percentage: Float,
    val resultLevel: ResultLevel,
    val message: String
)

enum class ResultLevel {
    SAFE,      // 안전
    CAUTION,   // 주의
    WARNING    // 경고
}

class ChecklistResultViewModel(
    application: Application,
    private val checklistItems: List<ChecklistItem>
) : AndroidViewModel(application) {

    private val repository: ChecklistResultRepository
    private val context = application
    var resultState by mutableStateOf<ChecklistResultState?>(null)
        private set

    init {
        val dao = AppDatabase.getDatabase(application).checklistResultDao()
        repository = ChecklistResultRepository(dao)
        calculateResult()
    }

    private fun calculateResult() {
        val totalQuestions = checklistItems.size
        val yesAnswers = checklistItems.count { it.answer == true }
        val percentage = (yesAnswers.toFloat() / totalQuestions) * 100

        val (resultLevel, message) = when {
            percentage < 30 -> ResultLevel.SAFE to "현재는 특별한 이상이 없어 보입니다.\n정기적인 관찰만으로도 충분합니다."
            percentage < 70 -> ResultLevel.CAUTION to "전문가와의 상담이 권장됩니다.\n조기 발견이 중요할 수 있습니다."
            else -> ResultLevel.WARNING to "전문의의 정밀 진단이 필요합니다.\n가까운 병원에서 진단을 받아보세요."
        }

        resultState = ChecklistResultState(
            totalQuestions = totalQuestions,
            yesAnswers = yesAnswers,
            percentage = percentage,
            resultLevel = resultLevel,
            message = message
        )

        // 결과를 데이터베이스에 저장
        viewModelScope.launch {
            try {
                val userNo = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    .getInt("current_user_no", -1)
                
                if (userNo == -1) {
                    throw Exception("로그인이 필요합니다")
                }

                val result = ChecklistResultEntity(
                    userNo = userNo,
                    yesCount = yesAnswers,
                    createdAt = System.currentTimeMillis()
                )
                repository.insert(result)
            } catch (e: Exception) {
                Log.e(TAG, "Error saving checklist result", e)
                Toast.makeText(context, "결과 저장에 실패했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG = "ChecklistResultViewModel"
        
        fun factory(checklistItems: List<ChecklistItem>): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                ChecklistResultViewModel(application, checklistItems)
            }
        }
    }
} 