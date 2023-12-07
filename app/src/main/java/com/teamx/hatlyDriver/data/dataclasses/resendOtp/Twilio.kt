package com.teamx.hatlyDriver.data.models.resendOtp

import androidx.annotation.Keep


@Keep
data class Twilio(
    val accountSid: String,
    val amount: Any,
    val channel: String,
    val dateCreated: String,
    val dateUpdated: String,
    val lookup: Lookup,
    val payee: Any,
    val sendCodeAttempts: List<SendCodeAttempt>,
    val serviceSid: String,
    val sid: String,
    val status: String,
    val to: String,
    val url: String,
    val valid: Boolean
)