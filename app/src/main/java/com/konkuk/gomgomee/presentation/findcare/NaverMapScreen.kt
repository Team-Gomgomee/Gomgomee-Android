package com.konkuk.gomgomee.presentation.findcare

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.*

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun NaverMapScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState()
    val permissionState = rememberMultiplePermissionsState(
        permissions =
            listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
    )
    val locationSource = rememberFusedLocationSource()

    //병원 정보3개 임시 저장 (데베 구축되면 api로 가져와야함)
    val institutions = listOf(
        Institution(1, "서울곰병원", "서울 성동구 뚝섬로 123", "02-1234-5678", 37.541, 127.079, "병원"),
        Institution(2, "마음곰 심리상담센터", "서울 광진구 능동로 456", "02-2345-6789", 37.543, 127.081, "상담소"),
        Institution(3, "강북곰정형외과", "서울 강북구 도봉로 789", "02-3456-7890", 37.542, 127.078, "병원")
    )

    // 클릭된 마커에 해당하는 Institution
    var selectedInstitution by remember { mutableStateOf<Institution?>(null) }


    //**************************현재 위치 설정을 위한 권한 설정********************************
    // 권한 요청: 처음에 한 번만
    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    val granted = permissionState.permissions.any{it.status.isGranted}
    //********************************************************************************

    // UI 전체 구성
    Box(modifier = modifier.fillMaxSize()) {
        if(granted){
            NaverMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                locationSource = locationSource,
                properties = MapProperties(
                    locationTrackingMode = LocationTrackingMode.Face
                ),
                onMapClick = { _: android.graphics.PointF, _: LatLng ->
                    selectedInstitution = null
                }
            ) {
                // 병원 마커
                institutions.forEach { inst ->
                    val markerState =
                        rememberMarkerState(position = LatLng(inst.latitude, inst.longitude))
                    Marker(
                        state = markerState,
                        captionText = inst.name,
                        onClick = {
                            selectedInstitution = inst
                            true
                        }
                    )
                }
            }
        }else{
            NaverMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ){
                // 병원 마커
                institutions.forEach { inst ->
                    val markerState =
                        rememberMarkerState(position = LatLng(inst.latitude, inst.longitude))
                    Marker(
                        state = markerState,
                        captionText = inst.name,
                        onClick = {
                            selectedInstitution = inst
                            true
                        }
                    )
                }
            }
        }


        // 선택된 병원 카드
        selectedInstitution?.let { inst ->
            InstitutionCard(
                inst = inst,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(10.dp)
            )
        }
    }
}