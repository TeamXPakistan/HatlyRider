package com.teamx.hatlyDriver.data.dataclasses.getActiveorder

data class Rating(
//    val _id: IdXX,
    val createdAt: String,
    val description: String,
    val images: List<String>,
    val name: String,
    val ratting: String,
    val shopId: ShopId,
    val updatedAt: String,
//    val userId: UserIdXX
)