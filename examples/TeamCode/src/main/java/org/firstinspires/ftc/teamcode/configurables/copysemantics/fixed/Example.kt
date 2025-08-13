package org.firstinspires.ftc.teamcode.configurables.copysemantics.fixed

import com.bylazar.configurables.annotations.Configurable
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.HardwareMap

@Configurable // [svp! ++]
class RobotClaw(hardwareMap: HardwareMap) {
    companion object {// [svp! ++]
        @JvmField// [svp! ++]
        var clawOffset = 0.2// [svp! ++]
    }// [svp! ++]

    private val clawServo = hardwareMap.servo.get("claw")

    fun setPosition(position: Double) {
        clawServo.position = clawOffset + position
    }
}

@Configurable // [svp! --]
@TeleOp(name = "Fixed Example TeleOp")
class FixedExampleOpMode : OpMode() {
    companion object {// [svp! --]
    @JvmField// [svp! --]
    var clawOffset = 0.2// [svp! --]
    }// [svp! --]

    private lateinit var robotClaw: RobotClaw
    override fun init() {
        robotClaw = RobotClaw(hardwareMap)
    }

    override fun loop() {
        robotClaw.setPosition(-gamepad1.left_stick_y.toDouble());
    }
}