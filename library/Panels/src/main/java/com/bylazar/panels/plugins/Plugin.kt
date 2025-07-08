package com.bylazar.panels.plugins



abstract class Plugin<T : BasePluginConfig>(baseConfig: T){
    var config = baseConfig
    var details = PluginDetails()
    val isDev: Boolean
        get() = config.isDev
    val isEnabled: Boolean
        get() = config.isEnabled


}