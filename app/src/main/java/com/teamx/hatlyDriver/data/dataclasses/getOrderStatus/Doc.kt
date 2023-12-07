package com.teamx.hatlyDriver.data.dataclasses.getOrderStatus

data class Doc(
    val _id: String,
    val dropOff: DropOff,
    val parcel: Parcel,
    val pickup: Pickup,
    val rejectionReason: String,
    val requestFor: String,
    val requestId: String,
    val riderId: String,
    val status: String
)