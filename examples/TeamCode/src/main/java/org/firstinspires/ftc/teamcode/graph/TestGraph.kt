package org.firstinspires.ftc.teamcode.graph

import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.cos
import kotlin.math.sin

@TeleOp(name = "Test Graph")
class TestGraph : OpMode() {
    private val panelsTelemetry = PanelsTelemetry.telemetry

    private val timer = ElapsedTime()

    private var sinVariable = 0.0
    private var cosVariable = 0.0
    private var constVariable = 0.0

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

        //Panels Graph finds data from Telemetry in format: <NAME>:<NUMBER VALUE>
        panelsTelemetry.addData("sin", sinVariable)
        panelsTelemetry.addData("cos", cosVariable)
        panelsTelemetry.addData("const", constVariable)
        panelsTelemetry.addLine("extra1: 1.0 extra2: 2.0 extra3: 7.0") // you can have multiple entries on one line
        panelsTelemetry.update(telemetry)
    }
}