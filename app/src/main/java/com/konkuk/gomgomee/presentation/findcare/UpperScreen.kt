package com.konkuk.gomgomee.presentation.findcare

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.R

@Composable
fun UpperScreen(modifier: Modifier = Modifier) {
    //상단 배너
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 22.dp), // 좌우 여백
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "곰곰이랑\n병원 · 심리센터 찾아보기 !",
            fontSize = 23.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.ic_bear_findcare),
            contentDescription = "곰돌이 캐릭터",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Preview
@Composable
private fun PrevUpperScreen() {
    UpperScreen()
}