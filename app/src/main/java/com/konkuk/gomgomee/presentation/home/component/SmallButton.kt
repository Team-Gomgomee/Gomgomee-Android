package com.konkuk.gomgomee.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.ui.theme.Black
import com.konkuk.gomgomee.ui.theme.Gray100
import com.konkuk.gomgomee.ui.theme.Gray50
import com.konkuk.gomgomee.ui.theme.Green300
import com.konkuk.gomgomee.util.modifier.noRippleClickable

@Composable
fun SmallButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (enabled) Green300 else Gray50
    val contentColor = if (enabled) Black else Gray100

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(72.dp)
            .height(52.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(26.dp)
            )
            .noRippleClickable(onClick)
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}