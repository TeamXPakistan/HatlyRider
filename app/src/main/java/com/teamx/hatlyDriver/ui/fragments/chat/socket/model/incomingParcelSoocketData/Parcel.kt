package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingParcelSoocketData

data class Parcel(
    val _id: String,
    val createdAt: String,
    val details: Details,
    val fare: Double,
    val receiverLocation: ReceiverLocation,
    val senderId: SenderId,
    val senderLocation: SenderLocation,
    val status: String,
    val trackingNumber: String,
    val updatedAt: String
)