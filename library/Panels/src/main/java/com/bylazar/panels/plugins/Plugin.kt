package com.bylazar.panels.plugins

import android.content.Context
import com.bylazar.panels.Panels


abstract class Plugin<T : BasePluginConfig>(baseConfig: T) {
    var config = baseConfig
    var details = PluginDetails()
    val isDev: Boolean
        get() = config.isDev
    val isEnabled: Boolean
        get() = config.isEnabled

    internal fun setConfig(newConfig: Any) {
        @Suppress("UNCHECKED_CAST")
        config = newConfig as T
    }

    abstract var id: String

    abstract fun onRegister(panelsInstance: Panels, context: Context)
}