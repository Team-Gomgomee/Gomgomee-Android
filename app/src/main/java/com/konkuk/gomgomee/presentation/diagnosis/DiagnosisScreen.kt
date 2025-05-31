package com.konkuk.gomgomee.presentation.findcare

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.ui.theme.White

// 연두색 계열의 색상 정의
private val LightGreen = Color(0xFFB7E4A7)
private val DarkGreen = Color(0xFF86B875)

@Composable
fun DiagnosisScreen(
    modifier: Modifier = Modifier,
    onSelfDiagnosisClick: () -> Unit = {},
    onReadingAreaClick: () -> Unit = {},
    onWritingAreaClick: () -> Unit = {},
    onListeningAreaClick: () -> Unit = {},
    onMathAreaClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // 상단 텍스트와 캐릭터 이미지
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "곰곰이랑 직접\n진단하러 가볼까요?",
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

        // 자가 진단 테스트 섹션
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "학습장애 진단 체크리스트",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = onSelfDiagnosisClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, DarkGreen, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGreen,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "체크리스트로 자가 진단하기",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        // 영역별 테스트 섹션
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "영역별 학습 기능 테스트",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            // 2x2 그리드 버튼
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AreaButton(
                        title = "읽기 영역",
                        subtitle = "시지각 + 이해",
                        onClick = onReadingAreaClick,
                        modifier = Modifier.weight(1f)
                    )
                    AreaButton(
                        title = "쓰기 영역",
                        subtitle = "운동 협응 + 철자 기억",
                        onClick = onWritingAreaClick,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AreaButton(
                        title = "듣기 영역",
                        subtitle = "청지각 + 언어 처리",
                        onClick = onListeningAreaClick,
                        modifier = Modifier.weight(1f)
                    )
                    AreaButton(
                        title = "산수 영역",
                        subtitle = "수리력 + 수 개념 이해",
                        onClick = onMathAreaClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AreaButton(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f)  // 정사각형 비율 설정
            .border(2.dp, DarkGreen, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LightGreen,
            contentColor = Color.Black
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "($subtitle)",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}