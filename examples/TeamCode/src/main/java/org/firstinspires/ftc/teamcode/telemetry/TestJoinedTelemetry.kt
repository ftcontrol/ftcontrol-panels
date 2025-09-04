package org.firstinspires.ftc.teamcode.telemetry

import com.bylazar.telemetry.JoinedTelemetry
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Joined Telemetry", group = "Dev")
class TestJoinedTelemetry: OpMode() {
    val joinedTelemetry = JoinedTelemetry(PanelsTelemetry.ftcTelemetry, telemetry)

    override fun init() {}

    override fun loop() {
        joinedTelemetry.addData("Key", "Value")
        joinedTelemetry.addData("Key2", 50)

        joinedTelemetry.update()
    }
}