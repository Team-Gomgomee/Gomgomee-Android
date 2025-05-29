package com.konkuk.gomgomee.presentation.onboarding

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
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "회원가입 화면"
        )
        Button(
            onClick = {
                onNavigateToLogin()
            }
        ) {
            Text(
                text = "로그인으로 이동"
            )
        }
    }
}