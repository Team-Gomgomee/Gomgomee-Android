package com.konkuk.gomgomee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.konkuk.gomgomee.presentation.main.MainScreen
import com.konkuk.gomgomee.ui.theme.GomgomeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GomgomeeTheme {
                MainScreen()
            }
        }
    }
}