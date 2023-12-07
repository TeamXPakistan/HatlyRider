package com.teamx.raseef.ui.fragments.messages.socket.model
import androidx.annotation.Keep

@Keep
data class GetAllChatsDataItem(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val messages: List<Message>,
    val order: String,
    val updatedAt: String,
    val users: List<String>
)