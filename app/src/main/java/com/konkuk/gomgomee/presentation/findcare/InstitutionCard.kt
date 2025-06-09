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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.konkuk.gomgomee.presentation.viewmodel.FavoriteViewModel
import com.konkuk.gomgomee.ui.theme.Green200
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
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Green200)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                scope.launch {
                    if (isFavorite) {
                        viewModel.removeFavorite(inst.institutionId)
                    } else {
                        viewModel.addFavorite(inst)
                    }
                    isFavorite = !isFavorite
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Text(if (isFavorite) "즐겨찾기 해제" else "즐겨찾기 등록")
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = inst.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = inst.category, fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = inst.phone ?: "전화번호 없음", fontSize = 14.sp, color = Color.DarkGray)
            Text(text = inst.address, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}