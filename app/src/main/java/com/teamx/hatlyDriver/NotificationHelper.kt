package com.teamx.hatlyDriver

import android.content.Context
import android.util.Log
import com.teamx.hatlyDriver.ui.activity.mainActivity.MainActivity.Companion.service
import org.json.JSONException
import org.json.JSONObject

object NotificationHelper {
    fun displayNotification(context: Context?, title: String?, description: String) {

        Log.d("showNotification1", "description: ${description}")
        try {
            val jsonObject = JSONObject(description)

            val type = jsonObject.getString("type")
            val descriptionJson = jsonObject.getString("Descripion") // Note the typo in the JSON key
            val orderID = jsonObject.getString("orderID")
            val actionText = jsonObject.getString("actionText")

            service!!.showNotification1(title!!, descriptionJson, type, orderID)
        }catch (e: JSONException) {
            e.printStackTrace()
            Log.d("showNotification1", "description JSONException: ${description}")
            Log.d("showNotification1", "title JSONException: ${title}")
            service!!.showNotification1(title!!, description, "dummy soahil","46545646")
        }catch (e : Exception){
            Log.d("showNotification1", "description: Exception ${description}")
        }

    }

}