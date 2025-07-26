package org.firstinspires.ftc.teamcode

import com.bylazar.gamepad.PanelsGamepad
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp


@TeleOp(name = "Test Gamepad", group = "Dev")
class TestGamepad: OpMode() {
    val g = PanelsGamepad.getGamepad()
    val telemetry = PanelsTelemetry.getTelemetry()
    override fun init() {

    }

    override fun loop() {
        telemetry.debug("OPTIONS: ${g.options}")
        telemetry.update()
    }
}