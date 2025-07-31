package org.firstinspires.ftc.teamcode

import com.bylazar.limelightproxy.LimelightProxyConfig
import com.bylazar.panels.PanelsConfig
import com.bylazar.telemetry.TelemetryPluginConfig

class Config : PanelsConfig() {
    @Transient
    override var isDisabled = false
}

class TelemetryConfig: TelemetryPluginConfig(){
    @Transient
    override var telemetryUpdateInterval = 100L
}

class LimelightProxyConfig: LimelightProxyConfig(){
    @Transient
    override var isDev = false
}