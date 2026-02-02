package org.firstinspires.ftc.teamcode.configurables

import com.bylazar.configurables.annotations.Configurable
import com.bylazar.telemetry.PanelsTelemetry
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

@Configurable
@TeleOp(name = "Test Configurables OpMode")
class TestOpMode: OpMode() {
    companion object{
        @JvmField
        var testInt: Int = 1
        @JvmField
        var testBoolean: Boolean = false
        @JvmField
        var testDouble: Double = 1.0

        enum class States{
            TEST1,
            TEST2,
            TEST3
        }

        @JvmField
        var PIDConstants = SortedConfigurables()

        @JvmField
        var testEnum: States = States.TEST1

        @JvmField
        var testArray: Array<Int> = arrayOf(1, 2, 3)

        class CustomType(
            @JvmField var testInt: Int,
            @JvmField var testString: String
        ){
            override fun toString(): String {
                return "CustomType($testInt, $testString)"
            }
        }

        @JvmField
        var testCustomType: CustomType = CustomType(1, "test!")
    }

    val panelsTelemetry = PanelsTelemetry.telemetry

    override fun init() {
        panelsTelemetry.debug("Init was ran!")
        panelsTelemetry.update(telemetry)
    }

    override fun loop() {
        panelsTelemetry.debug("INT: $testInt")
        panelsTelemetry.debug("BOOLEAN: $testBoolean")
        panelsTelemetry.debug("DOUBLE: $testDouble")
        panelsTelemetry.debug("ENUM: $testEnum")
        panelsTelemetry.debug("ARRAY: ${testArray.contentToString()}")
        panelsTelemetry.debug("CUSTOM TYPE: $testCustomType")
        panelsTelemetry.update(telemetry)
    }
}