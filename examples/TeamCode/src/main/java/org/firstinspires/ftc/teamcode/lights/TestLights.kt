package org.firstinspires.ftc.teamcode.lights

import com.bylazar.lights.Headlight
import com.bylazar.lights.PanelsLights
import com.bylazar.lights.RGBIndicator
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.PI
import kotlin.math.sin

@TeleOp(name = "Test Lights")
class TestLights : OpMode() {
    private val lightManager = PanelsLights.lights
    private val timer = ElapsedTime()

    private lateinit var rgb: RGBIndicator
    private lateinit var head: Headlight

    override fun init() {
        rgb = RGBIndicator("Colored Light").apply {
            withProvider { ((sin(timer.seconds() * PI) + 1.0) / 2.0) }
        }
        head = Headlight("Simple Light")

        lightManager.initLights(
            rgb,
            head
        )
        timer.reset()
    }

    override fun loop() {
        val t = timer.seconds()

        val headOn = ((t / 1.5).toInt() % 2) == 0

        rgb.update()
        head.update(headOn)

        lightManager.update()

        telemetry.addData("Time (s)", "%.2f", t)
        lightManager.addToTelemetry(telemetry)
        telemetry.update()
    }
}
