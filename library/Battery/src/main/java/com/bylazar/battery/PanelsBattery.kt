package com.bylazar.battery

import com.bylazar.panels.Panels

object PanelsBattery {
    val pkgName = this::class.java.`package`?.name ?: "null"

    fun getProvider(): BatteryProvider {
        val plugin = Panels.getPlugin(pkgName) as Plugin
        return plugin.provider
    }
}