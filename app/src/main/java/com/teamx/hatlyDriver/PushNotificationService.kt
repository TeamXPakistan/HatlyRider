package com.teamx.hatlyDriver

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging

class
PushNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title
            val description = remoteMessage.notification!!.body
            Log.d("123123", "onMessageReceived:$description ")
            Log.d("123123", "onMessageReceived:$title ")
            Log.d("123123", "onMessageReceived:${remoteMessage.notification} ")
            NotificationHelper.displayNotification(applicationContext, title, description ?: "")
        }
        Firebase.messaging.isAutoInitEnabled = true
        Log.d("123123", "onMessageReceived: ")

    }

    override fun onNewToken(token: String) {
        Log.d("123123", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }


}