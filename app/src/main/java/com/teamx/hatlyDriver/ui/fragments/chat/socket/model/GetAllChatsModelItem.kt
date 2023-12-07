package com.teamx.raseef.ui.fragments.messages.socket.model

import androidx.annotation.Keep

@Keep
data class GetAllChatsModelItem(
    val __v: Int,
    val _id: String,
    var createdAt: String,
    val messages: List<MessageX>,
    val order: String,
    val order_detail: List<OrderDetail>,
    val shop_details: List<ShopDetails>,
    val updatedAt: String,
    val users: List<String>,
    var color: String = "#FFFFFF",
)
@Keep
data class ShopDetails(
    val __v: Int,
    val orders_count: Int,
    val products_count: Int,
    val _id: String,
    val name: String,
    val slug: String,
    val owner: String,
    val cover_image: String,
    val description: String,
    val logo: String,
    val shop_type: String,
    val is_active: Boolean,
    var rating: Int,

    )
/*
"shop_details":[{"_id":"64a81045b9796ea860314e60",
    "owner":"64a80b91b9796ea860314c28",
    "shop_type":"64a80e41b9796ea860314dc8",
    "is_active":true,
    "orders_count":0,
    "products_count":3,
    "name":"Simple clothing",
    "slug":"simple-clothing",
    "description":"quality clothes under one roof",
    "cover_image":"https:\/\/res.cloudinary.com\/dpcgdl7eu\/image\/upload\/v1689322712\/w7akl4g6yfkdvy5dmelm.jpg",
    "rating":0,
    "logo":"https:\/\/res.cloudinary.com\/dpcgdl7eu\/image\/upload\/v1689315386\/wnv6rj8zagzwk5ky0o2q.png",
    "_id":"64b5023eed5b0b7181b7ac8d"},*/
