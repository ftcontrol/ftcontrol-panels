package org.firstinspires.ftc.teamcode

import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Telemetry", group = "Dev")
class TestTelemetry : OpMode() {

    val telemetry = PanelsTelemetry.getTelemetry()

    override fun init() {
        telemetry.debug("Init was ran")
        telemetry.update()
    }

    override fun loop() {
        telemetry.debug("Loop of ${System.currentTimeMillis()}")
        telemetry.update()
    }
}