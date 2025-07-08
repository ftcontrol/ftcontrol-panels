package com.bylazar.panels

open class PanelsConfig {
    open var isDisabled: Boolean = false

    override fun toString(): String {
        return """
            isDisabled: $isDisabled
        """.trimIndent()
    }
}