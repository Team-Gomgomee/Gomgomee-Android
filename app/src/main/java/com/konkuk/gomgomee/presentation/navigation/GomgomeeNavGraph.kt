package com.konkuk.gomgomee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.konkuk.gomgomee.presentation.areatest.AreaTestScreen
import com.konkuk.gomgomee.presentation.areatest.model.AreaType
import com.konkuk.gomgomee.presentation.diagnosis.ChecklistScreen
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
            DiagnosisScreen(
                navController = navController
            )
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

        composable(route = Route.Checklist.route) {
            ChecklistScreen(
                navController = navController
            )
        }

        // 결과 화면은 추후 구현 예정
        composable(route = Route.ChecklistResult.route) {
            // ChecklistResultScreen(navController = navController)
        }

        // 영역별 테스트 화면
        composable(
            route = "area_test/{areaType}",
            arguments = listOf(
                navArgument("areaType") { 
                    type = androidx.navigation.NavType.StringType 
                }
            )
        ) { backStackEntry ->
            val areaType = backStackEntry.arguments?.getString("areaType")?.let {
                AreaType.valueOf(it)
            } ?: AreaType.READING
            AreaTestScreen(navController = navController, areaType = areaType)
        }
    }
}