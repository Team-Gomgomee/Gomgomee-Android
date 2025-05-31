package com.konkuk.gomgomee.presentation.navigation

sealed class Route(
    val route: String
) {
    data object Splash: Route(route = "splash")

    data object SignUp: Route(route = "signup")

    data object Login: Route(route = "login")

    data object Home: Route(route = "home")

    data object HomeDetail: Route(route = "homedetail")

    data object Diagnosis: Route(route = "diagnosis")

    data object FindCare: Route(route = "findcare")

    data object MyPage: Route(route = "mypage")

    data object Checklist: Route(route = "checklist")

    data object ChecklistResult: Route(route = "checklist_result")
}