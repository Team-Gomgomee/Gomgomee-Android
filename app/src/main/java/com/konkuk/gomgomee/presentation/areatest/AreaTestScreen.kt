package com.konkuk.gomgomee.presentation.areatest

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.presentation.areatest.model.AreaType
import com.konkuk.gomgomee.presentation.areatest.model.Choice
import com.konkuk.gomgomee.presentation.areatest.model.MediaType
import com.konkuk.gomgomee.presentation.navigation.Route
import com.konkuk.gomgomee.ui.theme.*
import com.konkuk.gomgomee.util.context.toast
import com.konkuk.gomgomee.util.modifier.noRippleClickable
import kotlinx.coroutines.launch

@Composable
fun AreaTestScreen(
    navController: NavController,
    areaType: AreaType,
    userNo: Int,
    viewModel: AreaTestViewModel = viewModel(
        factory = AreaTestViewModel.Factory
    )
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // 문제 로딩
    LaunchedEffect(areaType) {
        try {
            viewModel.loadQuestionsByArea(areaType, userNo)
        } catch (e: Exception) {
            context.toast("문제를 불러오는데 실패했습니다.")
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 상단 헤더
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when(areaType) {
                    AreaType.READING -> "읽기 영역\n테스트"
                    AreaType.WRITING -> "쓰기 영역\n테스트"
                    AreaType.ARITHMETIC -> "산수 영역\n테스트"
                    AreaType.LISTENING -> "듣기 영역\n테스트"
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
            Image(
                painter = painterResource(id = R.drawable.ic_bear_diagnosis),
                contentDescription = "곰곰이 캐릭터",
                modifier = Modifier.size(100.dp)
            )
        }

        if (viewModel.questions.isNotEmpty()) {
            val currentQuestion = viewModel.questions[currentQuestionIndex]

            // 진행 상황
            Text(
                text = "문제 ${currentQuestionIndex + 1} / ${viewModel.questions.size}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            // 문제 내용
            Text(
                text = currentQuestion.questionText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            // 미디어 표시 (이미지/오디오)
            currentQuestion.media?.forEach { media ->
                when (media.type) {
                    MediaType.IMAGE -> {
                        // 이미지 표시 로직
                        val resourceId = remember(media.source) {
                            context.resources.getIdentifier(
                                media.source,
                                "drawable",
                                context.packageName
                            )
                        }
                        
                        if (resourceId != 0) {
                            Image(
                                painter = painterResource(id = resourceId),
                                contentDescription = "문제 이미지",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        } else {
                            Text(
                                text = "이미지를 불러올 수 없습니다",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                color = Color.Gray
                            )
                        }
                    }
                    MediaType.AUDIO -> {
                        val context = LocalContext.current
                        val mediaPlayer = remember { MediaPlayer() }
                        val audioResId = remember(media.source) {
                            context.resources.getIdentifier(
                                media.source,
                                "raw",
                                context.packageName
                            )
                        }
                        var isPlaying by remember { mutableStateOf(false) }
                        // 문제 인덱스가 바뀔 때마다 MediaPlayer 정지 및 상태 초기화
                        LaunchedEffect(currentQuestionIndex) {
                            if (mediaPlayer.isPlaying) {
                                mediaPlayer.stop()
                                mediaPlayer.reset()
                            }
                            isPlaying = false
                        }
                        DisposableEffect(Unit) {
                            onDispose {
                                mediaPlayer.release()
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Green400)
                                .noRippleClickable {
                                    if (audioResId != 0) {
                                        if (mediaPlayer.isPlaying) {
                                            mediaPlayer.stop()
                                            mediaPlayer.reset()
                                            isPlaying = false
                                        }
                                        val afd = context.resources.openRawResourceFd(audioResId)
                                        mediaPlayer.reset()
                                        mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                                        afd.close()
                                        mediaPlayer.prepare()
                                        mediaPlayer.start()
                                        isPlaying = true
                                        mediaPlayer.setOnCompletionListener {
                                            isPlaying = false
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isPlaying) "재생 중..." else "음성 듣기",
                                color = White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 14.sp
                            )
                        }
                    }
                    else -> {}
                }
            }

            // 보기 그리드
            ChoiceGrid(
                choices = currentQuestion.choices,
                selectedChoice = viewModel.getSelectedAnswer(currentQuestion.id),
                onChoiceSelected = { 
                    viewModel.setAnswer(currentQuestion.id, it)
                }
            )

            // 네비게이션 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 이전 버튼
                Box(
                    modifier = Modifier
                        .then(
                            if (currentQuestionIndex > 0) {
                                Modifier.noRippleClickable {
                                    currentQuestionIndex--
                                }
                            } else {
                                Modifier
                            }
                        )
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "이전 문제",
                        tint = if (currentQuestionIndex > 0) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }

                // 완료 버튼 (마지막 문제이고 모든 문제를 풀었을 때만 표시)
                AnimatedVisibility(
                    visible = currentQuestionIndex == viewModel.questions.size - 1 && 
                             viewModel.isAllQuestionsAnswered(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Green400)
                            .noRippleClickable {
                                scope.launch {
                                    try {
                                        viewModel.finishTest()
                                        navController.navigate(
                                            "area_test_result/${viewModel.questions.size}/${viewModel.currentSession?.correctCount ?: 0}"
                                        ) {
                                            popUpTo(Route.AreaTest.route) {
                                                inclusive = true
                                            }
                                        }
                                    } catch (e: Exception) {
                                        context.toast("결과를 저장하는데 실패했습니다.")
                                    }
                                }
                            }
                            .padding(vertical = 12.dp, horizontal = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "테스트 완료",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }

                // 다음 버튼
                Box(
                    modifier = Modifier
                        .then(
                            if (currentQuestionIndex < viewModel.questions.size - 1) {
                                Modifier.noRippleClickable {
                                    currentQuestionIndex++
                                }
                            } else {
                                Modifier
                            }
                        )
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "다음 문제",
                        tint = if (currentQuestionIndex < viewModel.questions.size - 1)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun ChoiceGrid(
    choices: List<Choice>,
    selectedChoice: Int?,
    onChoiceSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        choices.forEachIndexed { index, choice ->
            val isSelected = selectedChoice == index
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Green500 else Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = if (isSelected) Green500.copy(alpha = 0.1f)
                        else Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .noRippleClickable { onChoiceSelected(index) }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when(index) {
                        0 -> "① "
                        1 -> "② "
                        2 -> "③ "
                        3 -> "④ "
                        else -> "${index + 1}. "
                    } + choice.text,
                    fontSize = 16.sp,
                    color = if (isSelected) Green500 else Color.DarkGray
                )
            }
        }
    }
} 