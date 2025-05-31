package com.konkuk.gomgomee.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.data.HomeCardData
import com.konkuk.gomgomee.presentation.home.component.HomeCardItem
import com.konkuk.gomgomee.ui.theme.White

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val homeCardItems = listOf(
        HomeCardData("학습장애란?", "학습장애란 읽기, 쓰기, 추론, 산수계산 등의 능력과 획득 및 사용상의 심각한 곤란을 주 증상으로 하는, 다양한 원인을 배경으로 하는 이질적인 장애군을 총칭하는 용어이다."),
        HomeCardData("ADHD란?", "주의력 결핍/과잉행동 장애(Attention Deficit/Hyperactivity Disorder)는 아동기에 많이 나타나는 장애로, 지속적으로 주의력이 부족하여 산만하고 과다활동을 보이는 장애를 말한다."),
        HomeCardData("학습장애란?", "학습장애란 읽기, 쓰기, 추론, 산수계산 등의 능력과 획득 및 사용상의 심각한 곤란을 주 증상으로 하는, 다양한 원인을 배경으로 하는 이질적인 장애군을 총칭하는 용어이다."),
        HomeCardData("ADHD란?", "주의력 결핍/과잉행동 장애(Attention Deficit/Hyperactivity Disorder)는 아동기에 많이 나타나는 장애로, 지속적으로 주의력이 부족하여 산만하고 과다활동을 보이는 장애를 말한다.")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(start = 20.dp, end = 20.dp, top = 50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "곰곰이가 알려주는\n학습장애 관련 지식",
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 18.dp)
            )

            Image(
                painter = painterResource(R.drawable.ic_home_gomgomee),
                contentDescription = "home gomgomee image",
                modifier = Modifier.size(102.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(homeCardItems) { card ->
                HomeCardItem(
                    cardTitle = card.cardTitle,
                    cardDescription = card.cardDescription
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}