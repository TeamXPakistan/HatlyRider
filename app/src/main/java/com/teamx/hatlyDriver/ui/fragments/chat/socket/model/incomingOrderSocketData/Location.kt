package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingOrderSocketData

data class Location(
    val city: String,
    val coordinates: List<Double>,
    val country: String,
    val formattedAddress: String,
    val lat: Double,
    val lng: Double,
    val state: String,
    val type: String
)