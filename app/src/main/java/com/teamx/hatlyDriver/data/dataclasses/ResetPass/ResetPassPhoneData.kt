package com.teamx.hatlyDriver.data.dataclasses.ResetPass
import androidx.annotation.Keep

@Keep
data class ResetPassPhoneData(
    val message: String,
    val profile: Profile,
    val success: Boolean
)