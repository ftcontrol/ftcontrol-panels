package org.firstinspires.ftc.teamcode.field

import com.bylazar.field.PanelsField
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Coords")
class TestCoordsOpMode : OpMode() {
    private val panelsField = PanelsField.field

    private val p = Point(0.0, 0.0)

    override fun init() {
        panelsField.setStyle(fill = "none", outline = "white", width = 1.5)

        panelsField.moveCursor(p.x, p.y)
        panelsField.circle(4.0)

        panelsField.update()
    }

    override fun start() {
    }

    override fun loop() {

    }

    data class Point(val x: Double, val y: Double)
}
