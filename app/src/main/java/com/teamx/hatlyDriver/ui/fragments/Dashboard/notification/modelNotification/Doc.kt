package com.teamx.hatlyDriver.ui.fragments.Dashboard.notification.modelNotification

data class Doc(
    val _id: String,
    val description: String,
    val isRead: Boolean,
    val notifiableId: String,
    val title: String,
    val userId: String,
    var createdAt: String
)