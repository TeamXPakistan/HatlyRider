package com.teamx.hatlyDriver.data.dataclasses.pastorder

data class Shop(
    val _id: String,
    val address: Address,
    val balance: Balance,
    val bankAccountDetails: BankAccountDetails,
    val createdAt: String,
    val delivery: Delivery,
    val image: String,
    val isOpen: Boolean,
    val name: String,
    val owner: String,
    val ratting: Double,
//    val resturantCategory: List<String>,
    val setting: Setting,
    val status: String,
    val totalReviews: Int,
    val tradeLicence: TradeLicence,
    val type: String,
    val updatedAt: String
)