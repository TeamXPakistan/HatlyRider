package com.teamx.hatlyDriver.localization

import android.os.Build

class Utility {
    companion object {
        fun isAtLeastVersion(version: Int): Boolean {
            return Build.VERSION.SDK_INT >= version
        }
    }
}