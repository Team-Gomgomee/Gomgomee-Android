package com.konkuk.gomgomee.presentation.findcare

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konkuk.gomgomee.presentation.viewmodel.FavoriteViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState
import java.io.InputStreamReader

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun NaverMapScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel()
) {
    val context = LocalContext.current
    val institutions by remember {
        mutableStateOf(loadInstitutionsFromAssets(context))
    }

    val cameraPositionState = rememberCameraPositionState()
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val locationSource = rememberFusedLocationSource()

    var selectedInstitution by remember { mutableStateOf<Institution?>(null) }

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    val granted = permissionState.permissions.any { it.status.isGranted }

    Box(modifier = modifier.fillMaxSize()) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            locationSource = if (granted) locationSource else null,
            properties = if (granted) {
                MapProperties(locationTrackingMode = LocationTrackingMode.Face)
            } else {
                MapProperties()
            },
            onMapClick = { _: android.graphics.PointF, _: LatLng ->
                selectedInstitution = null
            }
        ) {
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

        selectedInstitution?.let { inst ->
            InstitutionCard(
                inst = inst,
                viewModel = viewModel, // 전달
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(20.dp)
            )
        }
    }
}

fun loadInstitutionsFromAssets(context: Context): List<Institution> {
    val inputStream = context.assets.open("initial_data/mental_hospitals.json")
    val reader = InputStreamReader(inputStream, "UTF-8")

    val type = object : TypeToken<List<Institution>>() {}.type
    return Gson().fromJson(reader, type)
}