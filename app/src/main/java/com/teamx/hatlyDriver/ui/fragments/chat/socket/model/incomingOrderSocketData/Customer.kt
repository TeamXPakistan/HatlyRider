package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingOrderSocketData

data class Customer(
    val __v: Int,
    val _id: String,
    val contact: String,
    val coordinates: Coordinates,
    val createdAt: String,
    val deviceData: String,
    val email: String,
    val fcmToken: String,
    val forgotPasswordToken: String,
    val isEnabled: Boolean,
    val name: String,
    val notificationCount: Int,
    val password: String,
    val profileImage: String,
    val role: String,
    val stripeId: String,
    val updatedAt: String,
    val verificationToken: String,
    val verified: Boolean,
    val wallet: String
)