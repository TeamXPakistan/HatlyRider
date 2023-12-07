package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingOrderSocketData

data class Veriation(
    val _id: String,
    val isMultiple: Boolean,
    val isRequired: Boolean,
    val limit: Int,
    val options: List<Option>,
    val title: String
)