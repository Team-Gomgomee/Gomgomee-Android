package com.konkuk.gomgomee.presentation.areatest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavController
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.presentation.navigation.Route
import com.konkuk.gomgomee.ui.theme.Green400
import com.konkuk.gomgomee.ui.theme.White
import com.konkuk.gomgomee.util.modifier.noRippleClickable

@Composable
fun AreaTestResultScreen(
    navController: NavController,
    viewModel: AreaTestResultViewModel
) {
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

        viewModel.resultState?.let { resultState ->
            // 결과 카드
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when (resultState.resultLevel) {
                        ResultLevel.WARNING -> Color(0xFFFFEBEE) // Light Red
                        ResultLevel.CAUTION -> Color(0xFFFFF3E0) // Light Orange
                        ResultLevel.SAFE -> Color(0xFFE8F5E9) // Light Green
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
                    // 정답률 표시
                    Text(
                        text = "${resultState.percentage.toInt()}%",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (resultState.resultLevel) {
                            ResultLevel.WARNING -> Color(0xFFB71C1C) // Dark Red
                            ResultLevel.CAUTION -> Color(0xFFE65100) // Dark Orange
                            ResultLevel.SAFE -> Color(0xFF1B5E20) // Dark Green
                        }
                    )

                    // 맞은 문제 수
                    Text(
                        text = "${resultState.correctAnswers}문제 / ${resultState.totalQuestions}문제",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )

                    // 결과 메시지
                    Text(
                        text = resultState.message,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp,
                        color = Color.DarkGray
                    )
                }
            }

            // 설명 텍스트
//            Text(
//                text = when (resultState.resultLevel) {
//                    ResultLevel.WARNING -> "정답률이 매우 낮습니다.\n전문의와 상담하여 정확한 진단을 받아보세요."
//                    ResultLevel.CAUTION -> "일부 영역에서 어려움이 있을 수 있습니다.\n전문가와 상담하는 것이 좋습니다."
//                    ResultLevel.SAFE -> "대부분의 문제를 잘 해결했습니다.\n지속적인 관찰과 연습을 통해 더 발전할 수 있습니다."
//                },
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Normal,
//                textAlign = TextAlign.Center,
//                lineHeight = 24.sp,
//                color = Color.DarkGray
//            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 하단 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Green400)
                .noRippleClickable {
                    navController.navigate(Route.Diagnosis.route) {
                        popUpTo(Route.AreaTest.route) {
                            inclusive = true
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "확인 완료",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 16.sp
            )
        }
    }
} 