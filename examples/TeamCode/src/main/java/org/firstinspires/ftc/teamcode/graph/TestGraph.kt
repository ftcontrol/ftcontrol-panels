package org.firstinspires.ftc.teamcode.graph

import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.*

@TeleOp(name = "Test Graph")
class TestGraph : OpMode() {
    private val panelsTelemetry = PanelsTelemetry.telemetry
    private val timer = ElapsedTime()

    private var sinVariable = 0.0
    private var cosVariable = 0.0
    private var constVariable = 0.0
    private var dampedSine = 0.0
    private var lissajous = 0.0
    private var ramp = 0.0
    private var squareWave = 0.0

    override fun init() {
        timer.reset()
        updateSignals()
    }

    override fun loop() {
        updateSignals()
    }

    private fun updateSignals() {
        val t = timer.seconds()

        sinVariable = sin(t)
        cosVariable = cos(t)
        constVariable = 1.0

        dampedSine = exp(-0.2 * t) * sin(2 * t)

        lissajous = sin(3 * t + Math.PI / 2) * cos(2 * t)

        ramp = (t % 5.0) / 5.0

        squareWave = if (sin(2 * Math.PI * 0.5 * t) > 0) 1.0 else -1.0

        //Panels Graph finds data from Telemetry in format: <NAME>:<NUMBER VALUE>
        panelsTelemetry.addData("sin", sinVariable)
        panelsTelemetry.addData("cos", cosVariable)
        panelsTelemetry.addData("dampedSine", dampedSine)
        panelsTelemetry.addData("lissajous", lissajous)
        panelsTelemetry.addData("ramp", ramp)
        panelsTelemetry.addData("square", squareWave)
        panelsTelemetry.addData("const", constVariable)

        // Multiple values in one line
        panelsTelemetry.addLine("extra1:${t} extra2:${t*t} extra3:${sqrt(t)}")

        panelsTelemetry.update(telemetry)
    }
}
