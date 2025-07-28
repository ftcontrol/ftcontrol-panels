package org.firstinspires.ftc.teamcode

import com.bylazar.limelightproxy.LimelightProxyConfig
import com.bylazar.panels.PanelsConfig
import com.bylazar.telemetry.TelemetryPluginConfig

class Config : PanelsConfig() {
    override var isDisabled = false
}

class TelemetryConfig: TelemetryPluginConfig(){
    override var telemetryUpdateInterval = 100L
}

class LimelightProxyConfig: LimelightProxyConfig(){
    override var isDev = true
}