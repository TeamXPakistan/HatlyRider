package com.teamx.raseef.ui.fragments.messages.socket.model
import androidx.annotation.Keep

@Keep
data class Message(
    val __v: Int,
    val _id: String,
    val content: String,
    val createdAt: String,
    val from: String,
    val media: String,
    val read: Boolean,
    val room_id: String,
    val updatedAt: String
)