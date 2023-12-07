package com.teamx.hatlyDriver.data.dataclasses.SignUp

data class RegisterData(
    val __v: Int,
    val _id: String,
    val contact: String,
    val email: String,
    val name: String,
    val role: String,
    val verified: Boolean,
    val wallet: Int
)