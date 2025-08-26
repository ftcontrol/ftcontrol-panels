package org.firstinspires.ftc.teamcode.gamepad

import com.bylazar.gamepad.PanelsGamepad
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Gamepad", group = "Dev")
class TestGamepad: OpMode() {
    val g1 = PanelsGamepad.firstManager
    val g2 = PanelsGamepad.secondManager
    val panelsTelemetry = PanelsTelemetry.telemetry
    override fun init() {

    }

    override fun loop() {
        val g1 = g1.asCombinedFTCGamepad(gamepad1)
        val g2 = g2.asCombinedFTCGamepad(gamepad2)

        panelsTelemetry.debug("==== Buttons ====")
        panelsTelemetry.debug("A: ${g1.a}")
        panelsTelemetry.debug("B: ${g1.b}")
        panelsTelemetry.debug("X: ${g1.x}")
        panelsTelemetry.debug("Y: ${g1.y}")
        panelsTelemetry.debug("DPad Up: ${g1.dpad_up}")
        panelsTelemetry.debug("DPad Down: ${g1.dpad_down}")
        panelsTelemetry.debug("DPad Left: ${g1.dpad_left}")
        panelsTelemetry.debug("DPad Right: ${g1.dpad_right}")
        panelsTelemetry.debug("Left Bumper: ${g1.left_bumper}")
        panelsTelemetry.debug("Right Bumper: ${g1.right_bumper}")
        panelsTelemetry.debug("Left Trigger: ${g1.left_trigger}")
        panelsTelemetry.debug("Right Trigger: ${g1.right_trigger}")
        panelsTelemetry.debug("Start / Options: ${g1.options}")
        panelsTelemetry.debug("Back / Share: ${g1.back}")
        panelsTelemetry.debug("Guide / PS: ${g1.guide}")
        panelsTelemetry.debug("Touchpad: ${g1.touchpad}")
        panelsTelemetry.debug("Left Stick Button: ${g1.left_stick_button}")
        panelsTelemetry.debug("Right Stick Button: ${g1.right_stick_button}")

        panelsTelemetry.debug("==== Sticks ====")
        panelsTelemetry.debug("Left Stick X: ${g1.left_stick_x}")
        panelsTelemetry.debug("Left Stick Y: ${g1.left_stick_y}")
        panelsTelemetry.debug("Right Stick X: ${g1.right_stick_x}")
        panelsTelemetry.debug("Right Stick Y: ${g1.right_stick_y}")

        panelsTelemetry.update(telemetry)
    }
}