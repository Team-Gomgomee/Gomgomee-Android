package com.konkuk.gomgomee.presentation.diagnosis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class ChecklistItem(
    val id: Int,
    val question: String,
    var answer: Boolean? = null
)

class ChecklistViewModel : ViewModel() {
    var checklistItems by mutableStateOf(listOf(
        ChecklistItem(1, "아이가 글자를 자주 빠뜨리거나 순서를 바꿔서 읽나요?"),
        ChecklistItem(2, "아이가 책이나 글을 읽을 때 집중하지 못하고 산만한가요?"),
        ChecklistItem(3, "아이가 읽기를 지나치게 싫어하거나 회피하나요?"),
        ChecklistItem(4, "아이가 자신의 생각이나 의견을 글로 표현하는 것을 어려워하나요?"),
        ChecklistItem(5, "아이의 글씨가 알아보기 힘들 정도로 흘려 쓰거나 삐뚤삐뚤한가요?"),
        ChecklistItem(6, "아이가 맞춤법이나 문법에 맞지 않게 글을 쓰나요?"),
        ChecklistItem(7, "아이가 숫자를 거꾸로 쓰거나 순서를 바꿔서 쓰나요?"),
        ChecklistItem(8, "아이가 간단한 수학 문제도 푸는 데 오랜 시간이 걸리나요?"),
        ChecklistItem(9, "아이가 수학 문제를 풀 때 연산 부호(+, -, ×, ÷)를 혼동하나요?"),
        ChecklistItem(10, "아이가 선생님의 설명이나 지시사항을 잘 이해하지 못하나요?"),
        ChecklistItem(11, "아이가 주의집중 시간이 또래에 비해 현저히 짧은가요?"),
        ChecklistItem(12, "아이가 수업 중에 자주 딴짓을 하거나 멍을 자주 때리나요?"),
        ChecklistItem(13, "아이가 숙제나 준비물을 자주 잊어버리나요?"),
        ChecklistItem(14, "아이가 또래와의 관계에서 어려움을 겪나요?"),
        ChecklistItem(15, "아이가 자존감이 낮고 자신감이 부족해 보이나요?")
    ))
        private set

    val isAllItemsAnswered: Boolean
        get() = checklistItems.all { it.answer != null }

    fun updateAnswer(id: Int, answer: Boolean) {
        checklistItems = checklistItems.map { item ->
            if (item.id == id) item.copy(answer = answer) else item
        }
    }

    fun resetAnswers() {
        checklistItems = checklistItems.map { it.copy(answer = null) }
    }
} 