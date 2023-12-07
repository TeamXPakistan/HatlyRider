package com.teamx.hatlyDriver.data.dataclasses.getActiveorder

data class Product(
    val _id: String,
    val image: String,
    val prize: Int,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val shopId: String,
    val veriations: Any
)