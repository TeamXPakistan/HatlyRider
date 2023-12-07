package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingOrderSocketData

data class Product(
    val _id: String,
    val image: String,
    val prize: Int,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val shopId: String,
    val specialInstruction: String,
    val veriations: List<Veriation>
)