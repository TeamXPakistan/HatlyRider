package com.teamx.hatlyDriver.data.dataclasses.model

import androidx.annotation.Keep

@Keep
data class ModelSignUpOtpVerify(
    val contact: String,
    val email: String,
    val name: String,
    val token: String,
    val verified: Boolean
)