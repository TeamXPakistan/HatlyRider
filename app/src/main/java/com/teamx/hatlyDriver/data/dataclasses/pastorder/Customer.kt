package com.teamx.hatlyDriver.data.dataclasses.pastorder

data class Customer(
    val __v: Int,
    val _id: String,
    val contact: String,
    val coordinates: Coordinates,
    val createdAt: String,
    val email: String,
    val name: String,
    val profileImage: String,
    val role: String,
    val updatedAt: String,
    val verified: Boolean,
    val wallet: Double
)