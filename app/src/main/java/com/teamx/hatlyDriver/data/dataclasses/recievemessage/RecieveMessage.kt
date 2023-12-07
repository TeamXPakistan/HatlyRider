package com.teamx.hatlyDriver.data.dataclasses.recievemessage

data class RecieveMessage(
    val _id: String,
    val createdAt: String,
    val entityId: String,
    val file: Any,
    val from: String,
    val isRead: Boolean,
    val message: String,
    val orderId: String,
    val socketId: String,
    val to: String
)