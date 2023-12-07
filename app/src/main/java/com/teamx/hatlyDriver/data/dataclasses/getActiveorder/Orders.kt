package com.teamx.hatlyDriver.data.dataclasses.getActiveorder

data class Orders(
    val __v: Int,
    val _id: String,
    val clientSecret: String,
    val customer: String,
    val deliveryCharges: Double,
    val dropOff: DropOffX,
    val isPayed: Boolean,
    val merchant: String,
    val orderId: String,
    val orderType: String,
    val products: List<Product>,
    val shop: Shop,
    val specialNote: String,
    val status: String,
    val subTotal: Int,
    val tax: Int,
    val total: Double,
    val useWallet: Boolean,
    val usedWalletAmount: Any
)