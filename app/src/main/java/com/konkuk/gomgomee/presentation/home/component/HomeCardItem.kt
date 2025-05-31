package com.konkuk.gomgomee.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.R
import com.konkuk.gomgomee.ui.theme.Green100
import com.konkuk.gomgomee.ui.theme.Green400

@Composable
fun HomeCardItem(
    cardTitle: String,
    cardDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(16.dp),
                color = Green100
            )
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(16.dp),
                color = Green400
            )
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = cardTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_24),
                    contentDescription = "right arrow",
                    tint = Color.Unspecified,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = cardDescription,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 18.sp
            )
        }
    }
}