package org.firstinspires.ftc.teamcode.configurables.copysemantics.lambda

import com.bylazar.configurables.annotations.Configurable
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap

class RobotClaw(hardwareMap: HardwareMap, private val clawOffset: () -> Double) { // [svp! ++]
    private val clawServo = hardwareMap.servo.get("claw")

    fun setPosition(position: Double) {
        //clawServo.position = clawOffset + position // [svp! --]
        clawServo.position = clawOffset() + position // [svp! ++]
    }
}

@Configurable
@TeleOp(name = "Fixed Example with Lambda TeleOp")
class BadExampleOpMode : OpMode() {
    companion object {
        @JvmField
        var offset = 0.2
    }

    private lateinit var robotClaw: RobotClaw
    override fun init() {
        //robotClaw = RobotClaw(hardwareMap, offset) // [svp! --]
        robotClaw = RobotClaw(hardwareMap, { offset }) // [svp! ++]
    }

    override fun loop() {
        robotClaw.setPosition(-gamepad1.left_stick_y.toDouble());
    }
}