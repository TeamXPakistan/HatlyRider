package com.teamx.hatlyDriver.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.teamx.hatlyDriver.data.dataclasses.login.LoginData

class PrefHelper private constructor() {

    companion object {
        private val sharePref = PrefHelper()
        private lateinit var sharedPreferences: SharedPreferences
        private const val USER_DATA = "USERDATA"
        private const val RIDER_ONLINE = "riderOnline"
        private const val MAX_PROGRESS = "MAX_PROGRESS"
        private const val MAX_PROGRESS_TEXT = "MAX_PROGRESS_TEXT"
        private const val NOTIFICATION_ENABLE = "notificationEnable"


        fun getInstance(context: Context): PrefHelper {
            if (!::sharedPreferences.isInitialized) {
                synchronized(PrefHelper::class.java) {
                    if (!::sharedPreferences.isInitialized) {
                        sharedPreferences =
                            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                    }
                }
            }
            return sharePref
        }
    }

    fun getUserData(): LoginData? {
        val gson = Gson()
        return gson.fromJson(
            sharedPreferences.getString(USER_DATA, ""), LoginData::class.java
        )
    }

    fun setUserData(shippingAddress: LoginData?) {
        val gson = Gson()
        val str = gson.toJson(shippingAddress)
        sharedPreferences.edit().putString(USER_DATA, str).apply()
    }


    val getMax: Int?
        get() = sharedPreferences.getInt(MAX_PROGRESS, 0)


    fun isMax(isMax: Int) {
        sharedPreferences.edit().putInt(MAX_PROGRESS, isMax).apply()
    }

    val getSeekbarText: String?
        get() = sharedPreferences.getString(MAX_PROGRESS_TEXT, "")


    fun saveSeekbarText(isMax: String) {
        sharedPreferences.edit().putString(MAX_PROGRESS_TEXT, isMax).apply()
    }


}