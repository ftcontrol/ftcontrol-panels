package org.firstinspires.ftc.teamcode

import com.bylazar.configurables.PanelsConfigurables
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Configurables", group = "Dev")
class TestConfigurables : OpMode() {

    val telemetry = PanelsTelemetry.getTelemetry()

    override fun init() {
        ClawConfig.follower = Follower()
        ClawConfig.follower?.something = "lazar"

        PanelsConfigurables.refreshClass(ClawConfig)
    }

    override fun loop() {
        telemetry.debug("Value of ${ClawConfig.follower?.something}")
        telemetry.update()
    }

    override fun stop() {
        ClawConfig.follower = null
    }
}