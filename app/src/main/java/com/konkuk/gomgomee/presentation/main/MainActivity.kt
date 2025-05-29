package com.konkuk.gomgomee.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.konkuk.gomgomee.ui.theme.GomgomeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GomgomeeTheme {
                val navController = rememberNavController()
                MainScreen(
                    navController = navController
                )
            }
        }
    }
}