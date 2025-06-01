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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.presentation.home.component.HomeCardItem
import com.konkuk.gomgomee.ui.theme.White
import com.konkuk.gomgomee.util.modifier.noRippleClickable

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToHomeDetail: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val homeCardItems = viewModel.homeCardItems

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
                text = stringResource(R.string.home_screen_title),
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 18.dp)
            )

            Image(
                painter = painterResource(R.drawable.ic_home_gomgomee),
                contentDescription = stringResource(R.string.home_gomgomee_image_description),
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
                    cardDescription = card.cardDescription,
                    modifier = Modifier.noRippleClickable {
                        navigateToHomeDetail()
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        navigateToHomeDetail = {}
    )
}