package com.teamx.hatlyDriver.ui.fragments.chat.socket.model.allmessageData

import androidx.annotation.Keep

@Keep
data class GetAllMessageData(
    val docs: List<Doc>,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val limit: Int,
    val nextPage: Int,
    val page: Int,
    val prevPage: Int,
    val totalDocs: Int,
    val totalPages: Int
)