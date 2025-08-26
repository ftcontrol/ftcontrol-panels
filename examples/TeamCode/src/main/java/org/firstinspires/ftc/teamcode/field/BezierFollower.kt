package org.firstinspires.ftc.teamcode.field

import com.bylazar.field.PanelsField
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Bezier Follower OpMode")
class BezierFollowerOpMode : OpMode() {
    private val panelsField = PanelsField.field

    private val p0 = Point(57.0, 56.0)
    private val p1 = Point(60.0, 11.0)
    private val p2 = Point(48.0, 6.0)
    private val p3 = Point(30.0, 10.0)

    private var tAnim = 0.0
    private var lastTime = 0.0
    private val loopDurationSec = 5.0

    override fun init() {
        panelsField.setStyle(fill = "none", outline = "white", width = 1.5)

        val steps = 200
        run {
            val t0 = 0.0
            val pStart = bezierCubic(p0, p1, p2, p3, t0)
            panelsField.moveCursor(pStart.x, pStart.y)
            for (i in 1..steps) {
                val t = i.toDouble() / steps.toDouble()
                val p = bezierCubic(p0, p1, p2, p3, t)
                panelsField.line(p.x, p.y)
                panelsField.moveCursor(p.x, p.y)
            }
        }

        panelsField.update()
    }

    override fun start() {
        lastTime = time
    }

    override fun loop() {
        val steps = 200
        run {
            val t0 = 0.0
            val pStart = bezierCubic(p0, p1, p2, p3, t0)
            panelsField.moveCursor(pStart.x, pStart.y)
            for (i in 1..steps) {
                val t = i.toDouble() / steps.toDouble()
                val p = bezierCubic(p0, p1, p2, p3, t)
                panelsField.line(p.x, p.y)
                panelsField.moveCursor(p.x, p.y)
            }
        }

        val now = time
        val dt = (now - lastTime).coerceAtLeast(0.0)
        lastTime = now
        tAnim = (tAnim + dt / loopDurationSec) % 1.0

        val p = bezierCubic(p0, p1, p2, p3, tAnim)
        panelsField.moveCursor(p.x, p.y)
        panelsField.circle(1.5)

        panelsField.update()
    }

    data class Point(val x: Double, val y: Double)

    private fun bezierCubic(p0: Point, p1: Point, p2: Point, p3: Point, t: Double): Point {
        // B(t) = (1-t)^3 p0 + 3(1-t)^2 t p1 + 3(1-t) t^2 p2 + t^3 p3
        val u = 1.0 - t
        val u2 = u * u
        val u3 = u2 * u
        val t2 = t * t
        val t3 = t2 * t

        val x = u3 * p0.x +
                3.0 * u2 * t * p1.x +
                3.0 * u * t2 * p2.x +
                t3 * p3.x

        val y = u3 * p0.y +
                3.0 * u2 * t * p1.y +
                3.0 * u * t2 * p2.y +
                t3 * p3.y

        return Point(x, y)
    }
}
