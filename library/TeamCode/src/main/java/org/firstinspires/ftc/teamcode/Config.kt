package org.firstinspires.ftc.teamcode

import com.bylazar.limelightproxy.LimelightProxyConfig
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
//        DevPluginEntry(
//            pluginID = "com.bylazar.capture",
//            devURL = "http://localhost:3002"
//        ),
//        DevPluginEntry(
//            pluginID = "com.bylazar.limelightproxy",
//            devURL = "http://localhost:3003"
//        ),
//        DevPluginEntry(
//            pluginID = "com.bylazar.field",
//            devURL = "http://localhost:3004"
//        ),
        DevPluginEntry(
            pluginID = "com.bylazar.gamepad",
            devURL = "http://localhost:3005"
        ),
        DevPluginEntry(
            pluginID = "com.bylazar.opmodecontrol",
            devURL = "http://localhost:3006"
        )
    )
}

class TelemetryConfig: TelemetryPluginConfig(){
    override var telemetryUpdateInterval = 100L
}

class LimelightProxyConfig: LimelightProxyConfig(){
    override var isDev = true
}