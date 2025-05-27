package com.konkuk.gomgomee.presentation.navigation

sealed class Route(
    val route: String
) {
    data object Home: Route(route = "home")

    data object Diagnosis: Route(route = "diagnosis")

    data object FindCare: Route(route = "findcare")

    data object MyPage: Route(route = "mypage")
}