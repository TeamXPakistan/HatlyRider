package com.teamx.hatlyDriver.data.models.socket_models
import androidx.annotation.Keep

@Keep
data class HelloData(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val order: String,
    val socket_id: String,
    val updatedAt: String,
    val user: String
)