package org.firstinspires.ftc.teamcode.telemetry

import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@TeleOp(name = "Test Telemetry", group = "Dev")
class TestTelemetry: OpMode() {
    inner class CustomObject(
        var number: Int = 0
    ){
        override fun toString(): String {
            return "Number: $number"
        }
    }

    val obj = CustomObject(10)

    val panelsTelemetry = PanelsTelemetry.telemetry

    override fun init() {}

    override fun loop() {
        panelsTelemetry.addData("Key", "Value")
        panelsTelemetry.addData("Key2", obj)

        panelsTelemetry.debug(
            "String 1",
            "String 2",
            "String 3",
            obj
        )

        panelsTelemetry.update(telemetry) //You can pass FTC telemetry to update both
    }
}