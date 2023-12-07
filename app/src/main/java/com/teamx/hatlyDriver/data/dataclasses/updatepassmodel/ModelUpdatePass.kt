package com.teamx.hatlyUser.ui.fragments.auth.createpassword.model

import androidx.annotation.Keep

@Keep
data class ModelUpdatePass(
    val _id: String,
    val email: String,
    val name: String,
    val token: String
)