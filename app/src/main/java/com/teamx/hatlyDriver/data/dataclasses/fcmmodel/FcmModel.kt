package com.teamx.hatlyDriver.data.dataclasses.fcmmodel

import androidx.annotation.Keep

@Keep
data class FcmModel(
    val message: String,
    val success: Boolean
)