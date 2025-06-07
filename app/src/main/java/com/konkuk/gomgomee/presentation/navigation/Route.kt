package com.konkuk.gomgomee.presentation.navigation

import com.konkuk.gomgomee.type.DisorderType

sealed class Route(
    val route: String
) {
    data object Splash: Route(route = "splash")

    data object SignUp: Route(route = "signup")

    data object Login: Route(route = "login")

    data object Home: Route(route = "home")

    data object HomeDetail: Route(route = "homeDetail/{type}") {
        fun createRoute(type: DisorderType): String = "homeDetail/${type.name}"
    }

    data object Diagnosis: Route(route = "diagnosis")

    data object FindCare: Route(route = "findcare")

    data object MyPage: Route(route = "mypage")
}