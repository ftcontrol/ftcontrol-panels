package com.bylazar.telemetry

import com.bylazar.panels.Panels

object PanelsTelemetry {
    fun getTelemetry(): TelemetryManager {
        val plugin = Panels.getPlugin(TelemetryPlugin().id) as TelemetryPlugin
        return plugin.manager
    }
}