package com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.defaultmodel
import androidx.annotation.Keep

@Keep
data class InvoiceSettings(
    val custom_fields: Any,
    val default_payment_method: String,
    val footer: Any,
    val rendering_options: Any
)