package com.konkuk.gomgomee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.konkuk.gomgomee.presentation.diagnosis.DiagnosisScreen
import com.konkuk.gomgomee.presentation.findcare.FindCareScreen
import com.konkuk.gomgomee.presentation.home.HomeScreen
import com.konkuk.gomgomee.presentation.mypage.MyPageScreen

@Composable
fun GomgomeeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(route = Route.Home.route) {
            HomeScreen(modifier = modifier)
        }

        composable(route = Route.Diagnosis.route) {
            DiagnosisScreen(modifier = modifier)
        }

        composable(route = Route.FindCare.route) {
            FindCareScreen(modifier = modifier)
        }

        composable(route = Route.MyPage.route) {
            MyPageScreen(modifier = modifier)
        }
    }
}