package com.bylazar.panels.core

import android.content.Context
import android.content.SharedPreferences
import com.bylazar.panels.Logger
import androidx.core.content.edit

object PreferencesHandler {
    const val PREF_FILE_NAME: String = "FTControl Panels"
    const val KEY_AUTO_ENABLE: String = "autoEnable"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        Logger.coreLog("Initialized Preferences")
    }

    var isEnabled: Boolean
        get() = try {
            prefs.getBoolean(KEY_AUTO_ENABLE, true)
        } catch (e: UninitializedPropertyAccessException) {
            Logger.error("Preferences", "Preferences not initialized!")
            true
        }
        set(value) {
            if (!this::prefs.isInitialized) {
                Logger.error("Preferences", "Preferences not initialized!")
                return
            }
            prefs.edit { putBoolean(KEY_AUTO_ENABLE, value) }
        }
}
