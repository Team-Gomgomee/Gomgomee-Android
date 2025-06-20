package com.konkuk.gomgomee.presentation.findcare

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.presentation.viewmodel.FavoriteViewModel
import com.konkuk.gomgomee.ui.theme.Gray100
import com.konkuk.gomgomee.ui.theme.Green200
import com.konkuk.gomgomee.ui.theme.Red
import com.konkuk.gomgomee.util.modifier.noRippleClickable
import kotlinx.coroutines.launch

@Composable
fun InstitutionCard(
    inst: Institution,
    viewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val userNo = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        .getInt("current_user_no", -1)


    LaunchedEffect(inst.institutionId) {
        isFavorite = viewModel.isFavorite(inst.institutionId)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Green200)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = inst.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = inst.category,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .noRippleClickable {
                            scope.launch {
                                if (isFavorite) {
                                    viewModel.removeFavorite(inst.institutionId)
                                } else {
                                    viewModel.addFavorite(inst)
                                }
                                isFavorite = !isFavorite
                            }
                        }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "즐겨찾기 해제" else "즐겨찾기 등록",
                        tint = if (isFavorite) Red else Gray100
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = inst.phone ?: "전화번호 없음",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Text(
                text = inst.address,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}