package com.konkuk.gomgomee.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konkuk.gomgomee.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF6FAB8E))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_bear_splash),
            contentDescription = "앱 로고",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
    }
}