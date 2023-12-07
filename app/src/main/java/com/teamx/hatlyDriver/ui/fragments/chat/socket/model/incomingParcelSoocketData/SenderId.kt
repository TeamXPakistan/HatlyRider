package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingParcelSoocketData

data class SenderId(
    val __v: Int,
    val _id: String,
    val contact: String,
    val coordinates: Coordinates,
    val createdAt: String,
    val deviceData: String,
    val email: String,
    val fcmToken: String,
    val isEnabled: Boolean,
    val location: String,
    val name: String,
    val notificationCount: Int,
    val password: String,
    val profileImage: String,
    val role: String,
    val stripeId: String,
    val updatedAt: String,
    val verified: Boolean,
    val wallet: String
)