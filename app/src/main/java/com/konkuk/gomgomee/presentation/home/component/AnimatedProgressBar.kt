package com.konkuk.gomgomee.presentation.home.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konkuk.gomgomee.ui.theme.Gray50
import com.konkuk.gomgomee.ui.theme.Green300
import com.konkuk.gomgomee.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun AnimatedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = Gray50
    val progressColor = Green300

    val animatedProgress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(progress) {
        scope.launch {
            animatedProgress.animateTo(
                targetValue = progress.coerceIn(0f, 1f),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Box(
        modifier = modifier
            .background(color = White)
            .fillMaxWidth()
            .height(8.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = animatedProgress.value)
                .fillMaxHeight()
                .background(
                    color = progressColor,
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}