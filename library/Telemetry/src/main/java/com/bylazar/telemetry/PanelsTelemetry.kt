package com.bylazar.telemetry

import com.bylazar.panels.Panels

object PanelsTelemetry {
    fun getTelemetry(): TelemetryManager {
        val plugin = Panels.getPlugin(TelemetryPlugin().panelsPluginUniqueID) as TelemetryPlugin
        return plugin.manager
    }
}