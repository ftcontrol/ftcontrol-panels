package org.firstinspires.ftc.teamcode

import com.bylazar.panels.DevPluginEntry
import com.bylazar.panels.PanelsConfig
import com.bylazar.telemetry.TelemetryPluginConfig

class Config : PanelsConfig() {
    override var isDisabled = false
    override var devPlugins: List<DevPluginEntry> = listOf(
//        DevPluginEntry(
//            pluginID = "com.bylazar.configurables",
//            devURL = "http://localhost:3000"
//        ),
//        DevPluginEntry(
//            pluginID = "com.bylazar.exampleplugin",
//            devURL = "http://localhost:3001"
//        ),
        DevPluginEntry(
            pluginID = "com.bylazar.capture",
            devURL = "http://localhost:3002"
        )
    )
}

class TelemetryConfig: TelemetryPluginConfig(){
    override var telemetryUpdateInterval = 100L
}