package com.bylazar.panels

open class PanelsConfig {
    open var isDisabled: Boolean = false
    open var enableLogs: Boolean = true
    open var enableClassCallerLogs: Boolean = true

    override fun toString(): String {
        return """
            isDisabled: $isDisabled
        """.trimIndent()
    }
}