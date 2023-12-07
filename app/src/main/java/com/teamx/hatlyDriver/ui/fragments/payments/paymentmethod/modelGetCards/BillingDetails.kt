package com.teamx.hatlyDriver.ui.fragments.payments.paymentmethod.modelGetCards
import androidx.annotation.Keep

@Keep
data class BillingDetails(
    val address: Address,
    val email: Any,
    val name: Any,
    val phone: Any
)