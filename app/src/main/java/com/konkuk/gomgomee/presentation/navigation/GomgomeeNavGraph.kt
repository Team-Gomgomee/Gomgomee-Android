package com.konkuk.gomgomee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.konkuk.gomgomee.presentation.diagnosis.DiagnosisScreen
import com.konkuk.gomgomee.presentation.findcare.FindCareScreen
import com.konkuk.gomgomee.presentation.home.HomeDetailScreen
import com.konkuk.gomgomee.presentation.home.HomeScreen
import com.konkuk.gomgomee.presentation.mypage.MyPageScreen
import com.konkuk.gomgomee.presentation.onboarding.LoginScreen
import com.konkuk.gomgomee.presentation.onboarding.SignUpScreen
import com.konkuk.gomgomee.presentation.onboarding.SplashScreen

@Composable
fun GomgomeeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(route = Route.Splash.route) {
            SplashScreen(
                onNavigateToSignUp = {
                    navController.navigate(Route.SignUp.route)
                }
            )
        }

        composable(route = Route.SignUp.route) {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.navigate(Route.Login.route)
                }
            )
        }

        composable(route = Route.Login.route) {
            LoginScreen(
                onNavigateToMain = {
                    navController.navigate("home") {
                        popUpTo(Route.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Route.Home.route) {
            HomeScreen(
                onNavigateToHomeDetail = {
                    navController.navigate(Route.HomeDetail.route)
                }
            )
        }

        composable(route = Route.Diagnosis.route) {
            DiagnosisScreen()
        }

        composable(route = Route.FindCare.route) {
            FindCareScreen()
        }

        composable(route = Route.MyPage.route) {
            MyPageScreen()
        }

        composable(route = Route.HomeDetail.route) {
            HomeDetailScreen()
        }
    }
}