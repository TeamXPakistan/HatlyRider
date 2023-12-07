package com.teamx.hatlyDriver.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TimeFormatter {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatTimeDifference(timestamp: String): String {
            val instant = Instant.parse(timestamp)
            val now = Instant.now()
            val duration = Duration.between(instant, now)

            return when {
                duration.toDays() > 2 -> {
                    // If more than 2 days, return the actual date
                    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
//                    localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                }

                duration.toDays() > 1 -> "${duration.toDays()} days ago"
                duration.toDays() == 1L -> "1 day ago"
                duration.toHours() > 1 -> "${duration.toHours()} hours ago"
                duration.toHours() == 1L -> "1 hour ago"
                duration.toMinutes() > 1 -> "${duration.toMinutes()} minutes ago"
                duration.toMinutes() == 1L -> "1 minute ago"
                else -> "just now"
            }
        }

    }
}