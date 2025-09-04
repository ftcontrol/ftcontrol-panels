package com.bylazar.telemetry

object PanelsTelemetry {
    val telemetry: TelemetryManager
        get() = Plugin.manager

    val ftcTelemetry: TelemetryManager.TelemetryWrapper
        get() = Plugin.manager.wrapper
}