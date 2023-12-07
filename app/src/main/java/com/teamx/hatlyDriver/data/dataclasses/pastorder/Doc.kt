package com.teamx.hatlyDriver.data.dataclasses.pastorder

data class Doc(
    val _id: String,
    val charges: Double,
    val createdAt: String,
    val dropOff: DropOff,
    val estimatedDeliveryTime: Any,
    val orders: Orders,
    val pickup: Pickup,
    val requestFor: String,
    val requestId: String,
    val riderId: String,
    val shop: Shop,
    val status: String,
    val total: Double,
    val updatedAt: String
)