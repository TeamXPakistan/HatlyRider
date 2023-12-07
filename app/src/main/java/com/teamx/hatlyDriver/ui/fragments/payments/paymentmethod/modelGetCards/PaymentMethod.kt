package com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.modelGetCards

import androidx.annotation.Keep

@Keep
data class PaymentMethod(
    val billing_details: BillingDetails,
    val card: Card,
    val created: Int,
    val customer: String,
    val id: String,
    val livemode: Boolean,
    val metadata: Metadata,
    val `object`: String,
    val type: String,
    var default: Boolean,
)