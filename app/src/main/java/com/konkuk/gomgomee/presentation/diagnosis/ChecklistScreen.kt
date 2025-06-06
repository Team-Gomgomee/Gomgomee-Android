package com.konkuk.gomgomee.presentation.diagnosis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.ui.theme.White
import android.app.Application
import androidx.compose.ui.platform.LocalContext

// 체크박스 관련 색상 정의
private val CheckboxCheckedColor = Color(0xFF4CAF50)
private val CheckboxUncheckedColor = Color.Gray

@Composable
fun ChecklistScreen(
    navController: NavController,
    viewModel: ChecklistViewModel = viewModel(factory = ChecklistViewModel.Factory),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 상단 타이틀과 이미지
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "학습장애 진단\n체크리스트",
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

        // 출처 안내
        Text(
            text = "다리꿈 심리상담센터(https://www.drkcenter.net/)에서 제공하는 자료를 바탕으로 구성된 학습장애 진단지입니다.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // 체크리스트 영역
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp) // 항목 간격 축소
        ) {
            // 컬럼 헤더
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.width(110.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "예",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(40.dp)
                    )
                    Text(
                        text = "아니오",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(40.dp)
                    )
                }
            }

            // 체크리스트 항목들
            viewModel.checklistItems.forEach { item ->
                ChecklistItemRow(
                    number = item.id,
                    question = item.question,
                    answer = item.answer,
                    onAnswerSelected = { answer ->
                        viewModel.updateAnswer(item.id, answer)
                    }
                )
            }
        }

        // 결과 보기 버튼
        Button(
            onClick = { navController.navigate("checklist_result") },
            enabled = viewModel.isAllItemsAnswered,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(
                text = "결과 보러가기",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun ChecklistItemRow(
    number: Int,
    question: String,
    answer: Boolean?,
    onAnswerSelected: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // 세로 패딩 축소
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 질문 번호와 내용
        Text(
            text = "$number. $question",
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            lineHeight = 20.sp
        )

        // 체크박스 그룹
        Row(
            modifier = Modifier.width(110.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(40.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomCheckbox(
                    checked = answer == true,
                    onCheckedChange = { onAnswerSelected(true) }
                )
            }
            Box(
                modifier = Modifier.width(40.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomCheckbox(
                    checked = answer == false,
                    onCheckedChange = { onAnswerSelected(false) }
                )
            }
        }
    }
}

@Composable
private fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White)  // 디버깅용 배경색
            .size(25.dp)
            .clip(RoundedCornerShape(2.dp))
            .border(
                width = 1.5.dp,
                color = if (checked) CheckboxCheckedColor else CheckboxUncheckedColor,
                shape = RoundedCornerShape(2.dp)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = if (checked) CheckboxCheckedColor else CheckboxUncheckedColor,
            modifier = Modifier.size(18.dp)
        )
    }
} 