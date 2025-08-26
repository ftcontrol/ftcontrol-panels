package org.firstinspires.ftc.teamcode

import com.bylazar.configurables.PanelsConfigurables
import com.bylazar.configurables.annotations.Configurable
import com.bylazar.field.PanelsField
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@Configurable
@TeleOp(name = "Test Telemetry 2", group = "Dev")
class TestTelemetry : OpMode() {
    companion object {
        var test = "lazar"

        enum class Config{
            PANELS,
            PEDRO,
            RR
        }

        @JvmField var config = Config.PANELS
    }

    val telemetry = PanelsTelemetry.telemetry
    val field = PanelsField.field

    val imgID = field.registerImage(PanelsField.images.INTO_THE_DEEP.DARK)

    override fun init() {
        field.setOffsets(when(config){
            Config.PANELS -> PanelsField.presets.PANELS
            Config.PEDRO -> PanelsField.presets.PEDRO_PATHING
            Config.RR -> PanelsField.presets.ROAD_RUNNER
            else -> PanelsField.presets.PANELS
        })

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
        telemetry.debug("Config: $config")
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
        field.moveCursor(98.0, 58.0)
        field.img(10.0, 10.0, imgID)
        field.update()
    }
}