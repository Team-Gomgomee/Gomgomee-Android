package com.konkuk.gomgomee.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konkuk.gomgomee.data.HomeDetailCardData
import com.konkuk.gomgomee.presentation.home.component.AnimatedProgressBar
import com.konkuk.gomgomee.presentation.home.component.HomeDetailCardItem
import com.konkuk.gomgomee.presentation.home.component.SmallButton
import com.konkuk.gomgomee.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeDetailViewModel = viewModel()
) {
    val sections = viewModel.detailCards
    val isLoading = viewModel.isLoading.value

    val refreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.fetchLearningDisorderInfo() }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchLearningDisorderInfo()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
            .background(White)
            .padding(horizontal = 16.dp)
    ) {
        if (sections.isEmpty()) {
            Text(
                text = when {
                    isLoading -> "불러오는 중 ..."
                    else      -> "데이터가 없습니다.\n당겨서 새로고침 하세요."
                },
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        } else {
            val pages: List<List<HomeDetailCardData>> = remember(sections) {
                sections.chunked(3)
            }
            val totalPages = pages.size

            var currentPage by remember { mutableIntStateOf(0) }

            Column(modifier = Modifier.fillMaxSize()) {
                val progressFraction = (currentPage + 1).toFloat() / totalPages.toFloat()

                Spacer(modifier = Modifier.height(30.dp))

                AnimatedProgressBar(
                    progress = progressFraction.coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "곰곰이가 SNUH 의학정보 페이지에서\n가져온 정보를 확인해보세요!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 22.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(pages[currentPage]) { card ->
                        HomeDetailCardItem(
                            cardDetail = card
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallButton(
                        text = "이전",
                        enabled = (currentPage > 0),
                        onClick = { currentPage = (currentPage - 1).coerceAtLeast(0) }
                    )

                    Text(
                        text = "${currentPage + 1} / $totalPages",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    SmallButton(
                        text = "다음",
                        enabled = (currentPage < totalPages - 1),
                        onClick = { currentPage = (currentPage + 1).coerceAtMost(totalPages - 1) }
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}