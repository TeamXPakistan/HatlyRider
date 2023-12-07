package com.teamx.hatlyDriver.data.models.riderMessage

import androidx.annotation.Keep

@Keep
data class RiderMessage(
    val _id: String,
    val createdAt: String,
    val entityId: String,
    val `file`: Any,
    val from: String,
    val isRead: Boolean,
    val message: String,
    val isUser: Boolean,
    val orderId: String,
    val socketId: String,
    val to: String
)