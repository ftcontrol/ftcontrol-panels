package org.firstinspires.ftc.teamcode.telemetry

import com.bylazar.telemetry.JoinedTelemetry
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Format Telemetry", group = "Dev")
class TestFormatTelemetry: OpMode() {
    val joinedTelemetry = JoinedTelemetry(PanelsTelemetry.ftcTelemetry, telemetry)

    override fun init() {}

    override fun loop() {
        joinedTelemetry.addData("Key", "Value")
        joinedTelemetry.addData("Key2", 50)
        val percent = 0.51453
        joinedTelemetry.addData("Robot health", "Battery: %.2f%%", percent)

        joinedTelemetry.update()
    }
}