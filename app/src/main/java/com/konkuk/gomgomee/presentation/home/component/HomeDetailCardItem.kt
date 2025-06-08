package com.konkuk.gomgomee.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.data.HomeDetailCardData
import com.konkuk.gomgomee.ui.theme.Green300
import com.konkuk.gomgomee.ui.theme.White

@Composable
fun HomeDetailCardItem(
    cardDetail: HomeDetailCardData,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(16.dp),
                color = White
            )
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(16.dp),
                color = Green300
            )
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = cardDetail.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = cardDetail.content,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 18.sp
            )
        }
    }
}