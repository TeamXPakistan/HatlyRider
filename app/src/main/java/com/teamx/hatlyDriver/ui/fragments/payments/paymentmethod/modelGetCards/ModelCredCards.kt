package com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.modelGetCards
import androidx.annotation.Keep

@Keep
data class ModelCredCards(
    val default: PaymentMethod?,
    val paymentMethod: List<PaymentMethod>?
)