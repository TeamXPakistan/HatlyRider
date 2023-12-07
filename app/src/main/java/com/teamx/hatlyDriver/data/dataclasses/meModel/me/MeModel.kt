package com.teamx.hatlyDriver.data.dataclasses.meModel.me

import androidx.annotation.Keep

@Keep
data class MeModel(
    val  stripeId: String,
    val __v: Int,
    val _id: String,
    val contact: String,
    val coordinates: Coordinates,
    val email: String,
    val location: Location,
    val name: String,
    val profileImage: String,
    val role: String,
    val verified: Boolean,
    val wallet: Double
)