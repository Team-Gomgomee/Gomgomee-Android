package com.konkuk.gomgomee.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.konkuk.gomgomee.ui.theme.White

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToHomeDetail: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "홈 메인 화면"
        )
        Button(
            onClick = {
                onNavigateToHomeDetail()
            }
        ) {
            Text(
                text = "홈 디테일로 이동"
            )
        }
    }
}