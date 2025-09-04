package org.firstinspires.ftc.teamcode.telemetry

import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Telemetry FTC Style", group = "Dev")
class TestTelemetryFTCStyle: OpMode() {
    val panelsTelemetry = PanelsTelemetry.ftcTelemetry

    override fun init() {}

    override fun loop() {
        panelsTelemetry.addData("Key", "Value")
        panelsTelemetry.addData("Key2", 50)

        panelsTelemetry.update()
    }
}