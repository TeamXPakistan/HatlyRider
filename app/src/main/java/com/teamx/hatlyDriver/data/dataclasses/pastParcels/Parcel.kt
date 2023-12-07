package com.teamx.hatlyDriver.data.dataclasses.pastParcels

data class Parcel(
    val _id: String,
    val details: Details,
    val fare: Double,
    val receiverLocation: ReceiverLocation,
    val senderId: SenderId,
    val senderLocation: SenderLocation,
    val status: String,
    val trackingNumber: String
)