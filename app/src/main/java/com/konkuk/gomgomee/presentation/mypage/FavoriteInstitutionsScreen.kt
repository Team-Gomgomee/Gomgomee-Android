package com.konkuk.gomgomee.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.konkuk.gomgomee.ui.theme.White

@Composable
fun FavoriteInstitutionsScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(24.dp)
    ) {
        Text(
            text = "즐겨찾기한 기관",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        // TODO: 즐겨찾기한 기관 목록 구현
    }
} 