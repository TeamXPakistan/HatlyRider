package com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.modelGetCards

data class Card(
    val brand: String,
    val checks: Checks,
    val country: String,
    val exp_month: Int,
    val exp_year: Int,
    val fingerprint: String,
    val funding: String,
    val generated_from: Any,
    val last4: String,
    val networks: Networks,
    val three_d_secure_usage: ThreeDSecureUsage,
    val wallet: Any
)