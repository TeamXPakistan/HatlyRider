package com.teamx.hatlyDriver.data.dataclasses.meModel.me
import androidx.annotation.Keep

@Keep
data class Location(
    val _id: String,
    val additionalDirection: String,
    val address: String,
    val building: String,
    val coordinates: Coordinates,
    val isDefault: Boolean,
    val label: String,
    val lat: Double,
    val lng: Double,
    val type: String,
    val userId: String
)