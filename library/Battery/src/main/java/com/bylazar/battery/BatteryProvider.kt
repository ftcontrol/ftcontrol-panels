package com.bylazar.battery

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit
import kotlin.math.max

class BatteryProvider {
    var batteryVoltage = -1.0
    internal var lastBatteryVoltage: Double = 0.0

    internal val hasChanged: Boolean
        get() = batteryVoltage != lastBatteryVoltage

    internal fun updateBatteryVoltage(opModeManager: OpModeManagerImpl) {
        batteryVoltage = -1.0

        val activeOpMode = opModeManager.activeOpMode
        val hardwareMap = activeOpMode?.hardwareMap

        if (hardwareMap != null) {
            for (module in hardwareMap.getAll(LynxModule::class.java)) {
                batteryVoltage = max(batteryVoltage, module.getInputVoltage(VoltageUnit.VOLTS))
            }
        }
    }
}