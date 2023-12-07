package com.teamx.hatlyDriver.data.dataclasses.login

data class LoginData(
    val __v: Int,
    val _id: String,
    val contact: String,
    val email: String,
    val location: Location,
    var name: String,
    val role: String,
    val token: String,
    val verified: Boolean,
    val wallet: Int,
    var profileImage: String
    )