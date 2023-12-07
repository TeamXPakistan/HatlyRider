package com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.modelDetach

data class ModelDetachCredCards(
    val billing_details: BillingDetails,
    val card: Card,
    val created: Int,
    val customer: Any,
    val id: String,
    val livemode: Boolean,
    val metadata: Metadata,
    val `object`: String,
    val type: String
)