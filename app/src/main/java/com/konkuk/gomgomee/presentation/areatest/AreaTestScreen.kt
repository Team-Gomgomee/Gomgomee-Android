package com.konkuk.gomgomee.presentation.areatest

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
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
import com.konkuk.gomgomee.presentation.areatest.model.Question
import com.konkuk.gomgomee.ui.theme.White

@Composable
fun AreaTestScreen(
    navController: NavController,
    areaType: AreaType,
    viewModel: AreaTestViewModel = viewModel()
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    val questions = remember(areaType) { viewModel.getQuestionsByArea(areaType) }

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
                    AreaType.ARITHMETIC -> "연산 영역\n테스트"
                    AreaType.ATTENTION -> "주의력 영역\n테스트"
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

        // 진행 상황
        Text(
            text = "문제 ${currentQuestionIndex + 1} / ${questions.size}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        // 문제 내용
        Text(
            text = questions[currentQuestionIndex].questionText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        // 보기 그리드 (2x2)
        ChoiceGrid(
            choices = questions[currentQuestionIndex].choices,
            selectedChoice = viewModel.getSelectedAnswer(questions[currentQuestionIndex].id),
            onChoiceSelected = { 
                viewModel.setAnswer(questions[currentQuestionIndex].id, it)
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
            IconButton(
                onClick = { if (currentQuestionIndex > 0) currentQuestionIndex-- },
                enabled = currentQuestionIndex > 0
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "이전 문제",
                    tint = if (currentQuestionIndex > 0) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }

            // 완료 버튼 (마지막 문제이고 모든 문제를 풀었을 때만 표시)
            AnimatedVisibility(
                visible = currentQuestionIndex == questions.size - 1 && 
                         viewModel.isAllQuestionsAnswered(questions.size),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    onClick = { navController.navigate("area_test_result") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("테스트 완료", fontSize = 16.sp)
                }
            }

            // 다음 버튼
            IconButton(
                onClick = { if (currentQuestionIndex < questions.size - 1) currentQuestionIndex++ },
                enabled = currentQuestionIndex < questions.size - 1
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "다음 문제",
                    tint = if (currentQuestionIndex < questions.size - 1) 
                          MaterialTheme.colorScheme.primary else Color.Gray
                )
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 2x2 그리드로 보기 배치
        for (row in 0..1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (col in 0..1) {
                    val index = row * 2 + col
                    if (index < choices.size) {
                        ChoiceItem(
                            choice = choices[index],
                            isSelected = selectedChoice == index,
                            onClick = { onChoiceSelected(index) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChoiceItem(
    choice: Choice,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = choice.text,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )
    }
} 