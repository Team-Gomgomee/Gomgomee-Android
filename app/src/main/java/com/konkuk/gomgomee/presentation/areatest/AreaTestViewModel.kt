package com.konkuk.gomgomee.presentation.areatest

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.konkuk.gomgomee.presentation.areatest.model.AreaType
import com.konkuk.gomgomee.presentation.areatest.model.Choice
import com.konkuk.gomgomee.presentation.areatest.model.Question

class AreaTestViewModel : ViewModel() {
    // 사용자의 답변을 저장하는 맵 (문제 ID -> 선택한 답안 인덱스)
    private val userAnswers = mutableStateMapOf<Int, Int>()

    // 임시 더미 데이터 생성
    fun getQuestionsByArea(areaType: AreaType): List<Question> {
        val areaName = when(areaType) {
            AreaType.READING -> "읽기"
            AreaType.WRITING -> "쓰기"
            AreaType.ARITHMETIC -> "연산"
            AreaType.ATTENTION -> "주의력"
        }
        
        return (1..10).map { index ->
            Question(
                id = index,
                areaType = areaType,
                questionText = "이것은 ${areaName} 영역의 ${index}번 문제입니다. 가장 적절한 답을 고르세요.",
                media = null,
                choices = List(4) { choiceIndex ->
                    Choice(text = "${areaName} 문제 ${index}의 ${choiceIndex + 1}번 보기")
                },
                correctAnswer = 0
            )
        }
    }

    // 사용자가 선택한 답안 가져오기
    fun getSelectedAnswer(questionId: Int): Int? {
        return userAnswers[questionId]
    }

    // 사용자 답안 설정
    fun setAnswer(questionId: Int, choiceIndex: Int) {
        userAnswers[questionId] = choiceIndex
    }

    // 모든 문제에 답했는지 확인
    fun isAllQuestionsAnswered(totalQuestions: Int): Boolean {
        return userAnswers.size == totalQuestions
    }

    // 답안 초기화
    fun clearAnswers() {
        userAnswers.clear()
    }
} 