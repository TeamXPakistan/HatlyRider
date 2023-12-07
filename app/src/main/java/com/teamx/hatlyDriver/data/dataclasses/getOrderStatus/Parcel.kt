package com.teamx.hatlyDriver.data.dataclasses.getOrderStatus

data class Parcel(
    val _id: Id,
    val createdAt: String,
    val details: Details,
    val fare: Int,
    val receiverLocation: ReceiverLocation,
    val senderId: SenderId,
    val senderLocation: SenderLocation,
    val status: String,
    val trackingNumber: String,
    val updatedAt: String
)