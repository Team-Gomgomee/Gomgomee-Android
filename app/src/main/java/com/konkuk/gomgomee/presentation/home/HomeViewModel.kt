package com.konkuk.gomgomee.presentation.home

import androidx.lifecycle.ViewModel
import com.konkuk.gomgomee.data.HomeCardData

class HomeViewModel : ViewModel() {
    val homeCardItems = listOf(
        HomeCardData("학습장애란?", "학습장애란 읽기, 쓰기, 추론, 산수계산 등의 능력과 획득 및 사용상의 심각한 곤란을 주 증상으로 하는, 다양한 원인을 배경으로 하는 이질적인 장애군을 총칭하는 용어이다."),
        HomeCardData("ADHD란?", "주의력 결핍/과잉행동 장애(Attention Deficit/Hyperactivity Disorder)는 아동기에 많이 나타나는 장애로, 지속적으로 주의력이 부족하여 산만하고 과다활동을 보이는 장애를 말한다."),
        HomeCardData("의사소통장애란?", "대뇌의 언어 중추 발달이 늦어서 말이 늦되는 것으로 언어장애(language disorder), 말소리장애(speech sound disorder), 아동기 발병 유창성장애 (말더듬, Child-onset fluency disorder, stuttering), 사회적(실용적)의사소통 장애(social(pragmatic) communication disorder) 네 가지로 분류할 수 있다."),
        HomeCardData("난독증이란?", "난독증(dyslexia)은 글을 정확하고 유창하게 읽지 못하고 철자를 정확하게 쓰기 힘들어 하는 것을 특징으로 하는 학습 장애의 한 유형으로 읽기장애라고도 한다.")
    )
}