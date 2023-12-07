package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.allmessageData

import androidx.annotation.Keep

@Keep
data class Doc(
    val _id: String,
    val createdAt: String,
    val entityId: String,
    val `file`: Any,
    val from: String,
    val isRead: Boolean,
    val message: String,
    val orderId: String,
    val socketId: String,
    val to: String
)