package com.teamx.raseef.ui.fragments.messages.socket.model
import androidx.annotation.Keep

@Keep
data class InitialLocation(
    val _id: String,
    val coordinates: List<Double>,
    val lat: Double,
    val lng: Double,
    val type: String
)