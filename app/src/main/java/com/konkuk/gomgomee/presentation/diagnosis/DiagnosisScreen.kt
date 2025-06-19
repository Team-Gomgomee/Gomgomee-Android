package com.konkuk.gomgomee.presentation.diagnosis

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.presentation.areatest.model.AreaType
import com.konkuk.gomgomee.presentation.navigation.Route
import com.konkuk.gomgomee.ui.theme.Black
import com.konkuk.gomgomee.ui.theme.White
import com.konkuk.gomgomee.util.modifier.noRippleClickable

// 연두색 계열의 색상 정의
private val LightGreen = Color(0xFFB7E4A7)
private val DarkGreen = Color(0xFF86B875)

@Composable
fun DiagnosisScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userNo = remember {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getInt("current_user_no", -1)
    }

    // userNo가 -1이면 로그인되지 않은 상태
    if (userNo == -1) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "로그인이 필요합니다", Toast.LENGTH_SHORT).show()
            navController.navigate(Route.Login.route)
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(start = 20.dp, end = 20.dp, top = 50.dp),
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
                text = "곰곰이랑 직접\n진단하러 가볼까요 ?",
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 18.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_bear_diagnosis),
                contentDescription = "곰곰이 캐릭터",
                modifier = Modifier.size(100.dp)
            )
        }

        // 자가진단 체크리스트 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .noRippleClickable {
                    navController.navigate(Route.Checklist.route)
                }
                .background(
                    color = Color(0xFFE5F1E7),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = ButtonDefaults.outlinedButtonBorder.width,
                    color = Color(0xFF6FAB8E),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "자가진단 체크리스트",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Black
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
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE5F1E7))
            .border(
                width = 1.dp,
                color = Color(0xFF6FAB8E),
                shape = RoundedCornerShape(8.dp)
            )
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
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