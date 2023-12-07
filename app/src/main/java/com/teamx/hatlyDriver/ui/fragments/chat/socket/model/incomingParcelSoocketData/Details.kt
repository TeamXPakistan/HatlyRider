package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingParcelSoocketData

data class Details(
    val description: String,
    val height: Double,
    val heightUnit: String,
    val item: String,
    val length: Double,
    val lengthUnit: String,
    val qty: Int,
    val weight: Int,
    val weightUnit: String,
    val width: Double,
    val widthUnit: String
)