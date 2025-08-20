package org.firstinspires.ftc.teamcode

import com.bylazar.graph.PanelsGraph
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.cos
import kotlin.math.sin

@TeleOp(name = "Test Graph")
class TestGraph : OpMode() {
    private val graphManager = PanelsGraph.manager

    private val timer = ElapsedTime()

    private var sinVariable = 0.0
    private var cosVariable = 0.0
    private var constVariable = 0.0

    override fun init() {
        timer.reset()
        updateSignals()
        graphManager.addData("sin", sinVariable)
        graphManager.addData("cos", cosVariable)
        graphManager.addData("const", constVariable)
        graphManager.update()
    }

    override fun loop() {
        updateSignals()

        graphManager.addData("sin", sinVariable)
        graphManager.addData("cos", cosVariable)
        graphManager.addData("const", constVariable)
        graphManager.update()
    }

    private fun updateSignals() {
        val t = timer.seconds()
        sinVariable = sin(t)
        cosVariable = cos(t)
    }
}