package com.example.swimpool.model

data class SwimmingPool(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phoneNumber: String? = null,
    val description: String? = null
)
