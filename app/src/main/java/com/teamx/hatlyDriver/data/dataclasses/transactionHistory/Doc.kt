package com.teamx.hatlyDriver.data.dataclasses.transactionHistory

data class Doc(
    val charges: Double,
    val createdAt: String,
    val description: String,
    val requestFor: String
)