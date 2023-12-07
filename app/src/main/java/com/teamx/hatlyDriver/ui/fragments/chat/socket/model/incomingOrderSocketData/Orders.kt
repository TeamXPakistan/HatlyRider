package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingOrderSocketData

data class
Orders(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val customer: Customer,
    val deliveryCharges: Double,
    val isPayed: Boolean,
    val merchant: String,
    val orderId: String,
    val orderType: String,
    val products: List<Product>,
    val status: String,
    val subTotal: Int,
    val tax: Double,
    val total: Double,
    val updatedAt: String,
    val useWallet: Boolean,
    val usedWalletAmount: Any
)