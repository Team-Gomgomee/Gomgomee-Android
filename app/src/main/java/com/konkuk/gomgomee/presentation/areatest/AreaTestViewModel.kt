package com.konkuk.gomgomee.presentation.areatest

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
import com.konkuk.gomgomee.data.local.entity.TestQuestionEntity
import com.konkuk.gomgomee.data.local.entity.TestSessionEntity
import com.konkuk.gomgomee.data.repository.TestQuestionRepository
import com.konkuk.gomgomee.data.repository.TestSessionRepository
import com.konkuk.gomgomee.presentation.areatest.model.AreaType
import com.konkuk.gomgomee.presentation.areatest.model.Choice
import com.konkuk.gomgomee.presentation.areatest.model.Question
import com.konkuk.gomgomee.presentation.areatest.model.QuestionMedia
import com.konkuk.gomgomee.presentation.areatest.model.MediaType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AreaTestViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "AreaTestViewModel"
        
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                AreaTestViewModel(application)
            }
        }
    }

    private val questionRepository: TestQuestionRepository
    private val sessionRepository: TestSessionRepository
    var currentSession: TestSessionEntity? = null
        private set
    
    // 사용자의 답변을 저장하는 맵 (문제 ID -> 선택한 답안)
    private val userAnswers = mutableStateOf<Map<Int, String>>(emptyMap())
    
    // 현재 테스트의 문제 목록
    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    init {
        val database = AppDatabase.getDatabase(application)
        questionRepository = TestQuestionRepository(database.testQuestionDao())
        sessionRepository = TestSessionRepository(database.testSessionDao())
    }

    suspend fun loadQuestionsByArea(areaType: AreaType, userNo: Int) {
        val domain = when(areaType) {
            AreaType.READING -> "읽기"
            AreaType.WRITING -> "쓰기"
            AreaType.ARITHMETIC -> "산수"
            AreaType.LISTENING -> "듣기"
        }

        try {
            // 해당 영역의 모든 문제 가져오기
            var dbQuestions = questionRepository.getQuestionsByDomain(domain).first()
            
            if (dbQuestions.isEmpty()) {
                Log.w(TAG, "No questions found for domain: $domain")
                val reader = com.konkuk.gomgomee.data.util.JsonDataReader(getApplication())
                val allJsonQuestions = reader.readTestQuestions()

                val filteredQuestions = allJsonQuestions.filter { it.domain == domain }

                if (filteredQuestions.isEmpty()) {
                    throw Exception("JSON에도 $domain 영역의 문제가 없습니다.")
                }

                questionRepository.insertAll(filteredQuestions)
                Log.d(TAG, "Inserted ${filteredQuestions.size} questions for domain $domain from JSON.")

                dbQuestions = questionRepository.getQuestionsByDomain(domain).first()
            }
            
            // 세션 생성 및 저장
            val newSession = TestSessionEntity(
                userNo = userNo,
                domain = domain,
                startedAt = System.currentTimeMillis(),
                finishedAt = null,
                totalCount = dbQuestions.size,
                correctCount = 0
            )
            
            // 데이터베이스에 세션 저장
            try {
                val sessionId = sessionRepository.insert(newSession)
                currentSession = newSession.copy(sessionId = sessionId.toInt())
            } catch (e: Exception) {
                Log.e(TAG, "Error saving initial session", e)
                throw e
            }
            
            // UI 모델로 변환
            questions = dbQuestions.map { entity ->
                Question(
                    id = entity.questionId,
                    areaType = areaType,
                    questionText = entity.questionText ?: "",
                    media = buildMediaList(entity),
                    choices = listOf(
                        Choice(text = entity.optionA),
                        Choice(text = entity.optionB),
                        Choice(text = entity.optionC),
                        Choice(text = entity.optionD)
                    ),
                    correctAnswer = when(entity.correctAnswer) {
                        "A" -> 0
                        "B" -> 1
                        "C" -> 2
                        "D" -> 3
                        else -> 0
                    }
                )
            }
            
            // 답변 초기화
            userAnswers.value = emptyMap()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error loading questions", e)
            throw e
        }
    }

    private fun buildMediaList(entity: TestQuestionEntity): List<QuestionMedia>? {
        val mediaList = mutableListOf<QuestionMedia>()
        
        entity.imagePath?.let {
            mediaList.add(QuestionMedia(type = MediaType.IMAGE, source = it))
        }
        
        entity.audioPath?.let {
            mediaList.add(QuestionMedia(type = MediaType.AUDIO, source = it))
        }
        
        return if (mediaList.isEmpty()) null else mediaList
    }

    // 사용자가 선택한 답안 가져오기
    fun getSelectedAnswer(questionId: Int): Int? {
        return userAnswers.value[questionId]?.let { answer ->
            when(answer) {
                "A" -> 0
                "B" -> 1
                "C" -> 2
                "D" -> 3
                else -> null
            }
        }
    }

    // 사용자 답안 설정
    fun setAnswer(questionId: Int, choiceIndex: Int) {
        val answer = when(choiceIndex) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            3 -> "D"
            else -> return
        }
        userAnswers.value = userAnswers.value + (questionId to answer)
    }

    // 모든 문제에 답했는지 확인
    fun isAllQuestionsAnswered(): Boolean {
        return userAnswers.value.size == questions.size
    }

    // 답안 초기화
    fun clearAnswers() {
        userAnswers.value = emptyMap()
    }

    // 테스트 완료 처리
    suspend fun finishTest() {
        val session = currentSession ?: throw Exception("No active test session")
        Log.d(TAG, "Finishing test session: $session")
        
        // 정답 개수 계산
        val correctCount = questions.count { question ->
            val userAnswer = userAnswers.value[question.id]
            val correctAnswer = when(question.correctAnswer) {
                0 -> "A"
                1 -> "B"
                2 -> "C"
                3 -> "D"
                else -> null
            }
            userAnswer == correctAnswer
        }
        
        Log.d(TAG, "Calculated correct answers: $correctCount out of ${questions.size}")
        
        try {
            // 세션 업데이트 (기존 sessionId 유지)
            val finishedSession = session.copy(
                finishedAt = System.currentTimeMillis(),
                correctCount = correctCount
            )
            Log.d(TAG, "Updating test session in database: $finishedSession")
            sessionRepository.update(finishedSession)  // insert 대신 update 사용
            currentSession = finishedSession
            Log.d(TAG, "Successfully updated test session")
        } catch (e: Exception) {
            Log.e(TAG, "Error updating test session", e)
            throw e
        }
    }

    // 현재 세션의 정답률 계산
    fun getCorrectAnswerPercentage(): Float {
        val session = currentSession ?: return 0f
        return (session.correctCount.toFloat() / session.totalCount) * 100
    }
} 