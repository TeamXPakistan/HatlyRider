package com.teamx.hatlyDriver.data.dataclasses.getActiveorder

data class Customer(
    val __v: Int,
    val _id: String,
    val contact: String,
    val coordinates: Coordinates,
    val email: String,
    val location: Location,
    val name: String,
    val image: String,
    val role: String,
    val verified: Boolean,
    val wallet: Double
)