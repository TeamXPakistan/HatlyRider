package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingOrderSocketData

data class Setting(
    val closesAt: String,
    val contact: String,
    val location: Location,
    val opensAt: String
)