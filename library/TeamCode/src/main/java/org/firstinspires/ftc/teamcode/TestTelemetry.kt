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

    val telemetry = PanelsTelemetry.telemetry
    val field = PanelsField.field

    val imgID = field.registerImage(PanelsField.images.INTO_THE_DEEP.DARK)

    override fun init() {

        Drawing.field.moveCursor(0.0, 0.0)

        field.setBackground(PanelsField.images.INTO_THE_DEEP.LIGHT)

        field.moveCursor(0.0, 0.0)
        field.setStyle(PanelsField.RED, PanelsField.BLUE, 0.1)
        field.circle(6.5)

        field.update()

        telemetry.debug("Init was ran")
        telemetry.update()

        PanelsConfigurables.refreshClass(this)
    }

    override fun loop() {
        telemetry.debug("Loop of ${System.currentTimeMillis()}")
        telemetry.update()
        field.moveCursor(0.0, 0.0)
        field.setStyle(PanelsField.RED, PanelsField.BLUE, 0.1)
        field.circle(6.5)
        field.moveCursor(10.0, 10.0)
        field.rect(10.0, 5.0)
        field.moveCursor(20.0, 20.0)
        field.line(25.0, 25.0)
        field.moveCursor(40.0, 40.0)
        field.img(10.0, 10.0, imgID)
        field.update()
    }
}