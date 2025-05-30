package com.konkuk.gomgomee.presentation.findcare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.konkuk.gomgomee.ui.theme.White
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState

@Composable
fun FindCareScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "기관 찾기 메인 화면"
        )
        NaverMapScreen()
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(modifier: Modifier = Modifier) {
    val konkuk = LatLng(37.5408,127.0793)
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition(konkuk,15.0)
    }

    NaverMap(
        modifier=modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ){
        Marker(
            state = rememberMarkerState(position = konkuk),
            captionText = "건국대학교"
        )
    }
}