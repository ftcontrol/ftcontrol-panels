package org.firstinspires.ftc.teamcode

import com.bylazar.configurables.annotations.Configurable
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@Configurable
@TeleOp(name = "Test Configurables Values", group = "Dev")
class TestConfigurablesValues : OpMode() {
    companion object {
        @JvmField
        var lazar = "test"
    }

    val telemetry = PanelsTelemetry.getTelemetry()

    override fun init() {
        telemetry.debug("Value of $lazar")
        telemetry.update()
    }

    override fun loop() {
        telemetry.debug("Value of $lazar")
        telemetry.update()
    }

}