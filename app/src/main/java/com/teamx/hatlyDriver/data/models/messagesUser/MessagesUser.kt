package com.teamx.raseef.data.models.messagesUser

import androidx.annotation.Keep


@Keep
data class MessagesUser(
    val _id: String,
    val imgMsg: String,
    val name: String,
    val time: String,
    val isUser: Boolean,
    val timeAndDate: String,
)

