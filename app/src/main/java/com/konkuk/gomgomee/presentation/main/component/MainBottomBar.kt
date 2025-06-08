package com.konkuk.gomgomee.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.presentation.navigation.BottomNavItem
import com.konkuk.gomgomee.ui.theme.Gray100
import com.konkuk.gomgomee.ui.theme.Green500

@Composable
fun MainBottomBar(
    visible: Boolean,
    tabs: List<BottomNavItem>,
    currentRoute: String?,
    modifier: Modifier = Modifier,
    onTabSelected: (BottomNavItem) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(68.dp)
        ) {
            tabs.forEach { item ->
                val selected = item.route == currentRoute
                val iconRes = if (selected) item.selectedIcon else item.unselectedIcon
                val textColor = if (selected) Green500 else Gray100

                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxHeight()
                        .weight(1f)
                        .selectable(
                            selected = selected,
                            onClick = { onTabSelected(item) },
                            role = Role.Tab,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = item.label,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = item.label,
                        color = textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
