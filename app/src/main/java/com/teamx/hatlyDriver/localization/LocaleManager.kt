package com.teamx.hatlyDriver.localization

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build.VERSION_CODES
import android.preference.PreferenceManager
import java.util.*

open class LocaleManager(context: Context) {
    companion object {
        val LANGUAGE_ENGLISH = "en"
        val LANGUAGE_ARABIC = "ar"


        val DEFAULT_LAN = LANGUAGE_ENGLISH

        val LANGUAGE_KEY = "language_key"

    }
    private var prefs: SharedPreferences? = null

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }


    fun setLocale(c: Context): Context {
        return updateResources(c, getLanguage())
    }

    fun setNewLocale(
        c: Context,
        language: String
    ): Context {
        persistLanguage(language)
        return updateResources(c, language)
    }

    fun getLanguage(): String {
        return prefs!!.getString(LANGUAGE_KEY, DEFAULT_LAN)!!
    }


    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs!!.edit().putString(LANGUAGE_KEY, language).commit()
    }

    private fun updateResources(
        contextx: Context,
        language: String
    ): Context {
        var context = contextx
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config =
            Configuration(res.configuration)
        if (Utility.isAtLeastVersion(VERSION_CODES.N)) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }


}

