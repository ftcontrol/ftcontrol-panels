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
        val gm = g.asFTCGamepad

        telemetry.debug("==== Buttons ====")
        telemetry.debug("A: ${gm.a}")
        telemetry.debug("B: ${gm.b}")
        telemetry.debug("X: ${gm.x}")
        telemetry.debug("Y: ${gm.y}")
        telemetry.debug("DPad Up: ${gm.dpad_up}")
        telemetry.debug("DPad Down: ${gm.dpad_down}")
        telemetry.debug("DPad Left: ${gm.dpad_left}")
        telemetry.debug("DPad Right: ${gm.dpad_right}")
        telemetry.debug("Left Bumper: ${gm.left_bumper}")
        telemetry.debug("Right Bumper: ${gm.right_bumper}")
        telemetry.debug("Left Trigger: ${gm.left_trigger}")
        telemetry.debug("Right Trigger: ${gm.right_trigger}")
        telemetry.debug("Start / Options: ${gm.options}")
        telemetry.debug("Back / Share: ${gm.back}")
        telemetry.debug("Guide / PS: ${gm.guide}")
        telemetry.debug("Touchpad: ${gm.touchpad}")
        telemetry.debug("Left Stick Button: ${gm.left_stick_button}")
        telemetry.debug("Right Stick Button: ${gm.right_stick_button}")

        telemetry.debug("==== Sticks ====")
        telemetry.debug("Left Stick X: ${gm.left_stick_x}")
        telemetry.debug("Left Stick Y: ${gm.left_stick_y}")
        telemetry.debug("Right Stick X: ${gm.right_stick_x}")
        telemetry.debug("Right Stick Y: ${gm.right_stick_y}")

        telemetry.update()
    }
}