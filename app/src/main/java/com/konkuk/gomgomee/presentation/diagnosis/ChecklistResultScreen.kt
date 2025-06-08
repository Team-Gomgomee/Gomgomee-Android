package com.konkuk.gomgomee.presentation.diagnosis

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.ui.theme.White
import com.konkuk.gomgomee.presentation.navigation.Route

@Composable
fun ChecklistResultScreen(
    navController: NavController,
    viewModel: ChecklistResultViewModel,
    modifier: Modifier = Modifier
) {
    val resultState = viewModel.resultState

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
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
                text = "진단 결과",
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

        // 결과 표시
        if (resultState != null) {
            // 결과 카드
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when (resultState.resultLevel) {
                        ResultLevel.SAFE -> Color(0xFFE8F5E9)     // 연한 초록
                        ResultLevel.CAUTION -> Color(0xFFFFF3E0)   // 연한 주황
                        ResultLevel.WARNING -> Color(0xFFFFEBEE)   // 연한 빨강
                    }
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 응답 비율
                    Text(
                        text = "${resultState.yesAnswers}/${resultState.totalQuestions}",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (resultState.resultLevel) {
                            ResultLevel.SAFE -> Color(0xFF2E7D32)      // 진한 초록
                            ResultLevel.CAUTION -> Color(0xFFE65100)   // 진한 주황
                            ResultLevel.WARNING -> Color(0xFFC62828)   // 진한 빨강
                        }
                    )
                    
                    // 퍼센트
                    Text(
                        text = "%.1f%%".format(resultState.percentage),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Gray.copy(alpha = 0.2f)
                    )

                    // 결과 메시지
                    Text(
                        text = resultState.message,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 하단 버튼
        Button(
            onClick = { 
                // DiagnosisScreen으로 돌아가기
                navController.navigate(Route.Diagnosis.route) {
                    // 체크리스트와 결과 화면을 백스택에서 제거
                    popUpTo(Route.Diagnosis.route) {
                        inclusive = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "확인 완료",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
} 