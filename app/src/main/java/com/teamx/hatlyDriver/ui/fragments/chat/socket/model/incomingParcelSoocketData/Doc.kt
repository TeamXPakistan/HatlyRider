package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingParcelSoocketData

data class Doc(
    val _id: String,
    val charges: Int,
    val createdAt: String,
    val estimatedDeliveryTime: Int,
    val parcel: Parcel,
    val requestFor: String,
    val requestId: String? = "",
    val riderId: String?= "" ,
    val status: String,
    val updatedAt: String
)