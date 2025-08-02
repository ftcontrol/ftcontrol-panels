package com.bylazar.telemetry

import org.firstinspires.ftc.robotcore.external.Telemetry

class TelemetryManager(
    config: TelemetryPluginConfig,
    private val sendLines: (lines: MutableList<String>) -> Unit
) {
    var lines = mutableListOf<String>()

    val updateInterval = config.telemetryUpdateInterval
    var lastUpdate = 0L
    val timeSinceLastUpdate: Long
        get() = System.currentTimeMillis() - lastUpdate
    val shouldUpdateLines: Boolean
        get() = timeSinceLastUpdate >= updateInterval

    fun addData(key: String, value: Any) {
        lines.add("$key: $value")
    }

    fun addData(key: String, value: String) {
        lines.add("$key: $value")
    }

    fun addLine(line: String) {
        lines.add(line)
    }

    fun debug(vararg data: String) {
        data.forEach { lines.add(it) }
    }

    fun update() {
        if (lines.isEmpty()) return
        if (shouldUpdateLines) {
            sendLines(lines)
            lastUpdate = System.currentTimeMillis()
        }
        lines.clear()
    }

    fun update(telemetry: Telemetry) {
        lines.forEach { telemetry.addLine(it) }
        telemetry.update()
        update()
    }
}