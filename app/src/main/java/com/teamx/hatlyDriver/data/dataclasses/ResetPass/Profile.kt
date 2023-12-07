package com.teamx.hatlyDriver.data.dataclasses.ResetPass
import androidx.annotation.Keep

@Keep
data class Profile(
    val __v: Int,
    val _id: String,
    val carImage: String,
    val carPlate: String,
    val contact: String,
    val contact_verified: Boolean,
    val createdAt: String,
    val email: String,
    val email_verified: Boolean,
    val favouritShop: List<Any>,
    val gender: String,
    val has_subscribed: Boolean,
    val is_active: Boolean,
    val name: String,
    val password: String,
    val roles: List<String>,
    val shops: List<Any>,
    val stripe_customer_id: String,
    val updatedAt: String
)