package com.konkuk.gomgomee.presentation.findcare

data class Institution(
    val institutionId: Int,
    val name: String,
    val address: String,
    val phone: String?,
    val latitude: Double,
    val longitude: Double,
    val category: String
)
