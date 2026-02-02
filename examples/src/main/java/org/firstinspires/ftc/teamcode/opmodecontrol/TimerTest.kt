package org.firstinspires.ftc.teamcode.opmodecontrol

import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "OpmodeControl Timer Test")
class TimerTest : OpMode() {

    val panelsTelemetry = PanelsTelemetry.telemetry

    var startTimestamp = -1L

    val deltaTime: Long
        get() {
            if(startTimestamp == -1L) return 0L
            return System.currentTimeMillis() - startTimestamp
        }

    override fun init() {
        startTimestamp = -1L
    }

    override fun start() {
        startTimestamp = System.currentTimeMillis()
    }

    override fun loop() {
        panelsTelemetry.debug("DeltaTime: $deltaTime")
        panelsTelemetry.update(telemetry)
    }
}