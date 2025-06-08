package com.konkuk.gomgomee.presentation.areatest

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

data class AreaTestResultState(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val percentage: Float,
    val resultLevel: ResultLevel,
    val message: String
)

enum class ResultLevel {
    WARNING,   // 경고 (70% 이상 틀림)
    CAUTION,   // 주의 (30-70% 틀림)
    SAFE       // 안전 (30% 미만 틀림)
}

class AreaTestResultViewModel(
    application: Application,
    private val totalQuestions: Int,
    private val correctAnswers: Int
) : AndroidViewModel(application) {

    companion object {
        fun factory(
            application: Application,
            totalQuestions: Int,
            correctAnswers: Int
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AreaTestResultViewModel(
                    application = application,
                    totalQuestions = totalQuestions,
                    correctAnswers = correctAnswers
                )
            }
        }
    }

    var resultState by mutableStateOf<AreaTestResultState?>(null)
        private set

    init {
        calculateResult()
    }

    private fun calculateResult() {
        val percentage = (correctAnswers.toFloat() / totalQuestions) * 100
        val incorrectPercentage = 100 - percentage

        val (resultLevel, message) = when {
            incorrectPercentage >= 70 -> ResultLevel.WARNING to 
                "전문의의 정밀 진단이 필요합니다.\n가까운 병원에서 진단을 받아보세요."
            incorrectPercentage >= 30 -> ResultLevel.CAUTION to 
                "전문가와의 상담이 권장됩니다.\n조기 발견이 중요할 수 있습니다."
            else -> ResultLevel.SAFE to 
                "현재는 특별한 이상이 없어 보입니다.\n정기적인 관찰만으로도 충분합니다."
        }

        resultState = AreaTestResultState(
            totalQuestions = totalQuestions,
            correctAnswers = correctAnswers,
            percentage = percentage,
            resultLevel = resultLevel,
            message = message
        )
    }
} 