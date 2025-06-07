package com.konkuk.gomgomee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konkuk.gomgomee.presentation.areatest.AreaTestScreen
import com.konkuk.gomgomee.presentation.areatest.AreaTestResultScreen
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
import com.konkuk.gomgomee.presentation.diagnosis.ChecklistResultScreen
import com.konkuk.gomgomee.presentation.diagnosis.ChecklistResultViewModel
import com.konkuk.gomgomee.presentation.diagnosis.ChecklistItem
import com.konkuk.gomgomee.presentation.areatest.AreaTestResultViewModel
import androidx.compose.ui.platform.LocalContext
import com.konkuk.gomgomee.presentation.mypage.TestHistoryScreen
import com.konkuk.gomgomee.presentation.mypage.FavoriteInstitutionsScreen
import com.konkuk.gomgomee.presentation.mypage.FeedbackScreen

@Composable
fun GomgomeeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(route = Route.Splash.route) {
            SplashScreen(
                navController = navController
            )
        }

        composable(route = Route.SignUp.route) {
            SignUpScreen(
                navController = navController
            )
        }

        composable(route = Route.Login.route) {
            LoginScreen(
                navController = navController
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
            MyPageScreen(
                navController = navController,
                onLogout = {
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Route.TestHistory.route) {
            TestHistoryScreen(navController = navController)
        }

        composable(route = Route.FavoriteInstitutions.route) {
            FavoriteInstitutionsScreen(navController = navController)
        }

        composable(route = Route.Feedback.route) {
            FeedbackScreen(navController = navController)
        }

        composable(route = Route.HomeDetail.route) {
            HomeDetailScreen()
        }

        composable(route = Route.Checklist.route) {
            ChecklistScreen(
                navController = navController
            )
        }

        composable(route = "checklist_result") {
            val checklistItems = navController.previousBackStackEntry?.savedStateHandle?.get<List<ChecklistItem>>("checklistItems") ?: emptyList()
            ChecklistResultScreen(
                navController = navController,
                viewModel = viewModel(
                    factory = ChecklistResultViewModel.factory(checklistItems)
                )
            )
        }

        // 영역별 테스트 화면
        composable(
            route = Route.AreaTest.route,
            arguments = listOf(
                navArgument("areaType") { 
                    type = androidx.navigation.NavType.StringType 
                },
                navArgument("userNo") {
                    type = androidx.navigation.NavType.IntType
                }
            )
        ) { backStackEntry ->
            val areaType = backStackEntry.arguments?.getString("areaType")?.let {
                AreaType.valueOf(it)
            } ?: AreaType.READING
            val userNo = backStackEntry.arguments?.getInt("userNo") ?: 0
            AreaTestScreen(navController = navController, areaType = areaType, userNo = userNo)
        }

        // 영역별 테스트 결과 화면
        composable(
            route = Route.AreaTestResult.route,
            arguments = listOf(
                navArgument("totalQuestions") {
                    type = androidx.navigation.NavType.IntType
                },
                navArgument("correctAnswers") {
                    type = androidx.navigation.NavType.IntType
                }
            )
        ) { backStackEntry ->
            val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 0
            val correctAnswers = backStackEntry.arguments?.getInt("correctAnswers") ?: 0
            
            AreaTestResultScreen(
                navController = navController,
                viewModel = viewModel(
                    factory = AreaTestResultViewModel.factory(
                        application = context.applicationContext as android.app.Application,
                        totalQuestions = totalQuestions,
                        correctAnswers = correctAnswers
                    )
                )
            )
        }
    }
}