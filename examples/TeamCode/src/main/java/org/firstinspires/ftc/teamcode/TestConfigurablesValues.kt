package org.firstinspires.ftc.teamcode

import com.bylazar.configurables.PanelsConfigurables
import com.bylazar.configurables.annotations.Configurable
import com.bylazar.field.PanelsField
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@Configurable
@TeleOp(name = "Test Configurables Values", group = "Dev")
class TestConfigurablesValues : OpMode() {
    companion object {
        @JvmField
        var lazar = "test"
        @JvmField
        var lazarBool = false
    }

    val telemetry = PanelsTelemetry.telemetry

    override fun init() {
        telemetry.debug("Value of $lazar")
        telemetry.debug("Value of $lazarBool")
        telemetry.update()
    }

    override fun loop() {
        telemetry.debug("Value of $lazar")
        telemetry.debug("Value of $lazarBool")
        telemetry.update()
    }

}