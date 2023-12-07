package com.teamx.hatlyDriver.data.models

data class ProductModel(
    val id: Int,
    val title: String,
    val price: String,
    val category: String,
    val description: String,
    val image: String,
    var isAddToCard: Boolean = false
)