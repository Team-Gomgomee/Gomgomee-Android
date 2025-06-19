package com.konkuk.gomgomee.presentation.findcare

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .padding(start = 20.dp, end = 20.dp, top = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "곰곰이랑\n병원 위치 찾아보기 !",
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 18.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_bear_findcare),
            contentDescription = "곰곰이 캐릭터",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Preview
@Composable
private fun PrevUpperScreen() {
    UpperScreen()
}