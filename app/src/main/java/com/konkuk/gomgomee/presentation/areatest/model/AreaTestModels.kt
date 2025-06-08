package com.konkuk.gomgomee.presentation.areatest.model

enum class AreaType {
    READING,    // 읽기 영역
    WRITING,    // 쓰기 영역
    LISTENING,  // 듣기 영역
    ARITHMETIC  // 산수 영역
}

enum class MediaType {
    IMAGE,
    VIDEO,
    AUDIO
}

data class QuestionMedia(
    val type: MediaType,
    val source: String
)

data class Choice(
    val text: String,
    val image: String? = null
)

data class Question(
    val id: Int,
    val areaType: AreaType,
    val questionText: String,
    val media: List<QuestionMedia>? = null,
    val choices: List<Choice>,
    val correctAnswer: Int
) 