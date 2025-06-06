package com.konkuk.gomgomee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_question")
data class TestQuestionEntity(
    @PrimaryKey(autoGenerate = true) 
    val questionId: Int = 0,
    val domain: String, // 읽기, 쓰기, 듣기, 산수 중 하나
    val questionText: String?, // 기본 문장형 질문
    val imagePath: String?, // 이미지 리소스 경로 또는 URI
    val audioPath: String?, // 오디오 리소스 경로 또는 URI
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String, // "A", "B", "C", "D"
    val createdAt: Long = System.currentTimeMillis()
) 