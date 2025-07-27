package com.bylazar.battery

import com.bylazar.panels.Panels

object PanelsBattery {
    fun getProvider(): BatteryProvider {
        val plugin = Panels.getPlugin(BatteryPlugin().id) as BatteryPlugin
        return plugin.provider
    }
}