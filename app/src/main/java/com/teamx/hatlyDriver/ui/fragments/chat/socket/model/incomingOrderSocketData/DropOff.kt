package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingOrderSocketData

data class DropOff(
    val _id: String,
    val additionalDirection: String,
    val address: String,
    val apartmentNumber: Int,
    val building: String,
    val coordinates: Coordinates,
    val createdAt: String,
    val isDefault: Boolean,
    val isDeleted: Boolean,
    val label: String,
    val lat: Double,
    val lng: Double,
    val type: String,
    val updatedAt: String,
    val userId: String
)