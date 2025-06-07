package com.konkuk.gomgomee.presentation.findcare

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun NaverMapScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    Log.d("권한 상태", locationPermissionState.status.toString())
    // 권한 요청: 처음에 한 번만
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    // 권한 허용 시 위치 요청
    val didRequestLocation = remember { mutableStateOf(false) }
    if (permissionState.status.isGranted && !didRequestLocation.value) {
        LaunchedEffect("location-request") {
            getCurrentLocation(context) { location ->
                location?.let {
                    currentLocation = LatLng(it.latitude, it.longitude)
                    Log.d("위치", "lat=${it.latitude}, lon=${it.longitude}")
                    cameraPositionState.position = CameraPosition(currentLocation!!, 15.0)
                    didRequestLocation.value = true
                }
            }
        }
    }

    NaverMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        currentLocation?.let {
            Marker(
                state = rememberMarkerState(position = it),
                captionText = "현재 위치"
            )
        }
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            onLocationReceived(location)
        }
        .addOnFailureListener {
            onLocationReceived(null)
        }
}