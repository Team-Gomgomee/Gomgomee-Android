package com.konkuk.gomgomee.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.konkuk.gomgomee.presentation.findcare.InstitutionCard
import com.konkuk.gomgomee.presentation.viewmodel.FavoriteViewModel
import com.konkuk.gomgomee.ui.theme.White

@Composable
fun FavoriteInstitutionsScreen(
    navController: NavController
) {
    val viewModel: FavoriteViewModel = viewModel()
    val favoriteInstitutions by viewModel.favorites.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(20.dp)
    ) {
        Text(
            text = "즐겨찾기한 병원",
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
        )

        if (favoriteInstitutions.isEmpty()) {
            Text(
                text = "즐겨찾기한 병원이 없습니다.",
                fontSize = 16.sp,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(favoriteInstitutions) { institution ->
                    InstitutionCard(
                        inst = institution,
                        viewModel = viewModel, // ViewModel 전달
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
