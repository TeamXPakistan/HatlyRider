package com.teamx.hatlyDriver.data.dataclasses.getOrderStatus

data class DropOff(
    val _id: String,
    val additionalDirection: String,
    val address: String,
    val apartmentNumber: Int,
    val building: String,
    val coordinates: Coordinates,
    val isDefault: Boolean,
    val label: String,
    val lat: Double,
    val lng: Double,
    val type: String,
    val userId: String
)