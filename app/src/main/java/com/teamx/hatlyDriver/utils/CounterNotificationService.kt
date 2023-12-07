package com.example.payhilt.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.teamx.hatlyDriver.PushNotificationService
import com.teamx.hatlyDriver.R
import com.teamx.hatlyDriver.ui.activity.mainActivity.MainActivity


class CounterNotificationService(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification1(title: String, description: String, type: String, orderID: String) {

// Step 1: Create Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Step 1: Create Notification Channel
            val channel = NotificationChannel(
                COUNTER_CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Create the channel
            notificationManager.createNotificationChannel(channel)
        }

// Step 2: Create an Intent for the MainActivity
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

// Step 3: Create an Intent for the BroadcastReceiver
        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, PushNotificationService::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notificationId = System.currentTimeMillis().toInt()
// Step 4: Build and show the Notification
        val notificationBuilder = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.logo,
                "View",
                incrementIntent
            )

        val notification = notificationBuilder.build()

// Step 5: Notify with a unique ID
        notificationManager.notify(notificationId, notification)

    }


    /*    fun showNotification(counter: Int) {
            val activityIntent = Intent(context, MainActivity::class.java)
            val activityPendingIntent = PendingIntent.getActivity(
                context,
                1,
                activityIntent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            )
            val incrementIntent = PendingIntent.getBroadcast(
                context,
                2,
                Intent(context, CounterNotificationReceiver::class.java),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            )
            val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_agenda)
                .setContentTitle("Increment counter")
                .setContentText("The count is $counter")
                .setContentIntent(activityPendingIntent)
                .addAction(
                    R.drawable.ic_menu_agenda,
                    "Increment",
                    incrementIntent
                )
                .build()

            notificationManager.notify(1, notification)
        }

        fun showNotification1(header: String, comment: String) {
            val activityIntent = Intent(context, MainActivity::class.java)
            val activityPendingIntent = PendingIntent.getActivity(
                context,
                1,
                activityIntent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            )
             val incrementIntent = PendingIntent.getBroadcast(
                 context,
                 2,
                 Intent(context, PushNotificationService::class.java),
                 if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
             )
            Log.d("showNotification1", "showNotification1: ${comment}")
            val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_agenda)
                .setContentTitle(header)
                .setContentText(comment)
                .setContentIntent(activityPendingIntent)
                .addAction(
                  R.drawable.ic_menu_agenda,
                    "View",
                    incrementIntent
                )
                .build()

            notificationManager.notify(1, notification)
        }

        fun showNotification1(header: String, comment: String, type: String, orderID: String) {
            val activityIntent = Intent(context, MainActivity::class.java)

            Log.d("orderHistoryFragment", "onCreate 1: $type")

    //        val bundle = Bundle()
    //
    //        bundle.putString("typeDir",type)

            activityIntent.putExtra("typeDir", type)

            val activityPendingIntent = PendingIntent.getActivity(
                context,
                1,
                activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )



    //        val activityPendingIntent = PendingIntent.getActivity(
    //            context,
    //            1,
    //            activityIntent,
    //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0,bundle
    //        )


            val incrementIntent = PendingIntent.getBroadcast(
                context,
                2,
                Intent(context, PushNotificationService::class.java),
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
            )
            Log.d("showNotification1", "showNotification122 : ${comment}")
            val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_agenda)
                .setContentTitle(header)
                .setContentText(comment)
                .setContentIntent(activityPendingIntent)
                .addAction(R.drawable.ic_menu_agenda,
                    "View",
                    incrementIntent
                )
                .build()

            notificationManager.notify(1, notification)
        }*/


    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}