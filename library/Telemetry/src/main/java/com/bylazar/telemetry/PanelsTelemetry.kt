package com.bylazar.telemetry

import com.bylazar.panels.Panels

object PanelsTelemetry {
    val pkgName = this::class.java.`package`?.name ?: "null"

    fun getTelemetry(): TelemetryManager {
        val plugin = Panels.getPlugin(pkgName) as Plugin
        return plugin.manager
    }
}