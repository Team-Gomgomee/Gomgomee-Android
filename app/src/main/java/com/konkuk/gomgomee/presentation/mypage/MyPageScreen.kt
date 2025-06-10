package com.konkuk.gomgomee.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.konkuk.gomgomee.presentation.navigation.Route
import com.konkuk.gomgomee.ui.theme.White
import com.konkuk.gomgomee.util.modifier.noRippleClickable

@Composable
fun MyPageScreen(
    navController: NavController,
    onLogout: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(start = 20.dp, end = 20.dp, top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 상단 타이틀
        Text(
            text = "마이페이지",
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 40.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 메뉴 아이템들
        MenuButton(
            text = "테스트 기록 확인하기",
            onClick = { navController.navigate(Route.TestHistory.route) }
        )

        MenuButton(
            text = "내가 즐겨찾기한 병원 모아보기",
            onClick = { navController.navigate(Route.FavoriteInstitutions.route) }
        )

        MenuButton(
            text = "서비스 피드백하기",
            onClick = { navController.navigate(Route.Feedback.route) }
        )

        MenuButton(
            text = "로그아웃",
            onClick = onLogout
        )
    }
}

@Composable
private fun MenuButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE5F1E7))
            .noRippleClickable(onClick = onClick)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "더보기",
                tint = Color.Gray
            )
        }
    }
}