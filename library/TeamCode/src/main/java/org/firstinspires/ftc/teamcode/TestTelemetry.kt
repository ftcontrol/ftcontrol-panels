package org.firstinspires.ftc.teamcode

import com.bylazar.configurables.PanelsConfigurables
import com.bylazar.field.PanelsField
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Telemetry", group = "Dev")
class TestTelemetry : OpMode() {
    companion object {
        var test = "lazar"
    }

    val telemetry = PanelsTelemetry.getTelemetry()
    val field = PanelsField.getField()

    override fun init() {
        field.setBackground(PanelsField.images.INTO_THE_DEEP.DARK)

        telemetry.debug("Init was ran")
        telemetry.update()

        PanelsConfigurables.refreshClass(this)
    }

    override fun loop() {
        telemetry.debug("Loop of ${System.currentTimeMillis()}")
        telemetry.update()
        field.goto(0.0, 0.0)
        field.setStyle(PanelsField.WHITE, PanelsField.BLUE, 0.1)
        field.circle(4.0)
        field.update()
    }
}