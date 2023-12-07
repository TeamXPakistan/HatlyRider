package com.teamx.hatlyDriver.data.models.resendOtp

import androidx.annotation.Keep


@Keep
data class ResendOtpData(
    val id: String,
    val message: String,
    val phone_number: String,
    val provider: String,
    val success: Boolean,
    val twilio: Twilio
)