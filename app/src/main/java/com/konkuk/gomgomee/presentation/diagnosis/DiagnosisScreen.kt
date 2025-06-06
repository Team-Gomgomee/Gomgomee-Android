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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.presentation.areatest.model.AreaType
import com.konkuk.gomgomee.ui.theme.White

// 연두색 계열의 색상 정의
private val LightGreen = Color(0xFFB7E4A7)
private val DarkGreen = Color(0xFF86B875)

@Composable
fun DiagnosisScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    userNo: Int = 1  // 임시로 1을 기본값으로 설정
) {
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
                text = "학습장애 진단\n테스트",
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

        // 자가진단 체크리스트 버튼
        Button(
            onClick = { navController.navigate("checklist") },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE5F1E7),
                contentColor = Color.Black
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = SolidColor(Color(0xFF6FAB8E))
            )
        ) {
            Text(
                text = "자가진단 체크리스트",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // 영역별 테스트 버튼들
        Text(
            text = "영역별 학습 기능 테스트",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // 2x2 그리드로 영역별 버튼 배치
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AreaButton(
                    text = "읽기 영역",
                    onClick = { navController.navigate("area_test/${AreaType.READING}/$userNo") }
                )
                AreaButton(
                    text = "쓰기 영역",
                    onClick = { navController.navigate("area_test/${AreaType.WRITING}/$userNo") }
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AreaButton(
                    text = "듣기 영역",
                    onClick = { navController.navigate("area_test/${AreaType.LISTENING}/$userNo") }
                )
                AreaButton(
                    text = "산수 영역",
                    onClick = { navController.navigate("area_test/${AreaType.ARITHMETIC}/$userNo") }
                )
            }
        }
    }
}

@Composable
private fun AreaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE5F1E7),
            contentColor = Color.Black
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(Color(0xFF6FAB8E))
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = when (text) {
                    "읽기 영역" -> "(시지각 + 이해)"
                    "쓰기 영역" -> "(운동 협응 + 철자 기억)"
                    "듣기 영역" -> "(청지각 + 언어 처리)"
                    "산수 영역" -> "(수리력 + 수 개념 이해)"
                    else -> ""
                },
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}