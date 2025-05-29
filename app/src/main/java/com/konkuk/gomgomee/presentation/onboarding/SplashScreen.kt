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
import com.konkuk.gomgomee.ui.theme.Green400

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignUp: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Green400),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "스플래시 화면"
        )
        Button(
            onClick = {
                onNavigateToSignUp()
            }
        ) {
            Text(
                text = "회원가입으로 이동"
            )
        }
    }
}