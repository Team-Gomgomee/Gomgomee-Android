package com.konkuk.gomgomee.presentation.findcare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InstitutionCard(
    inst: Institution,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.95f))
            .fillMaxWidth(1f)
    ) {
        // 병원명 + 분류
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = inst.name,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = inst.category,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1f)) // ⭐ 나머지 요소 밀어냄

            IconButton(
                onClick = { /* 즐겨찾기 토글 로직 여기에 */ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Star, // or Icons.Outlined.Star
                    contentDescription = "즐겨찾기",
                    tint = Color.Red // or Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 거리 + 주소
        Text(
            text = inst.address,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}