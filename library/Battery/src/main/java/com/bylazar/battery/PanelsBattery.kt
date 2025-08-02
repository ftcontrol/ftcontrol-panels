package com.bylazar.battery

import com.bylazar.panels.Panels

object PanelsBattery {
    fun getProvider(): BatteryProvider {
        val plugin = Panels.getPlugin(BatteryPlugin().panelsPluginUniqueID) as BatteryPlugin
        return plugin.provider
    }
}