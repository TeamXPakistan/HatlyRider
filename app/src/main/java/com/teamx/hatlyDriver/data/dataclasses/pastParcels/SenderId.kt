package com.teamx.hatlyDriver.data.dataclasses.pastParcels

data class SenderId(
    val __v: Int,
    val _id: String,
    val contact: String,
    val coordinates: Coordinates,
    val location: LocationX,
    val name: String,
    val profileImage: String,
    val role: String,
    val verified: Boolean,
    val wallet: Any
)