package com.teamx.raseef.ui.fragments.messages.socket.model
import androidx.annotation.Keep

@Keep
data class OrderDetail(
    val __v: Int,
    val _id: String,
    val amount: Double,
    val billing_address: BillingAddress,
    val children: List<Any>,
    val createdAt: String,
    val customer: String,
    val customer_contact: String,
    val delivery_fee: Double,
    val initial_location: InitialLocation,
    val paid_total: Int,
    val payment_gateway: String,
    val products: List<String>,
    val riding_status: String,
    val sales_tax: Double,
    val shipping_address: ShippingAddress,
    val shop: String,
    val special_request: String,
    val status: String,
    val total: Double,
    val updatedAt: String,
    val used_points: Int,
    val user_location: UserLocation,
    val user_riding: Boolean,
    val vehical: String
)