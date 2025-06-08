package com.konkuk.gomgomee.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.presentation.main.component.MainBottomBar
import com.konkuk.gomgomee.presentation.navigation.BottomNavItem
import com.konkuk.gomgomee.presentation.navigation.GomgomeeNavGraph
import com.konkuk.gomgomee.presentation.navigation.Route
import com.konkuk.gomgomee.ui.theme.Green200
import com.konkuk.gomgomee.ui.theme.White

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = currentBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem("홈", Route.Home.route, R.drawable.ic_home_selected, R.drawable.ic_home_unselected),
        BottomNavItem("진단하기", Route.Diagnosis.route, R.drawable.ic_diagnosis_selected, R.drawable.ic_diagnosis_unselected),
        BottomNavItem("기관 찾기", Route.FindCare.route, R.drawable.ic_find_care_selected, R.drawable.ic_find_care_unselected),
        BottomNavItem("마이페이지", Route.MyPage.route, R.drawable.ic_my_page_selected, R.drawable.ic_my_page_unselected)
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavItems.map { it.route }) {
                Box(
                    modifier = Modifier
                        .background(White)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(Green200)
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent
                    ) {
                        bottomNavItems.forEach { item ->
                            MainBottomBar(
                                visible = true,
                                tabs = bottomNavItems,
                                currentRoute = currentRoute,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                                    .background(Green200),
                                onTabSelected = { selectedItem ->
                                    navController.navigate(selectedItem.route) {
                                        popUpTo(route = Route.Home.route) {
                                            inclusive = false
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        GomgomeeNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}