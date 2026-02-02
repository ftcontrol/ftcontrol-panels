package org.firstinspires.ftc.teamcode.battery

import com.bylazar.battery.PanelsBattery

class Main {
    fun readVoltage(): Double {
        val provider = PanelsBattery.provider
        val voltage = provider.batteryVoltage
        return voltage
    }
}