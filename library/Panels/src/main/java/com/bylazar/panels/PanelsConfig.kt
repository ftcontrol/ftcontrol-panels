package com.bylazar.panels

data class DevPluginEntry(
    val pluginID: String,
    val devURL: String
)

open class PanelsConfig {
    open var isDisabled: Boolean = false
    open var enableLogs: Boolean = true
    open var enableClassCallerLogs: Boolean = true

    open var devPlugins = listOf<DevPluginEntry>()

    override fun toString(): String {
        return """
            isDisabled: $isDisabled
        """.trimIndent()
    }
}