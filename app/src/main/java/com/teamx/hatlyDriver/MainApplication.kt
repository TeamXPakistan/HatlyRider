package com.teamx.hatlyDriver

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.teamx.hatlyDriver.utils.localization.LocaleManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        var application: Application? = null
            private set
        val context: Context
            get() = application!!.applicationContext
        val PACKAGE_NAME: String
            get() = application!!.packageName

        var localeManager: LocaleManager? = null

    }

    override fun onCreate() {
        super.onCreate()

        application = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    override fun attachBaseContext(base: Context?) {
        localeManager = LocaleManager(base!!)
        super.attachBaseContext(localeManager!!.setLocale(base!!))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig!!)
        localeManager!!.setLocale(this)
    }

}